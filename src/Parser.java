import Constants.DataTypes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import schema.Field;
import schema.Schema;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by shiva on 3/1/18.
 */
public class Parser {
    public Schema parseConfigFile(){
        Schema schema  =  new Schema();
        try {
            File inputFile = new File("/Users/shiva/DataGen/src/configs/config");
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
//public Domain parseDomain(){};
    private String getElement(Element e, String tag){
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }
}
