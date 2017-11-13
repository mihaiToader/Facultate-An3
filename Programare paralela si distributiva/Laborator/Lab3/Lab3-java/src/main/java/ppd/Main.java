package ppd;

import ppd.domain.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by toade on 11/4/2017.
 */
public class Main {
    public void testLinkedListV2(int op1, int op2, int op3, WriteToFile logger) throws InterruptedException {
        SortedLinkedListV2<Integer> list = new SortedLinkedListV2<>();
        ThreadListAdd add1 = new ThreadListAdd(list, op1, logger, 1);
        ThreadListAdd add2 = new ThreadListAdd(list, op2, logger, 2);
        ThreadListDelete<Integer> delete = new ThreadListDelete<>(list, op3, logger, 3);
        ThreadListIterate<Integer> iterate = new ThreadListIterate<>(list, logger, 4);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(add1);
        executor.execute(add2);
        executor.execute(delete);
        executor.execute(iterate);

        executor.shutdown();

//        Thread[] threads = new Thread[4];
//        threads[0] = new Thread(add1);
//        threads[1] = new Thread(add2);
//        threads[2] = new Thread(delete);
//        threads[3] = new Thread(iterate);
//
//        for (int i =0; i<4; i++) {
//            threads[i].run();
//        }
//        for (int i =0; i<4; i++) {
//            threads[i].join();
//        }
    }

    public void testLinkedListV1(int op1, int op2, int op3, WriteToFile logger) throws InterruptedException {
        SortedLinkedListV1<Integer> list = new SortedLinkedListV1<>();
        ThreadListAdd add1 = new ThreadListAdd(list, op1, logger, 1);
        ThreadListAdd add2 = new ThreadListAdd(list, op2, logger, 2);
        ThreadListDelete<Integer> delete = new ThreadListDelete<>(list, op3, logger, 3);
        ThreadListIterate<Integer> iterate = new ThreadListIterate<>(list, logger, 4);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(add1);
        executor.execute(add2);
        executor.execute(delete);
        executor.execute(iterate);
        executor.shutdown();

//        Thread[] threads = new Thread[4];
//        threads[0] = new Thread(add1);
//        threads[1] = new Thread(add2);
//        threads[2] = new Thread(delete);
//        threads[3] = new Thread(iterate);
//
//        for (int i =0; i<4; i++) {
//            threads[i].run();
//        }
//        for (int i =0; i<4; i++) {
//            threads[i].join();
//        }
    }

    public static void main(String []args) throws InterruptedException {
        Main m = new Main();
        WriteToFile logger = new WriteToFile("Log.txt");
//        for (int i = 0; i <50; i++)
//        m.testLinkedListV2(10,5,7, logger);
//        m.testLinkedListV1(10,5, 7, logger);
//        m.testLinkedListV1(100,50,50, logger);
        m.testLinkedListV2(100,50,50, logger);
    }
}
