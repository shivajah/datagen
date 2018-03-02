/**
 * Created by shiva on 2/21/18.
 */

import java.io.*;
import java.net.InetSocketAddress;

import Generators.GeneratorManager;
import com.oracle.tools.packager.IOUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import htmlUtil.HtmlUtil;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import schema.Schema;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.net.URLDecoder;

public class Server {

    public static void main(String[] args) throws Exception {

//        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//        server.createContext("/datagen", new MyHandler());
//        server.createContext("/echoGet", new GetHandler());
//        server.setExecutor(null); // creates a default executor
//        server.start();
            Parser parser = new Parser();
            Schema schema = parser.parseConfigFile();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = (HtmlUtil.wrap("<div id =\"mydiv\"><a href=\"#\" id=\"addrows\"> Add New Field </a><form id=\"queryform\" class=\"form-horizontal\" method=\"get\" action=\"/echoGet\">"
                    + "<table id=\"mytable\" border=\"1\">"+HtmlUtil.addAttributeToSchema()+"</table><table><tr><td>Number of records:</td><td><input type=\"text\" name=\"noRows\" placeholder=\"1000\"></td></tr>"
                    + "<tr> <td>File Name:</td><td><input type=\"text\" name=\"file-name\"></td></tr></table><input type=\"submit\" value=\"Submit\"></form></div>")).getBytes();
//          File f = new File("/Users/shiva/DataGen/src/webui/index.html");
//            byte [] response = IOUtils.readFully(f);
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
    static class GetHandler implements HttpHandler{
        public void handle(HttpExchange he) throws IOException {
            Map<String, List<String>> parameters = new LinkedHashMap<String, List<String>>();
            URI requestedUri = he.getRequestURI();
            String query = requestedUri.getRawQuery();
            parseQuery(query, parameters);
            GeneratorManager manager = new GeneratorManager(parameters);
            manager.generateRows();
            // send response
            String response = "";
            for (String key : parameters.keySet())
                response += key + " = " + parameters.get(key) + "\n";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }

    public static void parseQuery(String query, Map<String,
            List<String>> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }
                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }
                List<String> values;
                if (parameters.containsKey(key)) {
                     values = parameters.get(key);
                        values.add(value);
                        parameters.put(key, values);
                    } else {
                    values = new LinkedList<>();
                    values.add(value);
                    parameters.put(key, values);
                }
            }
        }
    }
}
