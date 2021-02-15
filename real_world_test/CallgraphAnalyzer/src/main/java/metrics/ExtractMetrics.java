package metrics;

import com.ibm.wala.util.collections.HashSetFactory;
import probe.CallEdge;
import probe.CallGraph;
import probe.ProbeMethod;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class ExtractMetrics {

    private final static Logger LOG = Logger.getLogger(ExtractMetrics.class.getName());

    /**
     * This method is for WALA. As the class name of the probe class of wala CG is taken as the
     * Python project name and the actual class name is merged into the probe method of the edge.
     * Hence, for wala, the class of Pycallgraph probe edge is compared with the method of wala probe edge.
     * @param dynEdge Probe class of the dynamic edge
     * @param staticEdge Probe method of wala static edge
     * @return boolean
     */
    private static boolean compareClassWithMethod(String dynEdge, String staticEdge){
        String[] dynEdgeSplit = dynEdge.split("\\.");
        String[] staticEdgeSplit = staticEdge.split("\\.");
        int match = 0;
        int dynEdgeIndex = dynEdgeSplit.length - 1;
        for (int i = staticEdgeSplit.length - 1; i >= 0; i--) {
            if (dynEdgeIndex >= 0 && staticEdgeSplit[i].equalsIgnoreCase(dynEdgeSplit[dynEdgeIndex])) {
                dynEdgeIndex--;
                match++;
            }
        }
        if (match > 1)
            return true;
        return false;
    }

    /**
     * Deeply compare the two edges along with method names and the class names
     * @param edgeE1 Edge from a dynamic CG
     * @param e2Src Source Method of an edge from static CG
     * @param e2Dst Target Method of an edge from static CG
     * @return boolean
     */
    private static boolean deepCompare(CallEdge edgeE1, ProbeMethod e2Src, ProbeMethod e2Dst){
        String e1SrcClass = edgeE1.src().cls().toString();
        String e1SrcMethodName = edgeE1.src().name();
        String e1DstClass = edgeE1.dst().cls().toString();
        String e1DstMethodName = edgeE1.dst().name();

        String e2SrcClass = e2Src.cls().toString();
        String e2SrcMethodName = e2Src.name();
        String e2DestClass = e2Dst.cls().toString();
        String e2DestMethodName = e2Dst.name();

        if (e1SrcMethodName.equalsIgnoreCase(e2SrcMethodName) || e2SrcMethodName.contains(e1SrcMethodName) ||
                e1SrcMethodName.contains(e2SrcMethodName)){
            if (e1DstMethodName.equals(e2DestMethodName) || e1DstMethodName.contains(e2DestMethodName) ||
            e2DestMethodName.contains(e1DstMethodName)){
                if (e1DstClass.contains(e2DestClass) || e2DestClass.equals(e2DestMethodName) ||
                e1DstClass.equals(e1DstMethodName))
                    return true;
            } else if (e2Src.toString().contains(edgeE1.src().toString()) ||
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
        } else if (compareClassWithMethod(e1SrcClass, e2SrcMethodName) && compareClassWithMethod(e1DstClass,
                e2DestMethodName)) {
            return true;
        }
        return false;
    }

    /**
     * Method that compares two ProbeMethods
     * @param src probe method a
     * @param dst probe method b
     * @return true if a contains b or b contains a else false
     */
    private static boolean compareProbeMethods(ProbeMethod src, ProbeMethod dst){
        String source = src.toString().replace(" ", "");
        String dest = dst.toString().replace(" ", "");
        return source.contains(dest) || dest.contains(source);
    }

    protected static CallEdge searchEdge(Set<CallEdge> sourceEdges, ProbeMethod src, ProbeMethod dst, boolean wala){
        Iterator<CallEdge> callEdgeIterator = sourceEdges.iterator();
        while (callEdgeIterator.hasNext()){
            CallEdge edge = callEdgeIterator.next();
            if (compareProbeMethods(edge.src(), src) && compareProbeMethods(edge.dst(), dst)){
                return edge;
            } else if (wala && deepCompare(edge, src, dst)){
                return edge;
            }
        }
        return null;
    }

    public static boolean compareEdges(CallEdge srcEdge, CallEdge targetEdge, boolean wala) {
        String e1SrcClass = srcEdge.src().cls().toString();
        String e1SrcMethodName = srcEdge.src().name();
        String e1DstClass = srcEdge.dst().cls().toString();
        String e1DstMethodName = srcEdge.dst().name();

        String e2SrcClass = targetEdge.src().cls().toString();
        String e2SrcMethodName = targetEdge.src().name();
        String e2DestClass = targetEdge.dst().cls().toString();
        String e2DestMethodName = targetEdge.dst().name();

        if (compareProbeMethods(srcEdge.src(), targetEdge.src()) &&
                compareProbeMethods(srcEdge.dst(), targetEdge.dst())) {
            return true;
        }

        if (wala)
            return compareClassWithMethod(e1SrcClass, e2SrcMethodName) && compareClassWithMethod(e1DstClass,
                    e2DestMethodName);

        if (e1SrcMethodName.equalsIgnoreCase(e2SrcMethodName)){
            if (e1DstMethodName.equals(e2DestMethodName) || e1DstMethodName.contains(e2DestMethodName) ||
                    e2DestMethodName.contains(e1DstMethodName)){
                if (e1DstClass.contains(e2DestClass) || e2DestClass.equals(e2DestMethodName) ||
                        e1DstClass.equals(e1DstMethodName))
                    return true;
            } else if (targetEdge.src().toString().contains(srcEdge.src().toString()) ||
                    srcEdge.src().toString().contains(targetEdge.src().toString())) {
                String forWala = e1DstClass + "." +e1DstMethodName;
                if (targetEdge.dst().toString().contains(forWala)){
                    return true;
                }

                forWala = e2DestClass + "." + e2DestMethodName;
                if (srcEdge.dst().toString().contains(forWala)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void compareEdgeSets(Set<CallEdge> srcEdges, Set<CallEdge> tgtEdges, FrameworkMetrics m,
                                       boolean wala, boolean setCommonFromSrc) {
        for (CallEdge e: tgtEdges) {
            CallEdge presentEdge = searchEdge(srcEdges, e.src(), e.dst(), wala);
            if (presentEdge != null) {
                if (setCommonFromSrc) {
                    m.addCommonEdge(presentEdge);
                } else {
                    m.addCommonEdge(e);
                }
            }
        }
    }

    /**
     * Method that compares two Probe callgraphs and records respective metrics.
     * @param dynCG dynamic CG
     * @param staticCG static CG
     * @param m Metrics object
     */
    public static void compareGraphs(CallGraph dynCG, CallGraph staticCG, Metrics m, boolean wala){
        Set<CallEdge> staticEdges = staticCG.edges();
        m.setTotalStaticEdges(staticCG.edges());
        Set<CallEdge> dynEdges = dynCG.edges();
        m.setTotalDynamicEdges(dynCG.edges());
        Set<CallEdge> presentDynEdges = HashSetFactory.make();
        Set<CallEdge> falseNegatives = new HashSet<CallEdge>(){{
            addAll(dynEdges);
        }};
        LOG.fine("Total static edges: " + staticEdges.size() + ", dynamic edges: " + dynEdges.size());
        for (CallEdge staticEdge: staticEdges){
            CallEdge presentDynEdge = searchEdge(dynEdges, staticEdge.src(), staticEdge.dst(), wala);
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
