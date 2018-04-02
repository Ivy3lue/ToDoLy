package com.ivy.manager;

import com.ivy.model.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TaskManagerTest {

    private TaskManager taskManager;

    private final Task firstTask = new Task("first mock task", new Date(1524002400000L), null, false);
    private final Task secondTask = new Task("second mock task", new Date(1537999200000L), "mock", true);
    private final Task thirdTask = new Task("third mock task", null, "mock", false);
    private final Task fourthTask = new Task("fourth mock task", new Date(1520031600000L), "mock", false);
    private final Task fifthTask = new Task("fifth mock task", null, "edited", true);
    private final Task sixthTask = new Task("sixth mock task", new Date(1523484000000L), "edited", false);

    @Before
    public void SetUp() {
        taskManager = new TaskManager();
        taskManager.addTask(firstTask);
        taskManager.addTask(secondTask);
        taskManager.addTask(thirdTask);
        taskManager.addTask(fourthTask);
        taskManager.addTask(fifthTask);
        taskManager.addTask(sixthTask);
    }

    @Test
    public void getTasks() {
        List<Task> expected = new ArrayList<>();
        expected.add(fourthTask);
        expected.add(sixthTask);
        expected.add(firstTask);
        expected.add(secondTask);
        expected.add(thirdTask);
        expected.add(fifthTask);
        List<Task> result = taskManager.getTasks();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void getTasksWhenEmpty() {
        taskManager.removeAll();
        List<Task> result = taskManager.getTasks();
        assertArrayEquals(new ArrayList<>().toArray(), result.toArray());
    }

    @Test
    public void getCompletedTasks() {
        List<Task> expected = new ArrayList<>();
        expected.add(secondTask);
        expected.add(fifthTask);
        List<Task> result = taskManager.getCompletedTasks();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void getUncompletedTasks() {
        List<Task> expected = new ArrayList<>();
        expected.add(fourthTask);
        expected.add(sixthTask);
        expected.add(firstTask);
        expected.add(thirdTask);
        List<Task> result = taskManager.getUncompletedTasks();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void getOverdueTasks() {
        Task task = new Task("overdue task", new Date(1504000400000L), "edited", false);
        List<Task> expected = new ArrayList<>();
        expected.add(task);

        taskManager.removeAll();
        taskManager.addTask(task);

        List<Task> result = taskManager.getOverdueTasks();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void addTask() {
        assert (taskManager.getTasks().size() == 6);
        taskManager.addTask(new Task("added task", new Date(), "added", false));
        assert (taskManager.getTasks().size() == 7);
    }

    @Test
    public void addTaskNull() {
        assert (taskManager.getTasks().size() == 6);
        taskManager.addTask(new Task(null, null, "added", true));
        assert (taskManager.getTasks().size() == 6);
    }

    @Test
    public void findTaskExisting() {
        Task foundTask = taskManager.findTask("first mock task");
        assertEquals(firstTask, foundTask);
    }

    @Test
    public void findTaskNonExisting() {
        Task foundTask = taskManager.findTask("nonexisting task");
        assertNull(foundTask);
    }

    @Test
    public void removeExisting() {
        List<Task> expected = new ArrayList<>();
        expected.add(fourthTask);
        expected.add(sixthTask);
        expected.add(firstTask);
        expected.add(secondTask);
        expected.add(fifthTask);
        Task taskToRemove = taskManager.findTask("third mock task");
        taskManager.remove(taskToRemove);
        List<Task> result = taskManager.getTasks();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void removeNonExisting() {
        List<Task> expected = new ArrayList<>();
        expected.add(firstTask);
        expected.add(secondTask);
        expected.add(thirdTask);
        expected.add(fourthTask);
        expected.add(fifthTask);
        expected.add(sixthTask);
        Task taskToRemove = taskManager.findTask("third");
        taskManager.remove(taskToRemove);
        assert (expected.toArray() == taskManager.getTasks().toArray());
    }

    @Test
    public void removeAll() {
        assert (6 == taskManager.getTasks().size());
        taskManager.removeAll();
        assert (0 == taskManager.getTasks().size());
    }

    @Test
    public void removeAllWhenEmpty() {
        assertTrue(taskManager.removeAll());
        taskManager.removeAll();
        assertFalse(taskManager.removeAll());
    }

    @Test
    public void removeAllWithIsCompletePredicate() {
        assert (6 == taskManager.getTasks().size());
        Predicate<Task> predicate = task -> task.isComplete;
        taskManager.removeAll(predicate);
        assertEquals(4, taskManager.getTasks().size());
    }

    @Test
    public void removeAllWithProjectPredicate() {
        assert (6 == taskManager.getTasks().size());
        String projectName = "mock";
        Predicate<Task> predicate = (task -> projectName.equalsIgnoreCase(task.project));
        taskManager.removeAll(predicate);
        assertEquals(3, taskManager.getTasks().size());
    }
}