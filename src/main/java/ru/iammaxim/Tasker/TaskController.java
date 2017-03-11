package ru.iammaxim.Tasker;

public class TaskController {
    private final TaskThread[] threads;
    private int maxTasksSize = 100;

    public TaskController(int threadCount) {
        threads = new TaskThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TaskThread("TaskThread" + i, i);
            threads[i].start();
        }
    }

    public TaskController(int threadCount, int taskListSize) {
        this.maxTasksSize = taskListSize;
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
        int min_tasks = Integer.MAX_VALUE, index = 0;
        for (int i = threads.length - 1; i >= 0; i--) {
            int task_count = threads[i].getTasksCount();
            if (task_count < min_tasks) {
                min_tasks = task_count;
                index = i;
            }
        }
        return index;
    }
}
