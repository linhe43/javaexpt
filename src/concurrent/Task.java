package concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class Task implements Runnable {

    private final int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task-" + id + " is running ...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task-" + id + " finished");
    }

    public static void main(String[] args) {

//        Executor executor = new ThreadPoolExecutor();
//        ((ThreadPoolExecutor) executor).getCorePoolSize()
        Task t1 = new Task(1);
        Task t2 = new Task(2);

        t1.run();
        t2.run();
    }

}
