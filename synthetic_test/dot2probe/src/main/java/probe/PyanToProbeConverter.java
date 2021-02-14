package probe;

import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PyanToProbeConverter extends Converter {

    @Override
    public List<String> splitFullName(String fullName) {
        String[] fullNameSplit = fullName.split("__");
        List<String> prunedFullName = handlePythonInit(fullNameSplit);
        return prunedFullName;
    }

    private List<String> handlePythonInit(String[] fullNameSplit){
        List<String> prunedFullName = new ArrayList<String>();
        for(int index=0; index < fullNameSplit.length; index++){
            if (fullNameSplit[index].equals("init")){
                prunedFullName.add(Constants.PYTHON_INIT);
            } else if (!fullNameSplit[index].equals("")){
                prunedFullName.add(fullNameSplit[index]);
            }
        }
        return prunedFullName;
    }
}
