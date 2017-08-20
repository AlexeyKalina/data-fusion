package book;

import object_model.Object;
import object_model.Searcher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RdfBookSearcher implements Searcher {

    private String url;

    public RdfBookSearcher(String url) {
        this.url = url;
    }

    @Override
    public Object findObject(Object object) {
        return null;
    }

    @Override
    public Object findObjectById(String id) {

        String response = null;
        try {
            response = sendGet(url + id);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
        if (response.equals("{}"))
            return null;
        JSONObject jsonBook = new JSONObject(response).getJSONObject("ISBN:" + id);

        String title = jsonBook.getString("title");
        String author = jsonBook.optString("by_statement", "-");
        String publicationYear = jsonBook.optString("publish_date", "-");

        JSONArray jPublishers = jsonBook.optJSONArray("publishers");
        String publisher = jPublishers != null && jPublishers.length() > 0 ? jPublishers.optJSONObject(0).optString("name") : null;

        return new Book(id, title, author, publicationYear, publisher);
    }

    private String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
