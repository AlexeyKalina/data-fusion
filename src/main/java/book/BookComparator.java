package book;

import app.ComparisonResult;
import object_model.Comparator;
import object_model.Object;

public class BookComparator implements Comparator {

    @Override
    public ComparisonResult compare(Object object, Object anotherObject) {

        Book firstBook = (Book)object;
        Book secondBook = (Book)anotherObject;

        if (secondBook == null)
            return ComparisonResult.MISSING;

        else if (firstBook.title().equalsIgnoreCase(secondBook.title()) &&
                firstBook.author().equalsIgnoreCase(secondBook.author()) &&
                firstBook.publicationYear().equals(secondBook.publicationYear()) &&
                firstBook.publisher().equalsIgnoreCase(secondBook.publisher()))
            return ComparisonResult.EQUIVALENT;

        else
            return ComparisonResult.NONEQUIVALENT;
    }
}
