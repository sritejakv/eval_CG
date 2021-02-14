package adapter;

import com.google.common.base.Stopwatch;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashSetFactory;
import metrics.Metrics;
import probe.CallGraph;
import reports.CSVReporter;
import utils.Constants;
import utils.Wala;
import wala.ZeroContainerCFAPythonEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class to get 0-Container-CFA call graph of wala. Extends FrameworkAdapter to facilitate comparison of call graphs.
 */
public class WalaZeroContainerCFA extends FrameworkAdapter {

    private Wala walaUtil;
    private String reportsDir;
    private Map<String, Double> runTimeMap;

    public WalaZeroContainerCFA(String rDir) {
        super(Constants.WALA_FRAMEWORK, Constants.WALA_ZERO_CONTAINER_CFA_CALL_GRAPH);
        this.walaUtil = new Wala();
        this.reportsDir = rDir;
        this.runTimeMap = new HashMap<>();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public ArrayList<CallGraph> getStaticCallGraph(String fileName) {
        ArrayList<probe.CallGraph> cgList = new ArrayList<>();

        ZeroContainerCFAPythonEngine zeroContainerCFAPythonEngine = new ZeroContainerCFAPythonEngine();

        Set<Module> modules = HashSetFactory.make();
        try {
            modules.add(Wala.getPythonScript(fileName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        zeroContainerCFAPythonEngine.setModuleFiles(modules);

        try {
            CallGraphBuilder<? super InstanceKey> builder = zeroContainerCFAPythonEngine.defaultCallGraphBuilder();
            Stopwatch cgWatch = Stopwatch.createStarted();
            com.ibm.wala.ipa.callgraph.CallGraph callGraph = builder.makeCallGraph(zeroContainerCFAPythonEngine.getOptions(),
                    new NullProgressMonitor());
            long runTime = cgWatch.elapsed(TimeUnit.MILLISECONDS);
            runTimeMap.put(fileName, (double) runTime);

            probe.CallGraph probeCG = walaUtil.build(callGraph, fileName);
            cgList.add(probeCG);
        } catch (CancelException | IOException | com.ibm.wala.util.debug.UnimplementedError e) {
            //TODO check if the exceptions are correct
            LOG.warning(e.getMessage());
        }

        return cgList;
    }

    @Override
    public void writeToCSV(Map<String, List<Metrics>> metricsMap) {
        new CSVReporter(Constants.WALA_0C_CFA_CSV_NAME, reportsDir).writeToFile(metricsMap);
    }

    @Override
    public Double getRunTime(String fileName) {
        return runTimeMap.getOrDefault(fileName, 0.0);
    }

    public static void main(String... args) {
        WalaZeroContainerCFA walaZeroContainerCFAAdapter = new WalaZeroContainerCFA("");
        ArrayList<probe.CallGraph> walaProbeCG = walaZeroContainerCFAAdapter.getStaticCallGraph("eval_bark.py");
    }
}
