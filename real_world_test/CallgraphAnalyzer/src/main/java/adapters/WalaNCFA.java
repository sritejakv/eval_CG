package adapters;

import com.google.common.base.Stopwatch;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.SourceURLModule;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.collections.HashSetFactory;
import metrics.Metrics;
import reports.CSVReporter;
import utils.Constants;
import utils.Wala;
import wala.NCFAPythonEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class to get nCFA call graph of wala. Extends FrameworkAdapter to facilitate comparison of call graphs.
 */
public class WalaNCFA extends FrameworkAdapter {

    private Wala walaUtil;
    private String reportsDir;
    private Map<String, Double> runTimeMap;
    private Map<String, ArrayList<probe.CallGraph>> staticCG;

    public WalaNCFA(String rDir) {
        super(Constants.WALA_FRAMEWORK, Constants.WALA_NCFA_CALL_GRAPH);
        this.walaUtil = new Wala();
        this.reportsDir = rDir;
        this.runTimeMap = new HashMap<>();
        staticCG = HashMapFactory.make();
    }

    private Set<Module> pruneSourceFiles(Set<Module> sourceFiles){
        Set<Module> prunedFiles = HashSetFactory.make();
        for(Module file: sourceFiles){
            NCFAPythonEngine engine = new NCFAPythonEngine();
            Set<Module> modules = HashSetFactory.make();
            modules.add(file);
            engine.setModuleFiles(modules);
            CallGraphBuilder<? super InstanceKey> builder = null;
            try {
                builder = engine.defaultCallGraphBuilder();
                CallGraph callGraph = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
                prunedFiles.add(file);
            } catch (CancelException | IOException | com.ibm.wala.util.debug.UnimplementedError |
                    com.ibm.wala.util.WalaRuntimeException | IllegalArgumentException e) {
                LOG.warning(e.getMessage());
            }
        }
        return prunedFiles;
    }

    @Override
    public ArrayList<probe.CallGraph> getStaticCallGraph(String realWorldLibrary) {
        if (staticCG.get(realWorldLibrary) == null) {
            ArrayList<probe.CallGraph> cgList = new ArrayList<>();

            NCFAPythonEngine engine = new NCFAPythonEngine();

            Set<Module> modules = HashSetFactory.make();
            try {
                modules.addAll(Wala.getPythonScripts(realWorldLibrary));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

//            Set<Module> prunedModules = pruneSourceFiles(modules);
            engine.setModuleFiles(modules);

            try {
                CallGraphBuilder<? super InstanceKey> builder = engine.defaultCallGraphBuilder();
                Stopwatch cgWatch = Stopwatch.createStarted();
                CallGraph callGraph = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
                long runTime = cgWatch.elapsed(TimeUnit.MILLISECONDS);
                runTimeMap.put(realWorldLibrary, (double) runTime);

                probe.CallGraph probeCG = walaUtil.build(callGraph, realWorldLibrary);
                cgList.add(probeCG);
                staticCG.put(realWorldLibrary, cgList);
            } catch (CancelException | IOException | com.ibm.wala.util.debug.UnimplementedError e) {
                //TODO check if the exceptions are correct
                LOG.warning(e.getMessage());
            }

        }
        return staticCG.get(realWorldLibrary);
    }

    @Override
    public void writeToCSV(Map<String, List<Metrics>> metricsMap) {
        new CSVReporter(Constants.WALA_NCFA_CSV_NAME, reportsDir).writeToFile(metricsMap);
    }

    @Override
    public Double getRunTime(String fileName) {
        return runTimeMap.getOrDefault(fileName, 0.0);
    }

    public static void main(String...args) throws FileNotFoundException {
        WalaNCFA nCFA = new WalaNCFA("");
        ArrayList<probe.CallGraph> walaProbeCG = nCFA.getStaticCallGraph("statsmodels");
        System.out.println("Hello");
    }
}
