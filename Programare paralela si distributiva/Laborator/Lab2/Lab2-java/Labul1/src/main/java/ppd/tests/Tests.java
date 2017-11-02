package ppd.tests;

import ppd.view.ConsoleView;

/**
 * Created by toade on 10/14/2017.
 */
public class Tests {
    private ConsoleView consoleView;

    public Tests(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void setConsoleView(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    private void runIfAddCorectTests() {
        System.out.println("Run tests for adding rightness!");
        try {
            System.out.println("Add matrix test-add-1 with test-add-2!");
            consoleView.menu("load test-add-1");
            consoleView.menu("load test-add-2");
            consoleView.menu("sum 3 test-add-1 test-add-2 test-add-1-2");
            assert consoleView.getMatrixOut("test-add-1-2") .equals(consoleView.getMatrixOut("test-add-1-2-correct"));
            System.out.println("Result for add matrix test-add-1 with test-add-2! is OK");
            consoleView.menu("clear");
            System.out.println("Add matrix test-add-3 with test-add-4!");
            consoleView.menu("load test-add-3");
            consoleView.menu("load test-add-4");
            consoleView.menu("sum 3 test-add-3 test-add-4 test-add-3-4");
            assert consoleView.getMatrixOut("test-add-3-4").equals(consoleView.getMatrixOut("test-add-3-4-correct"));
            System.out.println("Result for add matrix test-add-3 with test-add-4! is OK");
        }catch (Exception e) {
            assert false;
        }finally {
            consoleView.menu("clear");
        }
    }

    private void runIfMultiplyCorectTests() {
        System.out.println("Run tests for multiply rightness!");
        try {
            System.out.println("Multiply matrix test-mul-1 with test-mul-2!");
            consoleView.menu("load test-mul-1");
            consoleView.menu("load test-mul-2");
            consoleView.menu("multiply 3 test-mul-1 test-mul-2 test-mul-1-2");
            assert consoleView.getMatrixOut("test-mul-1-2") .equals(consoleView.getMatrixOut("test-mul-1-2-correct"));
            System.out.println("Result for mul matrix test-mul-1 with test-mul-2! is OK");
            consoleView.menu("clear");
            System.out.println("Multiply matrix test-mul-3 with test-mul-4!");
            consoleView.menu("load test-mul-3");
            consoleView.menu("load test-mul-4");
            consoleView.menu("multiply 3 test-mul-3 test-mul-4 test-mul-3-4");
            assert consoleView.getMatrixOut("test-mul-3-4").equals(consoleView.getMatrixOut("test-mul-3-4-correct"));
            System.out.println("Result for mul matrix test-mul-3 with test-mul-4! is OK");
        }catch (Exception e) {
            assert false;
        }finally {
            consoleView.menu("clear");
        }
    }

    public void runPerformanceTests() {
        System.out.println("Run performance tests!");
        try {
            for (int i=11; i<12; i+=2) {
                String name1 = "matr-" + i;
                String name2 = "matr-" + (i + 1);
                getMulCommand(name1, name2, 1);
                getMulCommand(name1, name2, 2);
                getMulCommand(name1, name2, 4);
            }
        }catch (Exception e) {
            assert false;
        }finally {
            consoleView.menu("clear");
        }
    }

    public void getAddCommand(String name1, String name2, Integer p) {
        System.out.println("sum " + p + " " + name1 + " " + name2 + " " + "sum-" + name1 + "-" + name2 + "-on-" + p);
        consoleView.menu("load " + name1);
        consoleView.menu("load " + name2);
        consoleView.menu("print -name");
        consoleView.menu("sum " + p + " " + name1 + " " + name2 + " " + "sum-" + name1 + "-" + name2 + "-on-" + p);
        consoleView.menu("clear");
    }

    public void getMulCommand(String name1, String name2, Integer p) {
        System.out.println("multiply " + p + " " + name1 + " " + name2 + " " + "sum-" + name1 + "-" + name2 + "-on-" + p);
        consoleView.menu("load " + name1);
        consoleView.menu("load " + name2);
        consoleView.menu("print -name");
        consoleView.menu("multiply " + p + " " + name1 + " " + name2 + " " + "multiply-" + name1 + "-" + name2 + "-on-" + p);
        consoleView.menu("clear");
    }

    public void run() {
        System.out.println("Running ppd.tests!\n\n");
        runIfAddCorectTests();
        runIfMultiplyCorectTests();
        runPerformanceTests();
    }
}
