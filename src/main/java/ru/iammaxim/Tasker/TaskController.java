package ru.iammaxim.Tasker;

/**
 * Created by maxim on 20.08.2016.
 */
public class TaskController {
    private final TaskThread[] threads;
    private int lastThread = -1;
    private int maxIndex;
    private int maxTasksSize = 100;

    public TaskController(int threadCount) {
        maxIndex = threadCount-1;
        threads = new TaskThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TaskThread("TaskThread" + i, i);
            threads[i].start();
        }
    }

    public TaskController(int threadCount, int taskListSize) {
        this.maxTasksSize = taskListSize;
        maxIndex = threadCount-1;
        threads = new TaskThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TaskThread("TaskThread" + i, i, taskListSize);
            threads[i].start();
        }
    }

    public void addTask(Runnable task) {
        int index = getThreadIndex();
        //TODO: delete this
        while (threads[index].getToAddSize() > maxTasksSize) {
            index = getThreadIndex();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads[index].addTask(task);
    }

    public void stop() {
        for (TaskThread thread : threads) {
            thread.addTask(() -> Thread.currentThread().interrupt());
        }
    }

    private int getThreadIndex() {
        if (++lastThread > maxIndex) lastThread = 0;
        return lastThread;
    }
}
