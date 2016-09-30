package ru.iammaxim.Tasker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maxim on 20.08.2016.
 */
public class TaskThread extends Thread {
    public final int index;
    private final LinkedList<Runnable> toAdd = new LinkedList<Runnable>();
    private int tasksSize = 0;
    private final List<Runnable> tasks = new ArrayList<Runnable>(tasksSize);

    public int getToAddSize() {
        return toAdd.size();
    }

    public TaskThread(String name, int index) {
        this(name, index, 100);
    }

    public TaskThread(String name, int index, int maxTasksSize) {
        super(name);
        this.index = index;
        tasksSize = maxTasksSize;
    }

    public synchronized void addTask(Runnable task) {
        toAdd.add(task);
        notify();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (this) {
                if (toAdd.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int size = toAdd.size();
                for (int i = 0; i < size && i < tasksSize; i++) {
                    tasks.add(toAdd.poll());
                }
            }
            tasks.forEach(Runnable::run);
            tasks.clear();
        }
    }
}
