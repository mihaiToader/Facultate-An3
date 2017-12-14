package model;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by toade on 11/5/2017.
 */
public class WriteToFile {
    private String file;
    private String content = "";
    private Integer used = 0;
    private boolean stop = false;

    public WriteToFile(String file) {
        this.file = file;
    }

    public synchronized void log(String s) {
        Date date = new Date();
        content += "" + date + " |===| " + s + "\r\n";
    }

    public synchronized void logWithoutDate(String s) {
        content += s + "\r\n";
    }

    public synchronized void used() {
        used++;
    }

    public synchronized void unused() {
        used--;
    }

    public synchronized boolean isUsed() {
        return used > 0;
    }

    public synchronized void write() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
            this.content = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
