package book;

public class TitleAttributeMetric extends TextAttributeMetric {

    private static final String attributeName = "title";

    @Override
    protected String getAttributeName() {
        return attributeName;
    }

    @Override
    protected String getAttributeValue(Book book) {
        return book.title();
    }

}
