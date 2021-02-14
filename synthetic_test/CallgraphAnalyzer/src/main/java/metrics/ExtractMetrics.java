package metrics;

import com.ibm.wala.util.collections.HashSetFactory;
import probe.CallEdge;
import probe.CallGraph;
import probe.ProbeMethod;
import utils.Constants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class ExtractMetrics {

    private final static Logger LOG = Logger.getLogger(ExtractMetrics.class.getName());


    public static boolean deepCompare(CallEdge edgeE1, ProbeMethod e2Src, ProbeMethod e2Dst){
        // removing < and > to identify labmda nodes
        String e1SrcClass = edgeE1.src().cls().toString().replace("<", "").replace(">", "");
        String e1SrcMethodName = edgeE1.src().name().replace("<", "").replace(">", "");
        if (e1SrcMethodName.contains(Constants.PYTHON_INIT)) {
            e1SrcMethodName = e1SrcMethodName.replace(Constants.PYTHON_INIT, e1SrcClass);
        }
        String e1DstClass = edgeE1.dst().cls().toString().replace("<", "").replace(">", "");
        String e1DstMethodName = edgeE1.dst().name().replace("<", "").replace(">", "");
        if (e1DstMethodName.contains(Constants.PYTHON_INIT)) {
            e1DstMethodName = e1DstMethodName.replace(Constants.PYTHON_INIT, e1DstClass);
        }

        String e2SrcClass = e2Src.cls().toString().replace("<", "").replace(">", "");
        String e2SrcMethodName = e2Src.name().replace("<", "").replace(">", "");
        if (e2SrcMethodName.contains(Constants.PYTHON_INIT)) {
            e2SrcMethodName = e2SrcMethodName.replace(Constants.PYTHON_INIT, e2SrcClass);
        }
        String e2DestClass = e2Dst.cls().toString().replace("<", "").replace(">", "");
        String e2DestMethodName = e2Dst.name().replace("<", "").replace(">", "");
        if (e2DestMethodName.contains(Constants.PYTHON_INIT)) {
            e2DestMethodName = e2DestMethodName.replace(Constants.PYTHON_INIT, e2DestClass);
        }

        if (e1SrcMethodName.equals(e2SrcMethodName) || e2SrcMethodName.contains(e1SrcMethodName) ||
                e1SrcMethodName.contains(e2SrcMethodName)){
            if (e1DstMethodName.equals(e2DestMethodName) || e1DstMethodName.contains(e2DestMethodName) ||
            e2DestMethodName.contains(e1DstMethodName)){
                if (e1DstClass.contains(e2DestClass) || e1DstClass.equals(e2DestMethodName) ||
                e1DstClass.equals(e1DstMethodName) || e2DestMethodName.contains(e1DstClass))
                    return true;
            }

            if (e2Src.toString().contains(edgeE1.src().toString()) ||
                    edgeE1.src().toString().contains(e2Src.toString())){
                String forWala = e1DstClass + "." +e1DstMethodName;
                if (e2Dst.toString().contains(forWala)){
                    return true;
                }

                forWala = e2DestClass + "." + e2DestMethodName;
                if (edgeE1.dst().toString().contains(forWala)){
                    return true;
                }
            }
        } else if (e1SrcClass.equals(e1SrcMethodName) && e1DstClass.equals(e1DstMethodName)) {
            // When the class name and method name of the edges are same then only compare the method names of the
            // two edges.
            if (e2SrcMethodName.contains(e1SrcMethodName) || e1SrcMethodName.contains(e2SrcMethodName)) {
                if (e1DstMethodName.contains(e2DestMethodName) || e2DestMethodName.contains(e1DstMethodName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that compares two ProbeMethods
     * @param src probe method a
     * @param dst probe method b
     * @return true if a == b else false
     */
    private static boolean compareProbeMethods(ProbeMethod src, ProbeMethod dst){
        String source = src.toString().replace(" ", "");
        String dest = dst.toString().replace(" ", "");
        return source.contains(dest) || dest.contains(source);
    }

    private static CallEdge handleWalaMain(Set<CallEdge> sourceEdges, ProbeMethod src, ProbeMethod dst) {
        Iterator<CallEdge> callEdgeIterator = sourceEdges.iterator();
        while (callEdgeIterator.hasNext()){
            CallEdge edge = callEdgeIterator.next();
            if (edge.toString().contains("__main__")) {
                if (compareProbeMethods(dst, edge.dst())) {
                    sourceEdges.remove(edge);
                    return edge;
                }
            }
        }
        return null;
    }

    private static CallEdge searchEdge(Set<CallEdge> sourceEdges, ProbeMethod src, ProbeMethod dst){
        if (src.toString().contains("FakeRootClass")) {
            return handleWalaMain(sourceEdges, src, dst);
        }

        Iterator<CallEdge> callEdgeIterator = sourceEdges.iterator();
        while (callEdgeIterator.hasNext()){
            CallEdge edge = callEdgeIterator.next();
            if (compareProbeMethods(edge.src(), src) && compareProbeMethods(edge.dst(), dst)){
                sourceEdges.remove(edge);
                return edge;
            } else if (deepCompare(edge, src, dst)){
                sourceEdges.remove(edge);
                return edge;
            }
        }
        return null;
    }

    /**
     * Method that compares two Probe callgraphs and records respective metrics.
     * @param dynCG dynamic CG
     * @param staticCG static CG
     * @param m Metrics object
     */
    public static void compareGraphs(CallGraph dynCG, CallGraph staticCG, Metrics m){
        Set<CallEdge> staticEdges = staticCG.edges();
        m.setTotalStaticEdges(staticEdges.size());
        Set<CallEdge> dynEdges = dynCG.edges();
        Set<CallEdge> presentDynEdges = HashSetFactory.make();
        Set<CallEdge> falseNegatives = new HashSet<CallEdge>(){{
            addAll(dynEdges);
        }};
        LOG.fine("Total static edges: " + staticEdges.size() + ", dynamic edges: " + dynEdges.size());
        for (CallEdge staticEdge: staticEdges){
            CallEdge presentDynEdge = searchEdge(dynEdges, staticEdge.src(), staticEdge.dst());
            if (presentDynEdge != null && !presentDynEdges.contains(presentDynEdge)){
                m.addTP(staticEdge); //Present in both static and dynamic CGs
                presentDynEdges.add(presentDynEdge);
            } else {
                m.addFP(staticEdge); //Present in static but absent in dynamic CG
            }
        }

        falseNegatives.removeAll(presentDynEdges);
        for (CallEdge dynEdge: falseNegatives){
            m.addFN(dynEdge); //Present in dynamic CG but absent in static CG
        }
    }
}
