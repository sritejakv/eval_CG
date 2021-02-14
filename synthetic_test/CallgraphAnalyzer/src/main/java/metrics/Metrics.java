package metrics;

import probe.CallEdge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Metrics {
    private String framework, cgAlgorithm, benchmarkCategory, fileName, errorString = "";
    private Double precision = 0.0;
    private Double recall = 0.0;
    private Double runTime = 0.0;
    private Double f1score = 0.0;
    private List<CallEdge> tpEdges, fpEdges, fnEdges;
    private int totalDynamicEdges = 0, totalStaticEdges = 0;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public Metrics(String cgFramework, String cgAlg, String benchmarkCat, String file){
        framework = cgFramework;
        cgAlgorithm = cgAlg;
        benchmarkCategory = benchmarkCat;
        fileName = file;
        tpEdges = new ArrayList<>();
        fpEdges = new ArrayList<>();
        fnEdges = new ArrayList<>();
    }

    public List<CallEdge> getTpEdges() {
        return tpEdges;
    }

    public List<CallEdge> getFpEdges() {
        return fpEdges;
    }

    public List<CallEdge> getFnEdges() {
        return fnEdges;
    }

    public void addTP(CallEdge edge){
        tpEdges.add(edge);
    }

    public void addFP(CallEdge edge){
        fpEdges.add(edge);
    }

    public void addFN(CallEdge edge){
        fnEdges.add(edge);
    }

    public void recordPrecision(){
        Double denominator = Double.valueOf(tpEdges.size() + fpEdges.size());
        precision = tpEdges.size() > 0 ? tpEdges.size() / denominator : 0.0;
        precision = Double.valueOf(decimalFormat.format(precision));
    }

    public void recordRecall(){
        Double denominator = Double.valueOf(tpEdges.size() + fnEdges.size());
        recall = tpEdges.size() > 0 ? tpEdges.size() / denominator : 0.0;
        recall = Double.valueOf(decimalFormat.format(recall));
    }

    public void recordF1Score() {
        Double denominator = getPrecision() + getRecall();
        if (denominator > 0) {
            Double numerator = 2 * (getPrecision() * getRecall());
            f1score = numerator/denominator;
        }
    }

    public Double getPrecision() {
        return precision;
    }

    public Double getRecall() {
        return recall;
    }

    public Double getF1score() {
        return f1score;
    }

    public String getFramework() {
        return framework;
    }

    public String getCgAlgorithm() {
        return cgAlgorithm;
    }

    public String getBenchmarkCategory() {
        return benchmarkCategory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }

    public Double getRunTime() {
        return runTime;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public int getTotalDynamicEdges() {
        return totalDynamicEdges;
    }

    public void setTotalDynamicEdges(int totalDynamicEdges) {
        this.totalDynamicEdges = totalDynamicEdges;
    }

    public int getTotalStaticEdges() {
        return totalStaticEdges;
    }

    public void setTotalStaticEdges(int totalStaticEdges) {
        this.totalStaticEdges = totalStaticEdges;
    }

    public void validate() {
        assert getTotalStaticEdges() == tpEdges.size() + fpEdges.size();
        assert getTotalDynamicEdges() == tpEdges.size() + fnEdges.size();
    }
}
