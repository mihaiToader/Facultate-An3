package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by toade on 11/5/2017.
 */
public class ThreadListDelete<T> implements Runnable{
    private LinkedList<T> list;
    private Integer nrOperations;
    private ThreadWriteToFile looger;
    private Integer nrThread;

    public ThreadListDelete(LinkedList<T> list, Integer nrOperations, ThreadWriteToFile looger, Integer nrThread) {
        this.list = list;
        this.nrOperations = nrOperations;
        this.looger = looger;
        this.nrThread = nrThread;
        looger.used();
    }

    @Override
    public void run() {
        for (int i = 0; i < nrOperations; i++) {
            LocalDateTime startTime = LocalDateTime.now();
            try {
                T val = list.delete(0);
                LocalDateTime endTime = LocalDateTime.now();
                looger.add(String.format("[%s - %s] # Delete # Thread: %d # Nr operation: %d # Sec %d # Millis %d # Value %s\n",
                        startTime, endTime, nrThread, i,
                        ChronoUnit.SECONDS.between(startTime, endTime),
                        ChronoUnit.MILLIS.between(startTime, endTime), val));
            } catch (IndexOutOfBoundsException e) {
                LocalDateTime endTime = LocalDateTime.now();
                looger.add(String.format("[%s - %s] # Delete error position not found# Thread: %d # Nr operation: %d # Sec %d # Millis %d # Value %d\n",
                        startTime, endTime, nrThread, i,
                        ChronoUnit.SECONDS.between(startTime, endTime),
                        ChronoUnit.MILLIS.between(startTime, endTime), 0));
            }
        }
        looger.unused();
    }
}
