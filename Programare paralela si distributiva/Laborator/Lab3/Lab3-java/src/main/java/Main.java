import domain.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by toade on 11/4/2017.
 */
public class Main {
    public void testLinkedListV2() throws InterruptedException {
        SortedLinkedListV2<Integer> list = new SortedLinkedListV2<>();
        ThreadWriteToFile logger = new ThreadWriteToFile("Log.txt");
        ThreadListAdd add1 = new ThreadListAdd(list, 10, logger, 1);
        ThreadListAdd add2 = new ThreadListAdd(list, 5, logger, 2);
        ThreadListDelete<Integer> delete = new ThreadListDelete<>(list, 7, logger, 3);
        ThreadListIterate<Integer> iterate = new ThreadListIterate<>(list, logger, 4);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(logger);
        executor.execute(add1);
        executor.execute(add2);
        executor.execute(delete);
        executor.execute(iterate);

        executor.shutdown();
    }

    public void testLinkedListV1() throws InterruptedException {
        SortedLinkedListV1<Integer> list = new SortedLinkedListV1<>();
        ThreadWriteToFile logger = new ThreadWriteToFile("Log.txt");
        ThreadListAdd add1 = new ThreadListAdd(list, 10, logger, 1);
        ThreadListAdd add2 = new ThreadListAdd(list, 5, logger, 2);
        ThreadListDelete<Integer> delete = new ThreadListDelete<>(list, 7, logger, 3);
        ThreadListIterate<Integer> iterate = new ThreadListIterate<>(list, logger, 4);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(logger);
        executor.execute(add1);
        executor.execute(add2);
        executor.execute(delete);
        executor.execute(iterate);

        executor.shutdown();
    }

    public static void main(String []args) throws InterruptedException {
        Main m = new Main();
//        m.testLinkedListV2();
        m.testLinkedListV1();

    }
}
