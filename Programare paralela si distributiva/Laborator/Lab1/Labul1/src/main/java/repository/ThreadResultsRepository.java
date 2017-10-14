package repository;

import domain.ThreadResults;

import java.io.IOException;

/**
 * Created by toade on 10/13/2017.
 */
public interface ThreadResultsRepository {

    void writeResultsToFile(ThreadResults threadResults, String fileNameOutMatrix) throws IOException;
}
