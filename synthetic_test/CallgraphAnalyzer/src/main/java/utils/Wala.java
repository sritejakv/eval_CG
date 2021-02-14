package utils;

import com.ibm.wala.classLoader.SourceURLModule;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.util.graph.Graph;
import probe.CallEdge;
import probe.ObjectManager;
import probe.ProbeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;

/**
 * Utility class to convert the call graph generated by wala into probe CG
 * Source: https://bitbucket.org/Li_Sui/micro-benchmark/src/master/experiment/src/main/java/nz/ac/massey/cs/staticAnalyser/wala/Wala.java
 */
public class Wala {

    public static SourceURLModule getPythonScript(String fileName) throws MalformedURLException {
        File pythonFile = new File(ResourceReader.getResource(Constants.BENCHMARK_SUITE_FOLDER_NAME,
                fileName.split("\\.")[0] + ".py"));
        if (pythonFile.exists()){
            return new SourceURLModule(pythonFile.toURI().toURL());
        } else {
            throw new MalformedURLException("Resource not found - " + fileName);
        }
    }

    public probe.CallGraph build(Graph<CGNode> walaCG, String fileName){
        fileName = fileName.split("\\.")[0];
        probe.CallGraph cg=new probe.CallGraph();
        Iterator<CGNode> iter =walaCG.iterator();
        while(iter.hasNext()){
            CGNode n=iter.next();
            Iterator<CGNode> succIter=walaCG.iterator();
            while(succIter.hasNext()) {
                CGNode n2 = succIter.next();
                if (walaCG.hasEdge(n, n2)) {
                    ProbeMethod source =buildProbeMethod(n, fileName);
                    ProbeMethod target=buildProbeMethod(n2, fileName);
                    probe.CallEdge probeEdge = new CallEdge(source,target);
                    cg.entryPoints().add(source);
                    ((Set<CallEdge>)cg.edges()).add(probeEdge);
                }
            }
        }
        return cg;
    }

    //TODO check for any threat validations
    private String handleScriptNode(String node){
        String[] nodePySplit = node.split("\\.py");
        String finalString = "";
        for (String s: nodePySplit){
            if (s.contains("script")){
                String[] temp = s.split("script");
                for (String t: temp){
                    if (!t.contains("script")){
                        finalString = finalString.concat(t.replace(" ", "")).
                                replace("$", "");
                    }
                }
            } else {
                String[] temp = s.split("\\.");
                for (String t: temp){
                    if (t.length() > 0) {
                        finalString = finalString.concat(".").concat(t.replace(" ", ""));
                    }
                }
            }
        }
        return finalString;
    }

    private ProbeMethod buildProbeMethod(CGNode n, String fileName){
        String declaredClass = stringify(n.getMethod().getDeclaringClass().getName().toString());

        if (declaredClass.contains("script")) {
            String x = handleScriptNode(declaredClass);
            return ObjectManager.v().getMethod(ObjectManager.v()
                    .getClass(fileName), x, "");
        }

//        if (declaredClass.equals("com.ibm.wala.FakeRootClass")){
//            return ObjectManager.v().getMethod(ObjectManager.v()
//                    .getClass(fileName), fileName, "");
//        }
        
        String name = n.getMethod().getName().toString();

//        if (name.equals("do")){
//            return ObjectManager.v().getMethod(ObjectManager.v()
//                    .getClass(declaredClass), fileName, "");
//        }

        String returnType =stringify(n.getMethod().getReturnType().getName().toString());
        StringBuilder paramType = new StringBuilder();

        for(int i =1; i<n.getMethod().getNumberOfParameters();i++){
            paramType.append(stringify(n.getMethod().getParameterType(i).getName().toString()));
            if(i<n.getMethod().getNumberOfParameters()-1){
                paramType.append(",");
            }
        }
        String signature=returnType+" "+name+"("+paramType.toString()+")";

        return ObjectManager.v().getMethod(ObjectManager.v()
                .getClass(declaredClass),name,signature);
    }

    private String stringify(String s){
        //object
        if(s.startsWith("L")){
            return s.substring(1).replace("/",".");
        }
        //void return type
        if(s.equals("V")){
            return "void";
        }
        //primitive
        if(s.equals("I")){
            return "int";
        }
        if(s.equals("B")){
            return "byte";
        }
        if(s.equals("J")){
            return "long";
        }
        if(s.equals("F")){
            return "float";
        }
        if(s.equals("D")){
            return "double";
        }
        if(s.equals("S")){
            return "short";
        }
        if(s.equals("C")){
            return "char";
        }
        if(s.equals("Z")){
            return "boolean";
        }

        //array
        if(s.startsWith("[L") || s.startsWith("[I") || s.startsWith("[B") || s.startsWith("[J")||
                s.startsWith("[F") ||
                s.startsWith("[D") ||
                s.startsWith("[S") ||
                s.startsWith("[C") ||
                s.startsWith("[Z")){
            return stringify(s.substring(1));
        }
        return s;
    }
}