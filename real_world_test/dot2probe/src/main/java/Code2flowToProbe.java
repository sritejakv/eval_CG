import probe.CallGraph;
import probe.Code2flowToProbeConverter;

import java.util.ArrayList;

public class Code2flowToProbe {

    /**
     * Method that reads a file from the input path and returns list of probe CGs.
     * @param dotFilePath file path of the dot file
     * @return array list of probe CGs
     */
    public ArrayList<CallGraph> convertToProbe(String dotFilePath){
        Code2flowToProbeConverter pyanToProbeConverter = new Code2flowToProbeConverter();
        return pyanToProbeConverter.getProbeCG(dotFilePath);
    }
}
