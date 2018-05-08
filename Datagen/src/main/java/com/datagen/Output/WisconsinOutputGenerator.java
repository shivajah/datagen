package com.datagen.Output;

import com.datagen.Generators.wisconsingenerators.WisconsinGenerator;
import com.datagen.schema.Schema;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by shiva on 3/10/18.
 */
public class WisconsinOutputGenerator {
    private Schema schema;
    private List<WisconsinGenerator> generators;
    final String directory = "/Users/shiva/DataGen/Datagen/src/main/java/com/datagen/Output/data/";
    private String fileName;
    private Map<Integer,BufferedOutputStream> streams;
    private long maxRecordLength;
    private ExecutorService executorService;
    private Map<Integer,Pair<Integer,Integer>> executorsToStartAndEnd;
    private int numOfExecutors;
    public WisconsinOutputGenerator(Schema schema,List<WisconsinGenerator> generators){
        numOfExecutors = schema.getNumOfPartitions();
        initExecutors();
        this.schema = schema;
        this.generators=generators;
        streams = new HashMap<>();
        setUniqueFileName();
        maxRecordLength=0;

        executorsToStartAndEnd = new HashMap<>();
        initReaderToStartAndEnd();

    }

    private void initExecutors() {
        executorService = Executors.newFixedThreadPool(numOfExecutors);
    }

    private void shutDownExecutors() {
        try {
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS); // TODO: Is this necessary?

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if (!executorService.isTerminated()) {
                System.out.println("canceling all pending tasks");
            }
            executorService.shutdownNow();
            System.out.println("Shutdown complete!");
        }
    }
    private void initReaderToStartAndEnd() {
        int length = schema.getCardinality()/numOfExecutors;
        int oneToLastEnd = -1;
        for(int i = 0; i<numOfExecutors;i++){
            int start = length*i;

            Pair<Integer,Integer> p;
            if(i==numOfExecutors-1){
                p=new Pair<>(start,schema.getCardinality());
            }
            else {
                p = new Pair<>(start, start + length);
            }
            executorsToStartAndEnd.put(i,p);
        }

    }

    public void execute(){
        IntStream.range(0, numOfExecutors).forEach(readerId -> {
            executorService.submit(() -> {

                for (int id = executorsToStartAndEnd.get(readerId).getKey();
                     id < executorsToStartAndEnd.get(readerId).getValue(); id++) {
                    long size = 0;
                    String record = "{";

                    for (int i = 0; i < schema.getFields().size(); i++) {
                        boolean comma = record.equalsIgnoreCase("{") ? false : true;
                        Object nullOrMissing = generators.get(i).next(id);
                        if ((nullOrMissing instanceof Long && (long) nullOrMissing == Long.MAX_VALUE) || (nullOrMissing instanceof String && ((String) nullOrMissing).isEmpty())) {
                            continue;
                        }
                        if (comma) {
                            record = record + ", ";
                        }
                        if (nullOrMissing == null) {
                            record = record + "\"" + schema.getFields().get(i).getName() + "\":" + null;
                        } else {
                            Object val = generators.get(i).next(id);
                            record = record + "\"" + schema.getFields().get(i).getName() + "\":";
                            if (val instanceof String)
                                record = record + "\"" + val + "\"";
                            else
                                record = record + val;
                            size += schema.getFields().get(i).getSizeInBytes(schema.getCardinality());
                        }

                    }
                    record = record + "}\n";
                    //System.out.println(record);
                    write(record,readerId);
                    if (size > maxRecordLength) {
                        maxRecordLength = size;
                    }
                    if (id == 5) {
                        System.out.println(maxRecordLength);
                    }
                }
                close(readerId);
            });
        });
        shutDownExecutors();
    }
public long getMaxRecordLength(){
        return maxRecordLength;
}
    private void setUniqueFileName(){
        String fname = schema.getFileName();
        if(fname!=null && !fname.contains(".adm")){
            fname = fname +".adm";
        }
        fileName = (fname != null)? directory+fname:directory+"tempFile.adm";
        File f = new File(fileName);
        int i = 1;
        String newFname= fileName;
        while(f.exists()){
            String[] splits = fileName.split(".adm");
            newFname = splits[0]+"_"+i+".adm";
            f=new File(newFname);
            i++;
        }
        fileName = newFname;
            IntStream.range(0,numOfExecutors).forEach(readerId -> {
                String[]splits = fileName.split(".adm");
                String execFilename = splits[0]+"p_"+readerId+".adm";
                File execFile = new File(execFilename);
                try {
                    execFile.createNewFile();
                    streams.put(readerId,new BufferedOutputStream(
                            new FileOutputStream(execFilename)));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });


    }
    public void write(String record, int execId){

                try {
                streams.get(execId).write(record.getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

    }
    public void close(int execId){
                try {
                    streams.get(execId).close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }
}
