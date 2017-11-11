package domain;

import java.io.*;
import java.util.*;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * Created by toade on 11/5/2017.
 */
public class ThreadWriteToFile implements Runnable {
    private String file;
    private String content = "";
    private Integer used = 0;
    private boolean stop = false;

    public ThreadWriteToFile(String file) {
        this.file = file;
    }

    public synchronized void add(String s) {
        content += s;
    }

    public synchronized void used() {
        used++;
    }

    public synchronized void unused() {
        used--;
    }

    public synchronized void setUsed(Integer used) {
        this.used = used;
    }

    public boolean isStop() {
        return stop;
    }

    public synchronized void setStop(boolean stop) {
        this.stop = stop;
    }

    public synchronized boolean isUsed() {
        return used > 0;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
