package book;

import object_model.Object;
import object_model.Searcher;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;

public class ElasticBookSearcher implements Searcher {

    private TransportClient client;
    private final static String indexName = "data_fusion";

    ElasticBookSearcher(String addressName, int port) {

        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(addressName), port));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object findObjectById(String id) {

        SearchHits response = client.prepareSearch(indexName)
                .setTypes("books")
                .setQuery(QueryBuilders.matchQuery("isbn_10", id))
                .setFrom(0).setSize(1).setExplain(true)
                .get().getHits();

        return getBook(response);
    }

    @Override
    public Object findObject(Object object) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("title", ((Book)object).title()))
                .must(QueryBuilders.matchPhraseQuery("publishers", ((Book)object).publisher()))
                .must(QueryBuilders.matchQuery("publish_date", ((Book)object).publicationYear()));

        SearchHits response = client.prepareSearch("data_fusion")
                .setTypes("books")
                .setQuery(queryBuilder)
                .setFrom(0).setSize(1).setExplain(true)
                .get().getHits();

        return getBook(response);
    }

    private static Book getBook(SearchHits response) {
        if (response == null || response.totalHits() == 0)
            return null;

        SearchHit hit = response.getAt(0);

        JSONObject jsonBook = new JSONObject(hit.sourceAsString());

        Book book = null;

        try {
            String title = jsonBook.getString("title");
            JSONArray isbns = jsonBook.optJSONArray("isbn_10");
            String isbn = isbns != null && isbns.length() > 0 ? isbns.getString(0) : "";

            String author = jsonBook.optString("by_statement", "-");
            String publicationYear = jsonBook.optString("publish_date", "-");

            JSONArray jPublishers = jsonBook.optJSONArray("publishers");
            String publisher = jPublishers != null && jPublishers.length() > 0 ? jPublishers.getString(0) : "";

            book = new Book(isbn, title, author, publicationYear, publisher);
        } catch (JSONException e) {
            return null;
        }

        return book;
    }
}
