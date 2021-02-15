package wala;

import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.SourceURLModule;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.collections.HashSetFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Class to get nCFA call graph of wala. Extends FrameworkAdapter to facilitate comparison of call graphs.
 */
public class WalaNCFA {

    private Map<String, String> exceptionMap;

    public WalaNCFA() {
        this.exceptionMap = HashMapFactory.make();
    }

    public Map<String, String> getExceptionMap() {
        return exceptionMap;
    }

    public Set<String> pruneSourceFiles(List<File> sourceFiles) throws MalformedURLException {
        Set<String> prunedFiles = HashSetFactory.make();
        for(File fileName: sourceFiles){
            SourceURLModule file  = new SourceURLModule(fileName.toURI().toURL());
            NCFAPythonEngine engine = new NCFAPythonEngine();
            Set<Module> modules = HashSetFactory.make();
            modules.add(file);
            engine.setModuleFiles(modules);
            CallGraphBuilder<? super InstanceKey> builder = null;
            try {
                builder = engine.defaultCallGraphBuilder();
                builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
                prunedFiles.add(fileName.getAbsolutePath());
            } catch (CancelException | IOException | com.ibm.wala.util.debug.UnimplementedError |
                    com.ibm.wala.util.WalaRuntimeException | IllegalArgumentException e) {
                exceptionMap.put(fileName.toString(), e.toString());
            }
        }
        return prunedFiles;
    }
}