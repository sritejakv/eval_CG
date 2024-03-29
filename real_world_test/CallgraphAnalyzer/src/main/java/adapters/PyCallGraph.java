package adapters;

import probe.CallGraph;
import probe.PyCallGraphToProbeConverter;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to read the call graphs generated by PyCallGraph tool.
 */
public class PyCallGraph {

    private final static Logger LOG = Logger.getLogger(FrameworkAdapter.class.getName());

    private Map<String, ArrayList<CallGraph>> dynCGs;

    public PyCallGraph() {
        this.dynCGs = new HashMap<>();
        initializeDynCGs();
    }

    private void initializeDynCGs() {
        for (String lib : Constants.realWorldLibraries){
            PyCallGraphToProbeConverter pyCallGraphToProbeConverter = new PyCallGraphToProbeConverter();
            pyCallGraphToProbeConverter.setReplacementString(lib);
            File dotFileName = new File(Constants.RESOURCES_PATH + Constants.PYCALLGRAPH_DYNAMIC_CG_PATH +
                    File.separator + lib + "." + Constants.DOT_SUFFIX);
            try {
                dynCGs.put(lib, pyCallGraphToProbeConverter.getProbeCG(new FileInputStream(dotFileName)));
            } catch (FileNotFoundException e) {
                LOG.warning("Error initializing dynamic CGs: " + e.getMessage());
            }
        }
    }

    public ArrayList<CallGraph> getDynamicCallGraph(String realWorldLib) {
        return dynCGs.get(realWorldLib);
    }

    public static void main(String... args){
        PyCallGraph x = new PyCallGraph();
        ArrayList<CallGraph> y = x.getDynamicCallGraph("httpie");
    }
}
