package ru.iammaxim.Tasker;

import ru.iammaxim.Tasker.TaskController;

/**
 * Created by maxim on 20.08.2016.
 */
public class Test {
    private static final int arrSize = 16, max = 100000000;

    public static void main(String[] args) {
        TaskController controller = new TaskController(16);
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            controller.addTask(()-> {
                int[] arr = new int[arrSize];

                for (int i1 = 0; i1 < arr.length; i1++) {
                    arr[i1] = 0;
                }

                loopOver(arr, 0, finalI);
            });
            System.out.println("added task " + i);
        }
        controller.stop();
    }

    private static void loopOver(int[] arr, int currentIndex, int taskNumber) {
        if (currentIndex >= arrSize) {
            return;
        }
        while(arr[currentIndex] < max) {
            loopOver(arr, currentIndex + 1, taskNumber);
            arr[currentIndex]++;
            //System.out.println(currentIndex + " " + arr[currentIndex]);
        }
        if (currentIndex == 0)
            System.out.println(Thread.currentThread().getName() + " completed task #" + taskNumber);
    }
}
