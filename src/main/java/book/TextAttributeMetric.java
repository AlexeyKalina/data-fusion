package book;

import app.AttributeQuality;
import info.debatty.java.stringsimilarity.Levenshtein;
import object_model.AttributeMetric;
import app.AttributeReport;
import object_model.Object;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public abstract class TextAttributeMetric extends AttributeMetric {

    private static final int threshold = 5;

    @Override
    public AttributeReport evaluate(Object firstObj, Object secondObj) {
        Book firstBook = (Book) firstObj;
        Book secondBook = (Book) secondObj;

        if (getAttributeValue(firstBook).toLowerCase().contains(getAttributeValue(secondBook).toLowerCase())) {
            return new AttributeReport(getAttributeName(), AttributeQuality.ACCURATE);
        }

        Levenshtein levenshtein = new Levenshtein();

        double distance = (levenshtein.distance(getAttributeValue(firstBook).toLowerCase(), getAttributeValue(secondBook).toLowerCase()));

        if (distance > threshold) {
            if (getAttributeValue(secondBook).toLowerCase().contains(getAttributeValue(firstBook).toLowerCase())) {

                return new AttributeReport(getAttributeName(), AttributeQuality.INCOMPLETE);
            }
            else {

                if (!checkSemanticAccuracy(firstBook, secondBook)) {
                    return new AttributeReport(getAttributeName(), AttributeQuality.SEMANTIC_INACCURATE);
                }

                return new AttributeReport(getAttributeName(), AttributeQuality.UNKNOWN);
            }
        }
        else {

            JLanguageTool language = new JLanguageTool(new AmericanEnglish());
            try {
                List<RuleMatch> matchesFirst = language.check(getAttributeValue(firstBook));
                List<RuleMatch> matchesSecond = language.check(getAttributeValue(secondBook));

                if (matchesFirst.size() == 0) {
                    return new AttributeReport(getAttributeName(), AttributeQuality.ACCURATE);
                } else if (matchesSecond.size() == 0) {
                    return new AttributeReport(getAttributeName(), AttributeQuality.SYNTACTIC_INACCURATE);
                } else {
                    return new AttributeReport(getAttributeName(), AttributeQuality.UNKNOWN);
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                return new AttributeReport(getAttributeName(), AttributeQuality.UNKNOWN);
            }
        }
    }

    protected abstract String getAttributeName();

    protected abstract String getAttributeValue(Book book);

    protected boolean checkSemanticAccuracy(Book firstBook, Book secondBook) {

        String[] firstUrls =  YandexFileSearch.INSTANCE.getQueryResults(getAttributeValue(firstBook), getAttributeName());
        String[] secondUrls =  YandexFileSearch.INSTANCE.getQueryResults(getAttributeValue(secondBook), getAttributeName());

        if (firstUrls == null || secondUrls == null)
            return false;

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
