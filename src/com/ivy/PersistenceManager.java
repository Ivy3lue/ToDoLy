package com.ivy;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads and writes data to file
 */
public class PersistenceManager {

    private Path path;
    private Gson gson;

    /**
     * Constructor
     *
     * @param path the path to the file that the PersistenceManager will work with
     */
    public PersistenceManager(String path) {
        this.path = Paths.get(path);
        gson = new Gson();
    }

    /**
     * Writes to file
     * @param list the list of tasks to be written to file
     */
    public void writeToFile(List<Task> list) {
        try {
            Files.write(path, list.stream().map(gson::toJson).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from file
     * @return a list of tasks read from file
     */
    public List<Task> readFromFile() {
        //       List<String> lines = null;
//        try {
//            lines = Files.readAllLines(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lines.stream().map(line -> gson.fromJson(line, Task.class)).collect(Collectors.toList());
        return mockData();
    }

    //TODO remove method

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