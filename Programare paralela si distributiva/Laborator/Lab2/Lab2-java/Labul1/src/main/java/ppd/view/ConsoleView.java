package ppd.view;

import ppd.controller.MatrixController;
import ppd.domain.Matrix;
import ppd.domain.exception.InvalidMatrixException;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ppd.service.MatrixService;
import ppd.tests.Tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by toade on 10/11/2017.
 */
public class ConsoleView {
    private Scanner consoleInput = new Scanner(System.in);
    private MatrixController controller;
    private MatrixService matrixService;
    private String pathIn;
    private String pathOut;
    private String extensionOut;
    private String extensionIn;
    private Tests tests;

    public ConsoleView() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        controller = (MatrixController) context.getBean("matrixController");
        matrixService = (MatrixService) context.getBean("matrixService");
        tests = new Tests(this);
        InputStream resourceAsStream = ConsoleView.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        try {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            System.out.println("Config file missing!");
        }
        pathIn = prop.getProperty("path_matrix_in");
        pathOut = prop.getProperty("path_matrix_out");
        extensionIn = prop.getProperty("in_extension");
        extensionOut = prop.getProperty("out_extension");
    }

    public String getFullPathNameInFile(String name) {
        return pathIn + "\\" + name + extensionIn;
    }

    public String getFullPathNameOutFile(String name) {
        return pathOut + "\\" + name + extensionOut;
    }

    public String menu(String cmd) {
        if (cmd.equals("exit")) {
            return "done";
        } else if (cmd.contains("generation")) {
            generateMatrix(cmd);
        } else if (cmd.contains("load")) {
            loadMatrix(cmd);
        } else if (cmd.contains("print -all")) {
            printMatrix(controller.getAllMatrix());
        } else if (cmd.contains("print -name")) {
            controller.getAllMatrix().forEach(x -> System.out.println(x.getName() + " " + x.getNumberOfLines() + "X" + x.getNumberOfColumns()));
        } else if (cmd.contains("sum")) {
            sumMatrix(cmd);
        } else if (cmd.contains("multiply")) {
            multiplyMatrix(cmd);
        } else if (cmd.contains("find -matrix_in")) {
            findMatrixIn(cmd);
        } else if (cmd.contains("find -matrix_out")) {
            findMatrixOut(cmd);
        } else if (cmd.contains("clear")) {
            controller.clearMatrixes();
        } else if (cmd.equals("run tests")) {
            tests.run();
        } else if (cmd.equals("help")) {
            printHelp();
        } else if (cmd.contains("gd")) {
            generateDoubleMatrix(cmd);
        } else if (cmd.contains("dmm")) {
            doubleMatrixMul(cmd);
        } else if (cmd.contains("gc")) {
            generateComplexMatrix(cmd);
        } else if (cmd.contains("cmm")) {
            complexMatrixMul(cmd);
        } else if (cmd.contains("dmw")) {
            weirdDouble(cmd);
        } else if (cmd.contains("cmw")) {
            weirdComplex(cmd);
        } else {
            System.out.println("I don't know what you want! Try help for help");
        }
        return "";
    }

    private void weirdComplex(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            matrixService.complexMatrixWeird(Integer.parseInt(commands[1]), getFullPathNameInFile(commands[2]), getFullPathNameInFile(commands[3]), getFullPathNameOutFile(commands[2] + "_" + commands[3]));
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }}

    private void weirdDouble(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            matrixService.doubleMatrixWeird(Integer.parseInt(commands[1]), getFullPathNameInFile(commands[2]), getFullPathNameInFile(commands[3]), getFullPathNameOutFile(commands[2] + "_" + commands[3]));
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doubleMatrixMul(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            matrixService.doubleMatrix(Integer.parseInt(commands[1]), getFullPathNameInFile(commands[2]), getFullPathNameInFile(commands[3]), getFullPathNameOutFile(commands[2] + "_" + commands[3]));
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void complexMatrixMul(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            matrixService.complexMatrix(Integer.parseInt(commands[1]), getFullPathNameInFile(commands[2]), getFullPathNameInFile(commands[3]), getFullPathNameOutFile(commands[2] + "_" + commands[3]));
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void generateComplexMatrix(String cmd) {
        try {
            String[] commands = cmd.split(" ");
            if (commands[1].equals("-help")) {
                System.out.println("gc numberLines numberColumns from to nameMatrix");
                return;
            }
            int numberLines = Integer.parseInt(commands[1]);
            int numberColumns = Integer.parseInt(commands[2]);
            int from = Integer.parseInt(commands[3]);
            int to = Integer.parseInt(commands[4]);
            String name = commands[5];
            matrixService.generateComplexMatrix(numberLines, numberColumns, from, to, getFullPathNameInFile(name));
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void multiplyMatrix(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            controller.multiplyMatrix(Integer.parseInt(commands[1]), commands[2], commands[3], getFullPathNameOutFile(commands[4]));
            if (cmd.contains("-print")) {
                Matrix matr = controller.readMatrixFromFile(getFullPathNameOutFile(commands[4]));
                System.out.println(matr.getName());
                System.out.println(matr);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void start() {
        String cmd;
        while (true) {
            System.out.print("--->");
            cmd = consoleInput.nextLine();
            if (menu(cmd).equals("done")) {
                break;
            }
        }
    }

    private void findMatrixOut(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            Matrix matr = getMatrixOut(commands[2]);
            System.out.println(matr.getName());
            System.out.println(matr);
        } catch (IOException e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        } catch (InvalidMatrixException e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    public Matrix getMatrixOut(String name) throws IOException, InvalidMatrixException {
        return controller.readMatrixFromFile(getFullPathNameOutFile(name));
    }

    private void findMatrixIn(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            Matrix matr = controller.readMatrixFromFile(getFullPathNameInFile(commands[3]));
            System.out.println(matr.getName());
            System.out.println(matr);
        } catch (IOException e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        } catch (InvalidMatrixException e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void sumMatrix(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            controller.sumMatrix(Integer.parseInt(commands[1]), commands[2], commands[3], getFullPathNameOutFile(commands[4]));
            if (cmd.contains("-print")) {
                Matrix matr = controller.readMatrixFromFile(getFullPathNameOutFile(commands[4]));
                System.out.println(matr.getName());
                System.out.println(matr);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void loadMatrix(String cmd) {
        String[] commands = cmd.split(" ");
        if (commands.length == 2 && !commands[1].equals("-print")) {
            try {
                controller.loadMatrix(pathIn, commands[1]);
            } catch (IOException e) {
                System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
            }
        } else {
            try {
                controller.loadMatrixFromDirectory(pathIn);
            } catch (IOException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
            if (cmd.contains("-print")) {
                System.out.println("Loaded matrix are:\n");
                printMatrix(controller.getAllMatrix());
            }
        }
    }


    private void generateMatrix(String cmd) {
        try {
            String[] commands = cmd.split(" ");
            if (commands[1].equals("-help")) {
                System.out.println("generation numberLines numberColumns from to nameMatrix");
                return;
            }
            int numberLines = Integer.parseInt(commands[1]);
            int numberColumns = Integer.parseInt(commands[2]);
            int from = Integer.parseInt(commands[3]);
            int to = Integer.parseInt(commands[4]);
            String name = commands[5];
            controller.generateMatrix(numberLines, numberColumns, from, to, getFullPathNameInFile(name));
            System.out.println("Matrix generated, load to see matrix");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            System.out.println("generate -help");
        }
    }

    private void generateDoubleMatrix(String cmd) {
        try {
            String[] commands = cmd.split(" ");
            if (commands[1].equals("-help")) {
                System.out.println("generation-double numberLines numberColumns from to nameMatrix");
                return;
            }
            int numberLines = Integer.parseInt(commands[1]);
            int numberColumns = Integer.parseInt(commands[2]);
            int from = Integer.parseInt(commands[3]);
            int to = Integer.parseInt(commands[4]);
            String name = commands[5];
            matrixService.generateDoubleMatrix(numberLines, numberColumns, from, to, getFullPathNameInFile(name));
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }


    private void printMatrix(List<Matrix> all) {
        all.forEach(x -> System.out.println(x.toNiceString()));
    }

    private void printHelp() {
        try {
            System.out.println(IOUtils.toString(ConsoleView.class.getClassLoader().getResourceAsStream("help.txt")));
        } catch (IOException e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }
}
