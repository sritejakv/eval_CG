package probe;

import com.paypal.digraph.parser.GraphNode;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/***
 * Class that converts dot graph to probe CG.
 */
public class PyCallGraphToProbeConverter extends Converter {

    /**
     * String to be replaced with root node of PyCallGraph.
     */
    public String rootNodeReplaceString;

    public void setReplacementString(String file){
        this.rootNodeReplaceString = getReplacementString(file);;
    }

    /**
     * Returns the file name without extension.
     * @param fileName python file path
     * @return filename without extension
     */
    private String getReplacementString(String fileName){
        String[] nameSplit = fileName.split("\\.");
        return nameSplit[0];
    }

    @Override
    public List<String> splitFullName(String fullName) {
        return Arrays.asList(fullName.split("\\."));
    }

    @Override
    public String getCallSignature(GraphNode dotNode){
        String nodeId = dotNode.getId();
        nodeId = nodeId.replace("<module>", rootNodeReplaceString);
        nodeId = nodeId.replace("\"", "");
        return nodeId;
    }
}
