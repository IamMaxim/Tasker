package ru.iammaxim.Tasker;

public class Test {
    public static void main(String[] args) {
        TaskController controller = new TaskController(4);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            controller.addTask(()-> {
                try {
                    Thread.sleep(100 * finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        controller.stop();
    }
}
