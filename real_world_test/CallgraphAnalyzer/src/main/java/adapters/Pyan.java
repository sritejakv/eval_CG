package adapters;

import com.ibm.wala.util.collections.HashMapFactory;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import metrics.Metrics;
import probe.CallGraph;
import probe.PyanToProbeConverter;
import reports.CSVReporter;
import utils.Constants;
import utils.DotfileReader;
import utils.ResourceReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pyan extends FrameworkAdapter {

    private PyanToProbeConverter pyanToProbeConverter;
    private String reportsDir;
    private Map<String, ArrayList<CallGraph>> staticCG;

    public Pyan(String rDir) {
        super(Constants.PYAN_FRAMEWORK, Constants.PYAN_CALL_GRAPH);
        this.pyanToProbeConverter = new PyanToProbeConverter();
        this.reportsDir = rDir;
        staticCG = HashMapFactory.make();
    }

    @Override
    public ArrayList<CallGraph> getStaticCallGraph(String realWorldLibrary) throws FileNotFoundException {
        if (staticCG.get(realWorldLibrary) == null) {
            InputStream fileInputStream = new FileInputStream(ResourceReader.getResource(Constants.PYAN_STATIC_CG_PATH,
                    realWorldLibrary + "." + Constants.DOT_SUFFIX));
            staticCG.put(realWorldLibrary, pyanToProbeConverter.getProbeCG(fileInputStream));
        }
        return staticCG.get(realWorldLibrary);
    }

    @Override
    public void writeToCSV(Map<String, List<Metrics>> metricsMap) {
        new CSVReporter(Constants.PYAN_CSV_NAME, reportsDir).writeToFile(metricsMap);
    }

    @Override
    public Double getRunTime(String realWorldLibrary) throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream(ResourceReader.getResource(Constants.PYAN_STATIC_CG_PATH,
                realWorldLibrary + "." + Constants.DOT_SUFFIX));
        GraphParser p = DotfileReader.parseDotFile(fileInputStream);
        Map<String, GraphNode> nodeMap = p.getNodes();
        GraphNode runTimeNode = nodeMap.get(Constants.PYAN_RUN_TIME_NODE);
        String runTime = (String) runTimeNode.getAttribute("label");
        return Double.valueOf(runTime);
    }
}
