package object_model;

import app.AttributeReport;

public abstract class AttributeMetric {

    public abstract AttributeReport evaluate(Object firstObj, Object secondObj);
}
