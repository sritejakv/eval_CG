package adapter;

import com.opencsv.CSVWriter;
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

    private String frameworkName, cgAlgName;
    protected PyCallGraph pyCallGraph;
    private Map<String, List<Metrics>> metricsMap;
    protected final static Logger LOG = Logger.getLogger(FrameworkAdapter.class.getName());

    public FrameworkAdapter(String framework, String cgAlg){
        this.frameworkName = framework;
        this.cgAlgName = cgAlg;
        this.pyCallGraph = new PyCallGraph();
        this.metricsMap = new HashMap<>();
    }

    public String getFrameworkName(){
        return frameworkName;
    }

    public PyCallGraph getPyCallGraph(){
        return pyCallGraph;
    }
    /**
     * Method that fetches/generates static call graph using respective call graph algorithm of the python file.
     * @param fileName python file name
     * @return ArrayList containing the probe.CallGraph of the respective call graph
     * @throws FileNotFoundException
     */
    public abstract ArrayList<CallGraph> getStaticCallGraph(String fileName) throws FileNotFoundException;

    /**
     * Method that compares two Probe callgraphs and records respective metrics.
     * @param dynCG dynamic CG
     * @param staticCG static CG
     * @param m Metrics object
     */
    public void compareGraphs(CallGraph dynCG, CallGraph staticCG, Metrics m) {
        ExtractMetrics.compareGraphs(dynCG, staticCG, m);
    }

    /**
     * Method to add contents into the private variable metricsMap.
     * @param category benchmark suite category
     * @param m object of class Metrics
     */
    private void addToMetricsMap(String category, final Metrics m){
        if (metricsMap.containsKey(category)){
            metricsMap.get(category).add(m);
        } else {
            metricsMap.put(category, new ArrayList<Metrics>(){
                {
                    add(m);
                }
            });
        }
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
        String analysisFile = createAnalysisFile();
        for (Map.Entry<String, List<File>> categoryFilesMapEntry: pyCallGraph.getCategoryDotFilesMap().entrySet()){
            String benchmarkCategory = categoryFilesMapEntry.getKey();
            LOG.info("Evaluating the category: " + benchmarkCategory);
            for (File dynamicCGFile: categoryFilesMapEntry.getValue()){
                Metrics m = new Metrics(frameworkName, cgAlgName, benchmarkCategory, dynamicCGFile.getName());
                try {
                    LOG.info("Comparing the static and dynamic CGs of file: " + dynamicCGFile.getName());
                    ArrayList<CallGraph> dynamicCG = pyCallGraph.getDynamicCallGraph(dynamicCGFile);
                    m.setTotalDynamicEdges(dynamicCG.get(0).edges().size());
                    ArrayList<CallGraph> staticCG = getStaticCallGraph(dynamicCGFile.getName());
                    CallGraph tempDynCG = new CallGraph();
                    Set<CallEdge> tempDynEdges = dynamicCG.get(0).edges();
                    for (CallEdge e: tempDynEdges) {
                        tempDynCG.edges().add(e);
                    }
                    compareGraphs(dynamicCG.get(0), staticCG.get(0), m);
                    m.validate();
                    m.recordPrecision();
                    m.recordRecall();
                    m.recordF1Score();
                    m.setRunTime(getRunTime(dynamicCGFile.getName()));
                    writeEdgesToFile(m, tempDynCG, analysisFile);
                    LOG.info("Precision: " + m.getPrecision() +
                            ", Recall: " + m.getRecall() +
                            ", Runtime: " + m.getRunTime());
                } catch (RuntimeException e){
                    //This catch block is for wala framework
                    LOG.warning(e.toString());
                    m.setErrorString(e.toString());
                }
                addToMetricsMap(benchmarkCategory, m);
            }
        }

        writeToCSV(metricsMap);
    }

    private String createAnalysisFile() {
        String reportDir = Constants.DEFAULT_REPORT_DIR;
        File dir = new File(reportDir);
        File f = new File(reportDir + File.separator + cgAlgName + "Analysis.csv");

        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            FileWriter outFile = new FileWriter(f);
            CSVWriter csvWriter = new CSVWriter(outFile);
            csvWriter.writeNext(Constants.ANALYZE_CSV_HEADER);
            csvWriter.close();
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }

        return f.getAbsolutePath();
    }

    private void writeEdgesToFile(Metrics m, CallGraph dynamicCG, String analysisFilePath) {
        try {
            FileWriter outFile = new FileWriter(analysisFilePath, true);
            CSVWriter csvWriter = new CSVWriter(outFile);
            StringBuffer dynEdgesString = new StringBuffer();
            Set<CallEdge> dynEdges = dynamicCG.edges();
            for (CallEdge e: dynEdges) {
                dynEdgesString.append(e.toString() + ";\n");
            }
            StringBuffer tpString = new StringBuffer();
            for (CallEdge e: m.getTpEdges()) {
                tpString.append(e.toString() + "\n");
            }
            StringBuffer fpString = new StringBuffer();
            for (CallEdge e: m.getFpEdges()) {
                fpString.append(e.toString() + "\n");
            }
            StringBuffer fnString = new StringBuffer();
            for (CallEdge e: m.getFnEdges()) {
                fnString.append(e.toString() + "\n");
            }

            String[] row = {
                    m.getFileName(),
                    m.getBenchmarkCategory(),
                    dynEdgesString.toString(),
                    tpString.toString(),
                    fpString.toString(),
                    fnString.toString()
            };
            csvWriter.writeNext(row);
            csvWriter.close();
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }

    /**
     * Method to fetch the total time taken by the framework to generate call graph.
     * @param fileName python file
     * @return time taken to generate call graph
     * @throws FileNotFoundException
     */
    public abstract Double getRunTime(String fileName) throws FileNotFoundException;
}
