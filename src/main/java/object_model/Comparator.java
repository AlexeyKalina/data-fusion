package object_model;

import app.ComparisonResult;

public interface Comparator {

    ComparisonResult compare(Object object, Object anotherObject);
}
