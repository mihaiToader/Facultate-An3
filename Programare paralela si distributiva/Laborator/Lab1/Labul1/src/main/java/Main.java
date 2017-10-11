import controller.MatrixController;
import domain.exception.InvalidMatrixException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import view.ConsoleView;

import java.io.IOException;

/**
 * Created by toade on 10/4/2017.
 */
@Configuration
@ComponentScan
public class Main {
    public static void main(String args[]) throws IOException, InvalidMatrixException {
        ConsoleView consoleView = new ConsoleView();
        consoleView.start();
    }

}
