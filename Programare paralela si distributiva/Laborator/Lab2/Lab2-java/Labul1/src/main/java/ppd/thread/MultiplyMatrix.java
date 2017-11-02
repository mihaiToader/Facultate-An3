package ppd.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toade on 10/4/2017.
 */
public class MultiplyMatrix implements ThreadMatrix {

    private int start;
    private int end;
    private int n, m;
    private Integer[][] a,b,c;
    private List<String> operations = new ArrayList<>();
    private String threadId;

    public MultiplyMatrix(int start, int end, int n, int m, Integer[][] a, Integer[][] b, Integer[][] c) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public MultiplyMatrix(int start, int end, int n, int m, Integer[][] a, Integer[][] b, Integer[][] c, String threadId) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        operations.add("Number of operations: " + (end - start));
        int i = start / m;
        int j = start % m;
        for (int k = start; k < end; k++) {
            if (j >= m) {
                j = 0;
                i++;
            }
            c[i][j] = 0;
            for (int w = 0; w < n; w++) {
                c[i][j] += a[i][w] * b[w][j];
            }
            j++;
        }
    }

    @Override
    public List<String> getOperations() {
        return operations;
    }

    @Override
    public String getThreadName() {
        return threadId;
    }
}
