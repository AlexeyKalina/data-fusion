package app;

import object_model.Object;

import java.util.ArrayList;

public class ObjectReport {

    private Object firstObject;
    private Object secondObject;
    private ObjectQuality objectQuality;
    private ArrayList<AttributeReport> attributeReports;


    public ObjectReport(Object firstObject, Object secondObject, ArrayList<AttributeReport> attributeReports) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
        this.attributeReports = attributeReports;
        this.objectQuality = ObjectQuality.UNKNOWN;
    }

    ObjectReport(Object firstObject, Object secondObject, ObjectQuality objectQuality) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
        this.objectQuality = objectQuality;
    }

    public Object getFirstObject() {
        return firstObject;
    }

    public Object getSecondObject() {
        return secondObject;
    }

    ArrayList<AttributeReport> getAttributeReports() {
        return attributeReports;
    }

    ObjectQuality getObjectQuality() {
        return objectQuality;
    }
}
