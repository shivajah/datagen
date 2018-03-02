package htmlUtil;

import Constants.DataTypes;

import java.util.Map;
import java.util.Iterator;

/**
 * Created by shiva on 2/22/18.
 */
public  class HtmlUtil {
    public static String wrap(String content){
        return "<html><head>"
                + "<style>#mydiv {\n" + "    position:fixed;\n" + "    top: 20%;\n" + "    left: 35%;\n"
                + "    margin-top: -9em; /*set to a negative number 1/2 of your height*/\n"
                + "    margin-left: -15em; /*set to a negative number 1/2 of your width*/\n"
                + "    border: 1px solid #ccc;\n" + "    background-color: #f3f3f3;\n" + "}"
                + "table , td, tr{ margin: 10px;}"
                + "</style>"
                + "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"
                + "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n"
                + "<script type=\"text/javascript\">"
                + "function SomeDeleteRowFunction(o) {\n"
                + "var p=o.parentNode.parentNode;\n"
                + "p.parentNode.removeChild(p);}\n"+
                "$(document).ready(function() {"+
                "$(\"#addrows\").click(function () {"+
                "$(\"#mytable\").each(function () {"+
                "var tds = '<tr>';"+
                "jQuery.each($('tr:last td', this), function () {"+
                    "tds += '<td>' + $(this).html() + '</td>';"+
                "});"+
                "tds += '</tr>';"+
                "if ($('tbody', this).length > 0) {"+
                    "$('tbody', this).append(tds);"+
                "} else {"+
                    "$(this).append(tds);}});});});</script></head><body>"+content+"</body></html>";
    }
public static String addAttributeToSchema(){
       return  "<thead><th>Field Name</th><th>Type</th><th colspan=\"2\">Range</th></thead><tbody>"
            + "<tr><td> <input type=\"text\" name=\"id\" placeholder=\"id\"></td><td>"+ HtmlUtil.generateDropDownList(
            DataTypes.toMap())+"</td><td>From: <input type=\"text\" name=\"from\" placeholder=\"10\"></td> <td>To: <input type=\"text\" name=\"to\" placeholder=\"10\"></td>"
               + "<td><button type=\"button\" onclick=\"SomeDeleteRowFunction(this)\">Remove field</button></td></tr></tbody>";
}
   public static String generateDropDownList(Map<String,String> value_content){
       String st = "<select name=\"type\">";
       Iterator it = value_content.entrySet().iterator();
       while (it.hasNext()) {
           Map.Entry pair = (Map.Entry)it.next();
           st += "<option value=\""+pair.getKey().toString()+"\">"+pair.getValue().toString()+"</option>";
           it.remove(); // avoids a ConcurrentModificationException
       }
       st += "</select>";
       return st;
   }
}
