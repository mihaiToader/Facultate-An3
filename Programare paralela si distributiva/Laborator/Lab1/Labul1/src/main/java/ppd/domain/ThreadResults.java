package ppd.domain;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import ppd.thread.ThreadMatrix;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by toade on 10/13/2017.
 */
public class ThreadResults {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Integer executionTimeInSeconds;
    private Integer executionTimeInNano;
    private Integer threadNumber;
    private Matrix outMatrix;
    private List<ThreadMatrix> addThread;
    private String operation;

    public ThreadResults() {
    }

    public ThreadResults(LocalDateTime startTime, LocalDateTime finishTime, Matrix firstMatrix, Matrix secondMatrix, Integer executionTimeInSeconds, Integer executionTimeInNano, Integer threadNumber, Matrix outMatrix, List<ThreadMatrix> addThread) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.executionTimeInSeconds = executionTimeInSeconds;
        this.executionTimeInNano = executionTimeInNano;
        this.threadNumber = threadNumber;
        this.outMatrix = outMatrix;
        this.addThread = addThread;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Matrix getFirstMatrix() {
        return firstMatrix;
    }

    public void setFirstMatrix(Matrix firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    public Matrix getSecondMatrix() {
        return secondMatrix;
    }

    public void setSecondMatrix(Matrix secondMatrix) {
        this.secondMatrix = secondMatrix;
    }

    public Integer getExecutionTimeInSeconds() {
        return executionTimeInSeconds;
    }

    public void setExecutionTimeInSeconds(Integer executionTimeInSeconds) {
        this.executionTimeInSeconds = executionTimeInSeconds;
    }

    public Integer getExecutionTimeInNano() {
        return executionTimeInNano;
    }

    public void setExecutionTimeInNano(Integer executionTimeInNano) {
        this.executionTimeInNano = executionTimeInNano;
    }

    public Integer getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(Integer threadNumber) {
        this.threadNumber = threadNumber;
    }

    public Matrix getOutMatrix() {
        return outMatrix;
    }

    public void setOutMatrix(Matrix outMatrix) {
        this.outMatrix = outMatrix;
    }

    public List<ThreadMatrix> getAddThread() {
        return addThread;
    }

    public void setAddThread(List<ThreadMatrix> addThread) {
        this.addThread = addThread;
    }

    private String getSystemInformations() {
        String res = "";
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        res += os + "\n";
        HardwareAbstractionLayer hal = si.getHardware();
        res += hal.getProcessor().toString() + "\n";
        res += "Memory: " +
                FormatUtil.formatBytes(hal.getMemory().getAvailable()) + "/" +
                FormatUtil.formatBytes(hal.getMemory().getTotal()) + "/n";
        return res;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        String threadResults =  "Results:\n" +
                "-> operation = " + operation + "\n" +
                "-> threadNumber = " + threadNumber + "\n" +
                "-> startTime = " + startTime + "\n" +
                "-> finishTime = " + finishTime + "\n" +
                "-> executionTimeInSeconds = " + executionTimeInSeconds +  "\n" +
                "-> executionTimeInNano = " + executionTimeInNano +  "\n";
        threadResults += "\n\n";
        threadResults += "System information:\n";
        threadResults += getSystemInformations();
        threadResults += "\n\n";

        for (ThreadMatrix addMatrix: addThread) {
            threadResults += "------------------------------------------------------------------------------\n";
            threadResults += "-> threadName: " + addMatrix.getThreadName() + "\n";
            int index = 1;
            for (String op: addMatrix.getOperations()) {
                threadResults += index++ + ". " + op + "\n";
            }
        }
        threadResults += "------------------------------------------------------------------------------\n";
        threadResults += "\n\n";
        threadResults += "First matrix:\n" + firstMatrix.getName() + "\n";
        threadResults += "Second matrix:\n" + secondMatrix.getName() + "\n";
        threadResults += "The out matrix:\n" + outMatrix.getName() + "\n";

        threadResults += "\n\nCopyright Toader Mihai 2017";
        return threadResults;
    }
}
