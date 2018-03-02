import Constants.DataTypes;
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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiva on 3/1/18.
 */
public class Parser {
    public Schema parseConfigFile(){
        Schema schema  =  new Schema();
        try {
            File inputFile = new File("./src/configs/config");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =  dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("field");
            Field field;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                field = new Field();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    field.setPosition(Integer.parseInt(eElement.getAttribute("position")));
                    field.setName(getElement(eElement,"name"));
                    String t = getElement(eElement,"type");
                    DataTypes.DataType type = t.equalsIgnoreCase("string")? DataTypes.DataType.STRING :(t.equalsIgnoreCase("integer")?
                            DataTypes.DataType.INTEGER: DataTypes.DataType.UNKNOWN);
                    field.setType(type);
                    field.setDeclared(getElement(eElement,"declared").equalsIgnoreCase("true"));
                    schema.getFields().add(field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schema;
    }
    //TODO
    public List<Domain> parseDomain(){
        List<Domain> domains = new LinkedList<>();
        try {
            File inputFile = new File("./src/configs/domain");
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                domains.add(parse(line));
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
            List<Object> vals = new LinkedList<>();
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
