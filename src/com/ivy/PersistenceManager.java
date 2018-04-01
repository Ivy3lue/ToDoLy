package com.ivy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Reads and writes data to file
 */
public class PersistenceManager {

    private String path;
    private Gson gson;

    /**
     * Constructor
     *
     * @param path the path to the file that the PersistenceManager will work with
     */
    public PersistenceManager(String path) {
        this.path = path;
        gson = new Gson();
    }

    /**
     * Writes to file
     *
     * @param list the list of tasks to be written to file
     */
    public void writeToFile(List<Task> list) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(list, new TypeToken<List<Task>>() {
            }.getType(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from file
     *
     * @return a list of tasks read from file
     */
    public List<Task> readFromFile() {
        List<Task> tasks = new ArrayList<>();
        try (FileReader reader = new FileReader(path)) {
            tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    /**
     * Adds mock data for developing and testing purposes.
     */
    public List<Task> mockData() {
        List<Task> mockList = new ArrayList<>();
        mockList.add(new Task("first mock task", new Date(1524002400000L), null, false));
        mockList.add(new Task("second mock task", new Date(1537999200000L), "mock", true));
        mockList.add(new Task("third mock task", null, "mock", false));
        mockList.add(new Task("fourth mock task", new Date(1520031600000L), "mock", false));
        mockList.add(new Task("fifth mock task", null, "edited", true));
        mockList.add(new Task("sixth mock task", new Date(1523484000000L), "edited", false));
        return mockList;
    }
}