package probe;

import com.paypal.digraph.parser.GraphNode;

import java.util.ArrayList;
import java.util.List;

public class Code2flowToProbeConverter extends Converter {

    @Override
    public List<String> splitFullName(String fullName) {
        String[] fullNameList = fullName.split("\\.");
        ArrayList<String> prunedFullName = new ArrayList<>();
        for (String token: fullNameList){
            if (!token.contains("(") && !token.contains(")") && !token.contains("-")){ 
                prunedFullName.add(token);
            }
        }
        return prunedFullName;
    }

    @Override
    public String getCallSignature(GraphNode dotNode){
        String[] nodeSplit = dotNode.getAttribute("label").toString().split(":");
        return nodeSplit[nodeSplit.length - 1];
    }
}
