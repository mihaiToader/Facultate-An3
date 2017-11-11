package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Thread.sleep;

/**
 * Created by toade on 11/5/2017.
 */
public class ThreadListIterate<T> implements Runnable{
    private LinkedList<T> list;
    private ThreadWriteToFile looger;
    private Integer nrThread;

    public ThreadListIterate(LinkedList<T> list, ThreadWriteToFile looger, Integer nrThread) {
        this.list = list;
        this.looger = looger;
        this.nrThread = nrThread;
    }

    @Override
    public void run() {
        while (looger.isUsed()) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDateTime startTime = LocalDateTime.now();
            String res = list.iterate();
            LocalDateTime endTime = LocalDateTime.now();
            looger.add(String.format("[%s - %s] # Iterate # Thread: %d # Sec %d # Millis %d # Values: %s\n",
                    startTime, endTime, nrThread,
                    ChronoUnit.SECONDS.between(startTime, endTime),
                    ChronoUnit.MILLIS.between(startTime, endTime), res));
        }
        looger.setStop(true);
    }
}
