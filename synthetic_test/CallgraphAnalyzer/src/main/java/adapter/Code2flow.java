package adapter;

import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import metrics.Metrics;
import probe.CallGraph;
import probe.Code2flowToProbeConverter;
import reports.CSVReporter;
import utils.Constants;
import utils.DotfileReader;
import utils.ResourceReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Class to get call graph generated by code2flow. Extends FrameworkAdapter to facilitate comparison of call graphs.
 */
public class Code2flow extends FrameworkAdapter {

    private Code2flowToProbeConverter code2flowToProbeConverter;
    private String reportsDir;

    public Code2flow(String rDir) {
        super(Constants.CODE2FLOW_FRAMEWORK, Constants.CODE2FLOW_CALL_GRAPH);
        this.code2flowToProbeConverter = new Code2flowToProbeConverter();
        this.reportsDir = rDir;
    }

    @Override
    public ArrayList<CallGraph> getStaticCallGraph(String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(ResourceReader.getResource(Constants.CODE2FLOW_STATIC_CG_PATH,
                fileName));
        return code2flowToProbeConverter.getProbeCG(inputStream);
    }

    @Override
    public void writeToCSV(Map<String, List<Metrics>> metricsMap) {
        new CSVReporter(Constants.CODE2FLOW_CSV_NAME, reportsDir).writeToFile(metricsMap);
    }

    @Override
    public Double getRunTime(String fileName) throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream(ResourceReader.getResource(Constants.CODE2FLOW_STATIC_CG_PATH,
                fileName));
        GraphParser p = DotfileReader.parseDotFile(fileInputStream);
        Map<String, GraphNode> nodeMap = p.getNodes();
        GraphNode runTimeNode = nodeMap.get(Constants.CODE2FLOW_RUN_TIME_NODE);
        String runTime = (String) runTimeNode.getAttribute("label");
        return Double.valueOf(runTime);
    }
}
