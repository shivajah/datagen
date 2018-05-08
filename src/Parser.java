import Constants.DataTypes;
import Constants.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import schema.Domain.Domain;
import schema.Domain.IntegerDomain;
import schema.Domain.Range;
import schema.Domain.StringDomain;
import schema.Field;
import schema.Schema;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 3/1/18.
 */
public class Parser {
    public Schema parseConfigFile(){
        Schema schema  =  new Schema();
        Map<String, Domain> domains = parseDomain();
        try {
            File inputFile = new File("./src/configs/shivsconfig");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =  dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //get cardinality
            NodeList nList = doc.getElementsByTagName("dataset");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                schema.setCardinality(Integer.parseInt(getElement(element,"cardinality")));
                schema.setDatasetName(getElement(element,"name"));
            }
            //get file info
            nList = doc.getElementsByTagName("file");
            nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                schema.setFileName(getElement(element,"name"));
                schema.setNumOfPartitions(Integer.parseInt(getElement(element,"partitions")));
            }
            //getFields
             nList = doc.getElementsByTagName("field");
            Field field;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                 nNode = nList.item(temp);
                field = new Field();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    field.setPosition(Integer.parseInt(eElement.getAttribute("position")));
                    field.setName(getElement(eElement,"name"));
                    String t = getElement(eElement,"type");
                    DataTypes.DataType type = t.equalsIgnoreCase("string")? DataTypes.DataType.STRING :(t.equalsIgnoreCase("integer")?
                            DataTypes.DataType.INTEGER: null);
                    field.setType(type);
                    field.setDeclared(getElement(eElement,"declared").equalsIgnoreCase("true"));
                    String domainst = getElement(eElement,"domain");
                    if(!domains.containsKey(domainst)){
                        throw new IllegalArgumentException("Invalid domain name has been entered.");
                    }
                    field.setDomain(domains.get(domainst));
                    String orderst =  getElement(eElement,"order");
                    Order.order order = orderst.equalsIgnoreCase("random")? Order.order.RANDOM:(orderst.equalsIgnoreCase("sequential")?
                            Order.order.SEQUENTIAL:null);
                    field.setOrder(order);
//                    if(!schema.getFields().isEmpty()&&schema.getFields().get(field.getPosition())!= null){
//                        throw new IllegalArgumentException("Multiple fields have been selected for the same position in the schema.");
//                    }
                    schema.getFields().add(field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema;
    }

    public Map<String,Domain> parseDomain(){
        Map<String,Domain> domains = new HashMap<>();
        try {
            File inputFile = new File("./src/configs/domain");
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Domain domain = parse(line);
                domains.put(domain.getName(),domain);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return domains;
    }
    private Domain parse(String line) throws IllegalArgumentException{
        Domain domain;
        String[] colonSignSplits = line.split(":");
        String type = colonSignSplits[0].trim();
        if(type.equalsIgnoreCase("String")){
            domain = new StringDomain();
        }
        else if(type.equalsIgnoreCase("Integer")){
            domain = new IntegerDomain();
        }
        else {
            throw new IllegalArgumentException();
        }
        String name = colonSignSplits[1].trim();
        String[] equalSignSplits = name.split("=");
        domain.setName(equalSignSplits[0].trim());
        Domain.Type rangeOrVal;
        char rangeOrValue = equalSignSplits[1].trim().charAt(0);
        rangeOrVal = rangeOrValue =='<'? Domain.Type.RANGE :(rangeOrValue =='{' ? Domain.Type.VALUE:null);
        String values = equalSignSplits[1].trim();
        values = values.substring(1,values.length()-1);
        domain.setType(rangeOrVal);
        setValues(domain,values);
        return domain;
    }

    private void setValues(Domain domain, String values){
        String[] splits = values.split(",");
        if(domain.getType() == Domain.Type.RANGE){
            Range range = new Range();
            range.setFrom(splits[0]);
            range.setTo(splits[1]);
            domain.setRange(range);
        }
        else if(domain.getType() == Domain.Type.VALUE){
            List<String> vals = new LinkedList<>();
            for(String s:splits){
                vals.add(s);
            }
            domain.setValues(vals);
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    private String getElement(Element e, String tag){
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }
}
