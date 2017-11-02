package ppd.thread;

import ppd.domain.GeneralMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Created by toade on 10/30/2017.
 */
public class GeneralMatrixThread<T> implements ThreadMatrix{
    private int start;
    private int end;
    private int n, m;
    private GeneralMatrix<T> a,b,c;
    private List<String> operations = new ArrayList<>();
    private String threadId;
    private BinaryOperator<T> function;

    public GeneralMatrixThread(int start, int end, int n, int m, GeneralMatrix<T> a, GeneralMatrix<T> b, GeneralMatrix<T> c, String threadId, BinaryOperator<T> function) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
        this.operations = operations;
        this.threadId = threadId;
        this.function = function;
    }

    public GeneralMatrixThread(int start, int end, int n, int m, GeneralMatrix<T> a, GeneralMatrix<T> b, GeneralMatrix<T> c) {
        this.start = start;
        this.end = end;
        this.n = n;
        this.m = m;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public GeneralMatrixThread(int start, int end, int n, int m, GeneralMatrix<T> a, GeneralMatrix<T> b, GeneralMatrix<T> c, String threadId) {
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
            if (i>=n) {
                System.out.println("Something went wrong!");
                return;
            }
            c.setElement(i,j,function.apply(a.getElement(i,j), b.getElement(i,j)));
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
