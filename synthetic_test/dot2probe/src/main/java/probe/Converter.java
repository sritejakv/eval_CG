package probe;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import utils.DotfileReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Converter {

    /**
     * Intermediate method to get probeCG.
     * @param dotFilePath file path of the dot graph
     * @return array list of probe CG objects
     */
    public ArrayList<probe.CallGraph> getProbeCG(String dotFilePath){
        GraphParser p = DotfileReader.parseDotFile(dotFilePath);
        return buildProbeCG(p);
    }

    /**
     * Intermediate method to get probeCG.
     * @param dotFileStream file stream of the dot file
     * @return array list of probe CG objects
     */
    public ArrayList<probe.CallGraph> getProbeCG(InputStream dotFileStream){
        GraphParser p = DotfileReader.parseDotFile(dotFileStream);
        return buildProbeCG(p);
    }

    /**
     * Method that converts dot graph to probe CG
     * @param p GraphParser object
     * @return array list of probe CG objects
     */
    private ArrayList<probe.CallGraph> buildProbeCG(GraphParser p){
        ArrayList<probe.CallGraph> probeCGList = new ArrayList<>();
        probe.CallGraph probeCG =new probe.CallGraph();
        Map<String, GraphEdge> dotEdges = p.getEdges();
        for (GraphEdge dotEdge : dotEdges.values()) {
            GraphNode sourceNode = dotEdge.getNode1();
            GraphNode targetNode = dotEdge.getNode2();
            ProbeMethod sourceMethod = buildProbeMethod(sourceNode);
            ProbeMethod targetMethod = buildProbeMethod(targetNode);
            probe.CallEdge callEdge = new probe.CallEdge(sourceMethod, targetMethod);
            probeCG.entryPoints().add(sourceNode);
            ((Set<CallEdge>) probeCG.edges()).add(callEdge);
        }
        probeCGList.add(probeCG);
        return probeCGList;
    }

    /**
     * Method to split class name and method name from the dot graph node.
     * @param dotNode dot graph node with full name
     * @return probe method derived from full name
     */
     private ProbeMethod buildProbeMethod(GraphNode dotNode){
        String callSignature = getCallSignature(dotNode);
        List<String> callSignatureSplit = splitFullName(callSignature);
        if (callSignatureSplit.size() > 1){
            String className = null, methodName = null, pkgName = "";
            for (int index = callSignatureSplit.size() - 1; index >= 0; index--){
                if (methodName == null){
                    methodName = callSignatureSplit.get(index);
                } else if (className == null){
                    className = callSignatureSplit.get(index);
                } else {
                    pkgName = callSignatureSplit.get(index).concat(".".concat(pkgName));
                }
            }
            if (pkgName.length() > 0){
                pkgName = pkgName.substring(0, pkgName.length() - 1);
            }
            return ObjectManager.v().getMethod(ObjectManager.v().getClass(pkgName, className), methodName, "");
        } else {
            return ObjectManager.v().getMethod(ObjectManager.v().getClass(callSignatureSplit.get(0)), callSignatureSplit.get(0), "");
        }
    }

    /**
     * Method to split the dot node according to the respective regex character.
     * @param fullName full name of the dot node
     * @return List containing the pruned full name of the dot node
     */
    public abstract List<String> splitFullName(String fullName);

    /**
     * Method to get strip the call signature from the node full name.
     * @param dotNode dotNode with full name
     * @return function call signature
     */
    public String getCallSignature(GraphNode dotNode){
        return dotNode.getId();
    }
}
