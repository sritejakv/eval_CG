package metrics;

import probe.CallEdge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Metrics {
    private String framework, cgAlgorithm, realWorldLib, errorString = "";
    private Double precision = 0.0;
    private Double recall = 0.0;
    private Double runTime = 0.0;
    private Set<CallEdge> tpEdges, fpEdges, fnEdges;
    private Set<CallEdge> totalDynamicEdges;
    private Set<CallEdge> totalStaticEdges;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public Metrics(String cgFramework, String cgAlg, String lib){
        framework = cgFramework;
        cgAlgorithm = cgAlg;
        realWorldLib = lib;
        tpEdges = new HashSet<>();
        fpEdges = new HashSet<>();
        fnEdges = new HashSet<>();
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

    public Double getPrecision() {
        return precision;
    }

    public Double getRecall() {
        return recall;
    }

    public String getFramework() {
        return framework;
    }

    public String getCgAlgorithm() {
        return cgAlgorithm;
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

    public String getRealWorldLib() {
        return realWorldLib;
    }

    public Set<CallEdge> getTotalDynamicEdges() {
        return totalDynamicEdges;
    }

    public void setTotalDynamicEdges(Set<CallEdge> totalDynamicEdges) {
        this.totalDynamicEdges = totalDynamicEdges;
    }

    public Set<CallEdge> getTotalStaticEdges() {
        return totalStaticEdges;
    }

    public void setTotalStaticEdges(Set<CallEdge> totalStaticEdges) {
        this.totalStaticEdges = totalStaticEdges;
    }

    public Set<CallEdge> getTpEdges() {
        return tpEdges;
    }

    public int getFpEdges() {
        return fpEdges.size();
    }

    public int getFnEdges() {
        return fnEdges.size();
    }

    public void validate() {
        assert getTotalStaticEdges().size() == getTpEdges().size() + getFpEdges();
        assert getTotalDynamicEdges().size() == getTpEdges().size() + getFpEdges();
    }
}
