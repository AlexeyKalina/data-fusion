package book;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class YandexFileSearch {

    private Map<String, String> queryTitleToFileMap = new HashMap<>();
    private Map<String, String> queryPublisherToFileMap = new HashMap<>();
    private static final String pathToTitleMapping = "c:\\Users\\Alexey\\Documents\\Programming\\University\\Researches\\DataFusion\\searchQueriesTitleMapping.txt";
    private static final String pathToPublisherMapping = "c:\\Users\\Alexey\\Documents\\Programming\\University\\Researches\\DataFusion\\searchQueriesPublisherMapping.txt";
    private static final String pathToTitleResults = "c:\\Users\\Alexey\\Documents\\Programming\\University\\Researches\\yandexQueries\\";
    private static final String pathToPublisherResults = "c:\\Users\\Alexey\\Documents\\Programming\\University\\Researches\\yandexPublisherQueries\\";
    private static final String xpathQuery = "//yandexsearch/response/results/grouping/group/doc/url";

    public static final YandexFileSearch INSTANCE = new YandexFileSearch();

    private YandexFileSearch() {

        LoadMapping(queryTitleToFileMap, pathToTitleMapping);
        LoadMapping(queryPublisherToFileMap, pathToPublisherMapping);
    }

    public String[] getQueryResults(String query, String attributeName) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            if (attributeName.equals("title")) {
                String file = queryTitleToFileMap.get(query);
                if (file == null)
                    return null;
                doc = builder.parse(String.format("%s%s", pathToTitleResults, file));
            }
            else {
                String file = queryPublisherToFileMap.get(query);
                if (file == null)
                    return null;
                doc = builder.parse(String.format("%s%s", pathToPublisherResults, file));
            }
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xpathQuery);

            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            return convertToArray(nl);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void LoadMapping(Map<String, String> queryToFileMap, String pathToMapping){
        String query, fileName;
        try (
                InputStream fis = new FileInputStream(pathToMapping);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {
            while ((query = br.readLine()) != null) {

                fileName = br.readLine();
                queryToFileMap.put(query, fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] convertToArray(NodeList list)
    {
        int length = list.getLength();
        String[] copy = new String[length];

        for (int i = 0; i < length; i++) {

            copy[i] = list.item(i).getTextContent();
        }

        return copy;
    }
}
