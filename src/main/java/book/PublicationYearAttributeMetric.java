package book;

import app.AttributeQuality;
import object_model.AttributeMetric;
import app.AttributeReport;
import object_model.Object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicationYearAttributeMetric extends AttributeMetric {

    private static final String attributeName = "publish_date";
    private static final String[] formatStrings = new String[] { "yyyy" };

    @Override
    public AttributeReport evaluate(Object firstObj, Object secondObj) {

        Book firstBook = (Book) firstObj;
        Book secondBook = (Book) secondObj;

        if (firstBook.publicationYear().equalsIgnoreCase("0")) {
            return new AttributeReport(attributeName, AttributeQuality.INCOMPLETE);
        }
        else {
            if (tryParse(firstBook.publicationYear()) == null) {
                return new AttributeReport(attributeName, AttributeQuality.SYNTACTIC_INACCURATE);
            }
            else {
                try {
                if (new SimpleDateFormat(formatStrings[0]).parse(firstBook.publicationYear()).after(new Date())) {
                    return new AttributeReport(attributeName, AttributeQuality.SEMANTIC_INACCURATE);
                }
                else if (firstBook.publicationYear().contentEquals(secondBook.publicationYear())) {
                    return new AttributeReport(attributeName, AttributeQuality.ACCURATE);
                }
                else
                    return new AttributeReport(attributeName, AttributeQuality.UNKNOWN);
                }
                catch (ParseException exception) {
                    System.out.println(exception.getMessage());
                    return new AttributeReport(attributeName, AttributeQuality.UNKNOWN);
                }
            }
        }
    }

    private Date tryParse(String dateString) {
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateString);
            }
            catch (ParseException ignored) {}
        }
        return null;
    }
}
