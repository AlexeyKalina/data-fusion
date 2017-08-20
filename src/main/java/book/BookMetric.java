package book;

import app.AttributeReport;
import app.ObjectReport;
import object_model.*;
import object_model.Object;

import java.util.ArrayList;

public class BookMetric extends ObjectMetric {

    BookMetric(ArrayList<AttributeMetric> attributeMetrics) {
        super(attributeMetrics);
    }

    @Override
    public ObjectReport evaluate(Object firstObj, Object secondObj) {

        ArrayList<AttributeReport> reports = new ArrayList<>();

        for (AttributeMetric metric : getAttributeMetrics()) {
            reports.add(metric.evaluate(firstObj, secondObj));
        }

        return new ObjectReport(firstObj, secondObj, reports);
    }
}
