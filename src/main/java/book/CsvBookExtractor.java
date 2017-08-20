package book;

import com.opencsv.CSVReader;
import object_model.Extractor;
import object_model.Object;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvBookExtractor implements Extractor {

    private String path;

    CsvBookExtractor(String path) {

        this.path = path;
    }

    @Override
    public ArrayList<Object> getData() {
        ArrayList<Object> books = new ArrayList<>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path), ';');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] newRecord;
        try {
            assert reader != null;
            reader.readNext();
            while ((newRecord = reader.readNext()) != null) {
                books.add(new Book(newRecord[0], newRecord[1], newRecord[2], newRecord[3], newRecord[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
