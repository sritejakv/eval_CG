package adapter;

import com.google.common.base.Stopwatch;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashSetFactory;
import metrics.ExtractMetrics;
import metrics.Metrics;
import probe.CallGraph;
import reports.CSVReporter;
import utils.Constants;
import utils.Wala;
import wala.VanillaZeroOneCFAPythonEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class to get Vanilla-0-1-CFA call graph of wala. Extends FrameworkAdapter to facilitate comparison of call graphs.
 */
public class WalaVanillaZeroOneCFA extends FrameworkAdapter {

    private Wala walaUtil;
    private String reportsDir;
    private Map<String, Double> runTimeMap;

    public WalaVanillaZeroOneCFA(String rDir) {
        super(Constants.WALA_FRAMEWORK, Constants.WALA_VANILLA_ZERO_CFA_CALL_GRAPH);
        this.walaUtil = new Wala();
        this.reportsDir = rDir;
        this.runTimeMap = new HashMap<>();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public ArrayList<CallGraph> getStaticCallGraph(String fileName) {
        ArrayList<probe.CallGraph> cgList = new ArrayList<>();

        VanillaZeroOneCFAPythonEngine vanillaZeroOneCFAPythonEngine = new VanillaZeroOneCFAPythonEngine();

        Set<Module> modules = HashSetFactory.make();
        try {
            modules.add(Wala.getPythonScript(fileName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        vanillaZeroOneCFAPythonEngine.setModuleFiles(modules);

        try {
            CallGraphBuilder<? super InstanceKey> builder = vanillaZeroOneCFAPythonEngine.defaultCallGraphBuilder();
            Stopwatch cgWatch = Stopwatch.createStarted();
            com.ibm.wala.ipa.callgraph.CallGraph callGraph = builder.makeCallGraph(vanillaZeroOneCFAPythonEngine.getOptions(), new NullProgressMonitor());
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
    public void compareGraphs(CallGraph dynCG, CallGraph staticCG, Metrics m) {
        ExtractMetrics.compareGraphs(dynCG, staticCG, m);
    }

    @Override
    public void writeToCSV(Map<String, List<Metrics>> metricsMap) {
        new CSVReporter(Constants.WALA_V01_CFA_CSV_NAME, reportsDir).writeToFile(metricsMap);
    }

    @Override
    public Double getRunTime(String fileName) {
        return runTimeMap.getOrDefault(fileName, 0.0);
    }

    public static void main(String...args) throws FileNotFoundException {
        WalaVanillaZeroOneCFA vanillaCFA = new WalaVanillaZeroOneCFA("");
        ArrayList<probe.CallGraph> walaProbeCG = vanillaCFA.getStaticCallGraph("eval_bark.py");
    }
}
