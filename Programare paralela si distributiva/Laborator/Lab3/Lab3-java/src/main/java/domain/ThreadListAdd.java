package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by toade on 11/5/2017.
 */
public class ThreadListAdd implements Runnable{
    private LinkedList<Integer> list;
    private Integer nrOperations;
    private ThreadWriteToFile looger;
    private Integer nrThread;

    public ThreadListAdd(LinkedList<Integer> list, Integer nrOperations, ThreadWriteToFile looger, Integer nrThread) {
        this.list = list;
        this.nrOperations = nrOperations;
        this.looger = looger;
        this.nrThread = nrThread;
        looger.used();
    }

    @Override
    public void run() {
        for (int i = 0; i< nrOperations; i++) {
            int value = ThreadLocalRandom.current().nextInt(0, 10 + 1);

            LocalDateTime startTime = LocalDateTime.now();
            list.add(value);
            LocalDateTime endTime = LocalDateTime.now();
            looger.add(String.format("[%s - %s] # Add # Thread: %d # Nr operation: %d # Sec %d # Millis %d # Value %d\n",
                    startTime, endTime, nrThread, i,
                    ChronoUnit.SECONDS.between(startTime, endTime),
                    ChronoUnit.MILLIS.between(startTime, endTime), value));
        }
        looger.unused();
    }
}
