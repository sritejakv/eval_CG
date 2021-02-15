package utils;

public class EntryPointMetrics {
    int totalSourceFiles = 0, successfullyRun = 0, commonFiles = 0;
    String framework, realWorldLib;

    public EntryPointMetrics(String f, String lib) {
        this.framework = f;
        this.realWorldLib = lib;
    }

    public String getRealWorldLib() {
        return realWorldLib;
    }

    public String getFramework() {
        return framework;
    }

    public int getTotalSourceFiles() {
        return totalSourceFiles;
    }

    public void setTotalSourceFiles(int totalSourceFiles) {
        this.totalSourceFiles = totalSourceFiles;
    }

    public int getSuccessfullyRun() {
        return successfullyRun;
    }

    public void setSuccessfullyRun(int successfullyRun) {
        this.successfullyRun = successfullyRun;
    }

    public int getCommonFiles() {
        return commonFiles;
    }

    public void setCommonFiles(int commonFiles) {
        this.commonFiles = commonFiles;
    }
}
