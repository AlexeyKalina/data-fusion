package app;

import book.BookFactory;
import joptsimple.internal.Strings;
import object_model.*;
import object_model.Object;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataFusion {

    private int chunksCount = 10;
    public static Writer output;

    public static void main(String[] args) throws Exception {

        new DataFusion().run();
    }

    private void run() throws Exception {

        AbstractFactory factory = new BookFactory();

        Extractor extractor = factory.getExtractor();
        ArrayList<Object> objects = extractor.getData();
        int counter = 0;

        output = new BufferedWriter(new FileWriter("searchQueriesPublisher.txt", true));


        Fusion fusion = new Fusion(objects.size(), chunksCount, factory.getComparator(), factory.getSearcher(), factory.getObjectMetric());

        for (Object object : objects) {

            counter++;
            if (counter % 1000 == 0)
                System.out.println(counter);
            fusion.Fuse(object);
        }

        output.close();

        HashMap<KeyQuality, Integer>[] results = getResults(fusion);


        showResults(results);
    }

    private HashMap<KeyQuality, Integer>[] getResults(Fusion fusion)
    {

        HashMap<KeyQuality, Integer>[] results = new HashMap[chunksCount];

        for (int i = 0; i < chunksCount; i++) {

            results[i] = new HashMap<>();

            for (ObjectReport rep : fusion.Reports()[i]) {

                if (rep.getObjectQuality() != ObjectQuality.UNKNOWN) {

                    KeyQuality key = new KeyQuality( "book", rep.getObjectQuality().toString());
                    int count = results[i].containsKey(key) ? results[i].get(key) : 0;
                    results[i].put(key, count + 1);
                }
                else {

                    for (AttributeReport atrRep : rep.getAttributeReports()) {

                        KeyQuality key = new KeyQuality(atrRep.getAttributeName(), atrRep.getAttributeQuality().toString());
                        int count = results[i].containsKey(key) ? results[i].get(key) : 0;
                        results[i].put(key, count + 1);
                    }
                }
            }
        }

        return results;
    }

    private void showResults(HashMap<KeyQuality, Integer>[] results)
    {
        try {
            PrintWriter writer = new PrintWriter("results.csv", "UTF-8");

            for (int i = 0; i < chunksCount; i++) {

                writer.println("Quality information for " + i + " chunk");

                for (KeyQuality result : results[i].keySet()) {

                    writer.println(result + " " + results[i].get(result));
                }

                writer.println(Strings.repeat('-', 20));
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private class KeyQuality {

        private final String name;
        private final String quality;

        public KeyQuality(String name, String quality) {
            this.name = name;
            this.quality = quality;
        }

        @Override
        public boolean equals(java.lang.Object o) {
            if (this == o) return true;
            if (!(o instanceof KeyQuality)) return false;
            KeyQuality key = (KeyQuality) o;
            return name == key.name && quality == key.quality;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + quality.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return name + " " + quality;
        }

    }
}
