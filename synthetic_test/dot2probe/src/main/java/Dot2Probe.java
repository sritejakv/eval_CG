import java.util.ArrayList;


/***
 * Class to convert dot call graph to probe CG.
 */
public class Dot2Probe {

    public static void main(String... args){
//        ArrayList<probe.CallGraph> probeCGList = new PyCallGraphToProbe().convertToProbe(
//                "src/test/resources/reflection/greet.dot");
//        ArrayList<probe.CallGraph> probeCGList = new PyanToProbe().convertToProbe(
//                "src/test/resources/pyan_architecture.dot");
        ArrayList<probe.CallGraph> probeCGList = new Code2flowToProbe().convertToProbe(
                "src/test/resources/code2flow.gv");
        for(probe.CallGraph probeCG: probeCGList){
            System.out.println(probeCG.toString());
        }

    }
}
