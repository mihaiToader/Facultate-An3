import thread.AddMatrix;

/**
 * Created by toade on 10/4/2017.
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        Integer a[][] = {
                {1,2,3,4},
                {1,2,3,4},
                {2,1,3,2}
        };
        Integer b[][] = {
                {0,2,3,4},
                {0,2,3,4},
                {2,0,3,2}
        };
        Integer c[][] = new Integer[100][100];

        int p  = 5;
        Thread threads[] = new Thread[p];
        int n = a.length;
        int m = a[0].length;
        int size = n*m;
        int rest = size % p;
        int capacity = size / p;
        int start = 0;
        int end = capacity;
        for (int i=0; i<p; i++) {
            if (rest != 0) {
                rest--;
                end++;
            }
            threads[i] = new Thread(new AddMatrix(start, end, n, m, a, b, c));
            start = end;
            end += capacity;
        }

        for (int i = 0; i<p; i++) {
            threads[i].run();
        }
        for (int i = 0; i<p; i++) {
            threads[i].join();
        }
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                System.out.print(c[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
