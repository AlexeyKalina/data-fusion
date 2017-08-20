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

        if (getAttributeValue(firstBook).toLowerCase().contains(secondBook.title().toLowerCase())) {
            return new AttributeReport(getAttributeName(), AttributeQuality.ACCURATE);
        }

        Levenshtein levenshtein = new Levenshtein();

        double distance = (levenshtein.distance(firstBook.title().toLowerCase(), secondBook.title().toLowerCase()));

        if (distance > threshold) {
            if (secondBook.title().toLowerCase().contains(firstBook.title().toLowerCase())) {

                return new AttributeReport(getAttributeName(), AttributeQuality.INCOMPLETE);
            }
            else {
                //todo: semantic analysis

                return new AttributeReport(getAttributeName(), AttributeQuality.UNKNOWN);
            }
        }
        else {

            JLanguageTool language = new JLanguageTool(new AmericanEnglish());
            try {
                List<RuleMatch> matchesFirst = language.check(firstBook.title());
                List<RuleMatch> matchesSecond = language.check(secondBook.title());

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
}
