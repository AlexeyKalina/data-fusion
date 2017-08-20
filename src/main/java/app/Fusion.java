package app;

import object_model.*;
import object_model.Object;
import java.util.ArrayList;

class Fusion {
    private int step;
    private int counter = 0;
    private int index = -1;

    private ArrayList[] objectReports;
    private Comparator comparator;
    private Searcher searcher;
    private Assessment assessment;

    Fusion(int size, int chunksCount, Comparator comparator, Searcher searcher, ObjectMetric objectMetric)
    {
        this.step = size / chunksCount + 1;
        this.comparator = comparator;
        this.searcher = searcher;
        assessment = new Assessment(objectMetric);
        objectReports = new ArrayList[chunksCount];
    }

    void Fuse(Object object) throws Exception {

        Object anotherObject = searcher.findObjectById(object.getId());

        if (counter == 0) {
            index++;
            objectReports[index] = new ArrayList<>();
        }

        switch (comparator.compare(object, anotherObject)) {
            case MISSING:

                Object misObject = searcher.findObject(object);

                if (misObject != null)
                    objectReports[index].add(new ObjectReport(object, misObject, ObjectQuality.MISSING));
                else
                    objectReports[index].add(new ObjectReport(object, misObject, ObjectQuality.MISSING_BOTH));
                break;

            case EQUIVALENT:

                objectReports[index].add(new ObjectReport(object, anotherObject, ObjectQuality.EQUIVALENT));
                break;

            case NONEQUIVALENT:

                ObjectReport report = assessment.evaluate(object, anotherObject);
                objectReports[index].add(report);
                break;
        }
        counter++;
        if (counter == step) {
            counter = 0;
        }
    }

    ArrayList<ObjectReport>[] Reports()  {
        return objectReports;
    }
}
