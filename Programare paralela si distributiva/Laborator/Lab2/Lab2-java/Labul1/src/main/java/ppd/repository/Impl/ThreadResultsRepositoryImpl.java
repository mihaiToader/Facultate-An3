package ppd.repository.Impl;

import ppd.domain.ThreadResults;
import org.apache.commons.io.FilenameUtils;
import ppd.repository.ThreadResultsRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by toade on 10/13/2017.
 */
public class ThreadResultsRepositoryImpl implements ThreadResultsRepository {
    @Override
    public void writeResultsToFile(ThreadResults threadResults, String fileNameOutMatrix) throws IOException {
        String pathToDirectory = FilenameUtils.getFullPath(fileNameOutMatrix);
        String name = FilenameUtils.getBaseName(fileNameOutMatrix);
        String fileResults = pathToDirectory + "/" + name + "_results_stats.txt";
        File outputFile = new File(fileResults);
        outputFile.createNewFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileResults))) {
            writer.write(threadResults.toString());
        }
    }
}
