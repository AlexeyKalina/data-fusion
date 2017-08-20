package book;

import object_model.*;

import java.util.ArrayList;

public class BookFactory extends AbstractFactory {

    @Override
    public ObjectMetric getObjectMetric() {

        ArrayList<AttributeMetric> attributeMetrics = new ArrayList<>();
        attributeMetrics.add(new TitleAttributeMetric());
        attributeMetrics.add(new PublisherAttributeMetric());
        attributeMetrics.add(new PublicationYearAttributeMetric());

        return new BookMetric(attributeMetrics);
    }

    @Override
    public Extractor getExtractor() {

        return new CsvBookExtractor("data\\books.csv");
    }

    @Override
    public Searcher getSearcher() {

        //return new RdfBookSearcher("https://openlibrary.org/api/books?format=json&jscmd=data&bibkeys=ISBN:");

        return new ElasticBookSearcher("localhost", 9300);
    }

    @Override
    public Comparator getComparator() {

        return new BookComparator();
    }
}
