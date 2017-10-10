import controller.MatrixController;
import domain.exception.InvalidMatrixException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by toade on 10/4/2017.
 */
@Configuration
@ComponentScan
public class Main {
    public static void main(String args[]) throws IOException, InvalidMatrixException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MatrixController controller = (MatrixController) context.getBean("matrixController");

        controller.generateMatrix(5, 5,0,1000, "C:\\Users\\toade\\Desktop\\matrici\\1.out");
        controller.generateMatrix(7, 5,0,1000, "C:\\Users\\toade\\Desktop\\matrici\\2.out");
        controller.generateMatrix(8, 5,0,1000, "C:\\Users\\toade\\Desktop\\matrici\\3.out");
        controller.generateMatrix(9, 5,0,1000, "C:\\Users\\toade\\Desktop\\matrici\\4.out");
        controller.generateMatrix(15, 5,0,1000, "C:\\Users\\toade\\Desktop\\matrici\\5.out");

        controller.loadMatrixFromDirectory("C:\\Users\\toade\\Desktop\\matrici");

        controller.getAllMatrix().forEach(System.out::println);
    }

}
