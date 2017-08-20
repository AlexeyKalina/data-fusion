package book;

public class PublisherAttributeMetric extends TextAttributeMetric {

    private static final String attributeName = "publisher";

    @Override
    protected String getAttributeName() {
        return attributeName;
    }

    @Override
    protected String getAttributeValue(Book book) {
        return book.publisher();
    }
}
