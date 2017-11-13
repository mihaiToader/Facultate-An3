package ppd.domain;

import java.io.*;

import static java.lang.Thread.sleep;

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

    public synchronized void add(String s) {
        content += s;
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
