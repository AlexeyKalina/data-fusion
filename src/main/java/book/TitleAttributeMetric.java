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

    @Override
    protected boolean checkSemanticAccuracy(Book firstBook, Book secondBook) {

        String[] firstUrls =  YandexFileSearch.INSTANCE.getQueryResults(getAttributeValue(firstBook));
        String[] secondUrls =  YandexFileSearch.INSTANCE.getQueryResults(getAttributeValue(secondBook));

        int countCommon = 0;
        for(int i = 0; i < firstUrls.length; i++) {
            for(int j = 0; j < secondUrls.length; j++) {
                if(firstUrls[i].equals(secondUrls[j])) {
                    countCommon++;
                }
            }
        }

        if (countCommon >= 5)
            return true;

        return false;
    }
}
