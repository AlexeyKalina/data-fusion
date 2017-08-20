package app;

public class AttributeReport {

    private String attributeName;
    private AttributeQuality attributeQuality;

    public AttributeReport(String attributeName, AttributeQuality attributeQuality) {

        this.attributeName = attributeName;
        this.attributeQuality = attributeQuality;
    }

    AttributeQuality getAttributeQuality() {
        return attributeQuality;
    }

    String getAttributeName() {
        return attributeName;
    }
}
