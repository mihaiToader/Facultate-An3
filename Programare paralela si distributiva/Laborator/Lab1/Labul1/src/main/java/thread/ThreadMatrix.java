package thread;

import java.util.List;

/**
 * Created by toade on 10/13/2017.
 */
public interface ThreadMatrix extends Runnable{
    List<String> getOperations();
    String getThreadName();
}
