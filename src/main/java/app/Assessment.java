package app;

import object_model.*;
import object_model.Object;

import java.io.IOException;
import java.text.ParseException;

class Assessment {

    Assessment(ObjectMetric objectMetric)
    {
        this.objectMetric = objectMetric;
    }

    private ObjectMetric objectMetric;

    ObjectReport evaluate(Object firstObj, Object secondObj) throws IOException, ParseException {

        return objectMetric.evaluate(firstObj, secondObj);
    }
}
