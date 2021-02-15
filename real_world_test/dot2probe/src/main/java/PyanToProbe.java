import probe.CallGraph;
import probe.PyanToProbeConverter;

import java.util.ArrayList;

public class PyanToProbe {

    /**
     * Method that reads a file from the input path and returns list of probe CGs.
     * @param dotFilePath file path of the dot file
     * @return array list of probe CGs
     */
    public ArrayList<CallGraph> convertToProbe(String dotFilePath){
        PyanToProbeConverter pyanToProbeConverter = new PyanToProbeConverter();
        return pyanToProbeConverter.getProbeCG(dotFilePath);
    }
}
