package wala;

import com.ibm.wala.cast.python.client.PythonAnalysisEngine;
import com.ibm.wala.cast.python.loader.PythonLoaderFactory;
import com.ibm.wala.cast.python.types.PythonTypes;
import com.ibm.wala.cast.python.util.PythonInterpreter;
import com.ibm.wala.cast.types.AstMethodReference;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.ModuleEntry;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.DefaultEntrypoint;
import com.ibm.wala.ipa.callgraph.propagation.PropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.collections.HashSetFactory;

import java.util.Set;

/**
 * Wala python engine to generate nCFA call graph.
 */
public class NCFAPythonEngine extends PythonAnalysisEngine<Void> {

    static {
        try {
            Class<?> j3 = Class.forName("com.ibm.wala.cast.python.loader.Python3LoaderFactory");
            NCFAPythonEngine.setLoaderFactory((Class<? extends PythonLoaderFactory>) j3);
            Class<?> i3 = Class.forName("com.ibm.wala.cast.python.util.Python3Interpreter");
            PythonInterpreter.setInterpreter((PythonInterpreter)i3.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                Class<?> j2 = Class.forName("com.ibm.wala.cast.python.loader.Python2LoaderFactory");
                NCFAPythonEngine.setLoaderFactory((Class<? extends PythonLoaderFactory>) j2);
                Class<?> i2 = Class.forName("com.ibm.wala.cast.python.util.Python2Interpreter");
                PythonInterpreter.setInterpreter((PythonInterpreter)i2.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
                assert false : e.getMessage() + ", then " + e1.getMessage();
            }
        }
    }

    public NCFAPythonEngine(){
        super();
    }

    @Override
    protected Iterable<Entrypoint> makeDefaultEntrypoints(AnalysisScope scope, IClassHierarchy cha) {
        Set<Entrypoint> result = HashSetFactory.make();
        for(Module m : moduleFiles) {
            IClass entry = cha.lookupClass(TypeReference.findOrCreate(PythonTypes.pythonLoader,
                    TypeName.findOrCreate(scriptName(m))));
            assert entry != null: "bad root name " + scriptName(m) + ":\n" + cha;
            MethodReference er = MethodReference.findOrCreate(entry.getReference(), AstMethodReference.fnSelector);
            result.add(new DefaultEntrypoint(er, cha));
        }


        for (IClass klass : cha) {
            for (IMethod method : klass.getDeclaredMethods()) {
                MethodReference er = method.getReference();
                result.add(new DefaultEntrypoint(er, cha));
            }
        }

        return result;
    }

    private String scriptName(Module m) {
        String path = ((ModuleEntry)m).getName();
        return "Lscript " + (path.contains("/") ? path.substring(path.lastIndexOf(47) + 1) : path);
    }

    @Override
    public Void performAnalysis(PropagationCallGraphBuilder propagationCallGraphBuilder) throws CancelException {
        return null;
    }
}
