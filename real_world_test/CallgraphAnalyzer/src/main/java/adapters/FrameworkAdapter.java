package adapters;

import metrics.ExtractMetrics;
import metrics.Metrics;
import probe.CallEdge;
import probe.CallGraph;
import utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Abstract class that should be extended by call graph generation frameworks to facilitate call graph comparison.
 */
public abstract class FrameworkAdapter {

    private String frameworkName;

    private String cgAlgName;

    protected PyCallGraph pyCallGraph;
    private Map<String, List<Metrics>> metricsMap;
    protected final static Logger LOG = Logger.getLogger(FrameworkAdapter.class.getName());
    public FrameworkAdapter(String framework, String cgAlg){
        this.frameworkName = framework;
        this.cgAlgName = cgAlg;
        this.pyCallGraph = new PyCallGraph();
        this.metricsMap = new HashMap<>();
    }

    public String getCgAlgName() {
        return cgAlgName;
    }

    public String getFrameworkName(){
        return frameworkName;
    }

    public PyCallGraph getPyCallGraph(){
        return pyCallGraph;
    }
    /**
     * Method that fetches/generates static call graph using respective call graph algorithm of the python file.
     * @param realWorldLibrary real world library name
     * @return ArrayList containing the probe.CallGraph of the respective call graph
     * @throws FileNotFoundException
     */
    public abstract ArrayList<CallGraph> getStaticCallGraph(String realWorldLibrary) throws FileNotFoundException;

    /**
     * Method that compares two Probe callgraphs and records respective metrics.
     * @param dynCG dynamic CG
     * @param staticCG static CG
     * @param m Metrics object
     */
    public void compareGraphs(CallGraph dynCG, CallGraph staticCG, Metrics m, boolean wala) {
        ExtractMetrics.compareGraphs(dynCG, staticCG, m, wala);
    }

    /**
     * Method to add contents into the private variable metricsMap.
     * @param realWorldLibrary benchmark suite category
     * @param m object of class Metrics
     */
    private void addToMetricsMap(String realWorldLibrary, final Metrics m){
        if (metricsMap.containsKey(realWorldLibrary)){
            metricsMap.get(realWorldLibrary).add(m);
        } else {
            metricsMap.put(realWorldLibrary, new ArrayList<Metrics>(){
                {
                    add(m);
                }
            });
        }
    }

    public Map<String, List<Metrics>> getMetricsMap() {
        return metricsMap;
    }

    /**
     * Method to write the metrics into a csv.
     * @param metricsMap map containing metrics corresponding to each benchmark category
     */
    public abstract void writeToCSV(Map<String, List<Metrics>> metricsMap);

    /**
     * Method that compares static and dynamic call graphs for each python file of benchmark suite.
     * @throws FileNotFoundException
     */
    public void evaluate() throws FileNotFoundException {
        for (String lib : Constants.realWorldLibraries){
            LOG.info("Evaluating the library: " + lib);
            Metrics m = new Metrics(frameworkName, cgAlgName, lib);
            try {
                ArrayList<CallGraph> dynamicCG = pyCallGraph.getDynamicCallGraph(lib);
                ArrayList<CallGraph> staticCG = getStaticCallGraph(lib);
                if (frameworkName.equals(Constants.WALA_FRAMEWORK)){
                    compareGraphs(dynamicCG.get(0), staticCG.get(0), m ,true);
                } else {
                    compareGraphs(dynamicCG.get(0), staticCG.get(0), m ,false);
                }
                m.validate();
                m.recordPrecision();
                m.recordRecall();
                m.setRunTime(getRunTime(lib));
                LOG.info("Precision: " + m.getPrecision() +
                        ", Recall: " + m.getRecall() +
                        ", Runtime: " + m.getRunTime());
            } catch (RuntimeException | IOException e){
                //This catch block is for wala framework
                LOG.warning("Execution failed for the algorithm: "+ cgAlgName + ", with exception: " + e.toString());
                m.setErrorString(e.toString());
            }
            addToMetricsMap(lib, m);
        }

        writeToCSV(metricsMap);
    }

    /**
     * Method to fetch the total time taken by the framework to generate call graph.
     * @param fileName python file
     * @return time taken to generate call graph
     * @throws FileNotFoundException
     */
    public abstract Double getRunTime(String fileName) throws FileNotFoundException;

    private void writeEdges(CallGraph cg, String frName, String libName) throws IOException {
        String reportDir = "reports";
        File dir = new File(reportDir);
        File f = new File(reportDir + File.separator + frName + "_" + libName);

        if (!dir.exists()){
            dir.mkdir();
        }
        FileWriter outFile = new FileWriter(f);

        Set<CallEdge> edges = cg.edges();
        for (CallEdge e: edges) {
            outFile.write(String.valueOf(e) + "\n");
        }
        outFile.close();

    }
}
