package view;

import controller.MatrixController;
import domain.Matrix;
import domain.exception.InvalidMatrixException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by toade on 10/11/2017.
 */
public class ConsoleView {
    private Scanner consoleInput = new Scanner(System.in);
    private MatrixController controller;
    private String hardcodedPathIn = "C:\\Users\\toade\\Desktop\\matrici\\in";
    private String hardcodedPathOut = "C:\\Users\\toade\\Desktop\\matrici\\out";
    private String harcodedExtensionOut = ".out";
    private String harcodedExtensionIn = ".in";

    public ConsoleView() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        controller = (MatrixController) context.getBean("matrixController");
    }

    public String getFullPathNameInFile(String name) {
        return hardcodedPathIn + "\\" + name + harcodedExtensionIn;
    }

    public String getFullPathNameOutFile(String name) {
        return hardcodedPathOut + "\\" + name + harcodedExtensionOut;
    }

    public void start() {
        String cmd;
        while (true) {
            System.out.print("--->");
            cmd = consoleInput.nextLine();
            if (cmd.equals("exit")) {
                break;
            } else if (cmd.contains("generation")) {
                generateMatrix(cmd);
            } else if (cmd.contains("load")) {
                loadMatrix(cmd);
            } else if (cmd.contains("print -all")) {
                printMatrix(controller.getAllMatrix());
            } else if (cmd.contains("print -name")) {
                controller.getAllMatrix().forEach(x -> System.out.println(x.getName()));
            } else if (cmd.contains("sum")) {
                sumMatrix(cmd);
            } else {
                System.out.println("I don't know what you want! Try help for help");
            }
        }
    }

    private void sumMatrix(String cmd) {
        String[] commands = cmd.split(" ");
        try {
            controller.sumMatrix(Integer.parseInt(commands[1]), commands[2], commands[3], getFullPathNameOutFile(commands[4]));
        } catch (Exception e) {
            System.out.println("[ERROR] " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void loadMatrix(String cmd) {
        try {
            controller.loadMatrixFromDirectory(hardcodedPathIn);
        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        if (cmd.contains("-print")) {
            System.out.println("Loaded matrix are:\n");
            printMatrix(controller.getAllMatrix());
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

    private void printMatrix(List<Matrix> all) {
        all.forEach(x -> System.out.println(x.toNiceString()));
    }
}
