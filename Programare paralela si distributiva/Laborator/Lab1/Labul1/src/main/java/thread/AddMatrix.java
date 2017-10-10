package thread;

/**
 * Created by toade on 10/4/2017.
 */
public class AddMatrix implements Runnable {

    private int start;
    private int end;
    private int n, m;
    private int[][] a,b,c;

    public AddMatrix(int start, int end, int n, int m, int[][] a, int[][] b, int[][] c) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        int i = start / m;
        int j = start % m;
        for (int k = start; k < end; k++) {
            if (j >= m) {
                j = 0;
                i++;
            }
            if (i>=n) {
                System.out.println("Something went wrong!");
                return;
            }
            c[i][j] = a[i][j] + b[i][j];
            System.out.println("(" + i + " " + j + ")");
            j++;
        }
    }
}
