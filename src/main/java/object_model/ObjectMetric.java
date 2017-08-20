package object_model;

import app.ObjectReport;

import java.util.ArrayList;

public abstract class ObjectMetric {

    private ArrayList<AttributeMetric> attributeMetrics;

    protected ObjectMetric(ArrayList<AttributeMetric> attributeMetrics) {

        this.attributeMetrics = attributeMetrics;
    }

    protected ArrayList<AttributeMetric> getAttributeMetrics() {
        return attributeMetrics;
    }

    public abstract ObjectReport evaluate(Object firstObj, Object secondObj);
}
