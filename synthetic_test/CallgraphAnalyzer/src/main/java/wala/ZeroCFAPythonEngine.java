package wala;

import com.ibm.wala.cast.ipa.callgraph.AstCFAPointerKeys;
import com.ibm.wala.cast.ipa.callgraph.AstContextInsensitiveSSAContextInterpreter;
import com.ibm.wala.cast.ir.ssa.AstIRFactory;
import com.ibm.wala.cast.python.client.PythonAnalysisEngine;
import com.ibm.wala.cast.python.ipa.callgraph.PythonSSAPropagationCallGraphBuilder;
import com.ibm.wala.cast.python.ipa.callgraph.PythonScopeMappingInstanceKeys;
import com.ibm.wala.cast.python.ipa.summaries.PythonSuper;
import com.ibm.wala.cast.python.loader.PythonLoaderFactory;
import com.ibm.wala.cast.python.util.PythonInterpreter;
import com.ibm.wala.ipa.callgraph.AnalysisCacheImpl;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.IAnalysisCacheView;
import com.ibm.wala.ipa.callgraph.impl.ClassHierarchyClassTargetSelector;
import com.ibm.wala.ipa.callgraph.impl.ClassHierarchyMethodTargetSelector;
import com.ibm.wala.ipa.callgraph.impl.DefaultContextSelector;
import com.ibm.wala.ipa.callgraph.propagation.PropagationCallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.cfa.ZeroXInstanceKeys;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.ssa.SSAOptions;
import com.ibm.wala.ssa.SymbolTable;
import com.ibm.wala.util.CancelException;

/**
 * Wala python engine to generate 0-CFA call graph.
 */
public class ZeroCFAPythonEngine extends PythonAnalysisEngine<Void> {

    static {
        try {
            Class<?> j3 = Class.forName("com.ibm.wala.cast.python.loader.Python3LoaderFactory");
            ZeroCFAPythonEngine.setLoaderFactory((Class<? extends PythonLoaderFactory>) j3);
            Class<?> i3 = Class.forName("com.ibm.wala.cast.python.util.Python3Interpreter");
            PythonInterpreter.setInterpreter((PythonInterpreter)i3.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                Class<?> j2 = Class.forName("com.ibm.wala.cast.python.loader.Python2LoaderFactory");
                ZeroCFAPythonEngine.setLoaderFactory((Class<? extends PythonLoaderFactory>) j2);
                Class<?> i2 = Class.forName("com.ibm.wala.cast.python.util.Python2Interpreter");
                PythonInterpreter.setInterpreter((PythonInterpreter)i2.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
                assert false : e.getMessage() + ", then " + e1.getMessage();
            }
        }
    }

    public ZeroCFAPythonEngine() {
        super();
    }

    @Override
    public Void performAnalysis(PropagationCallGraphBuilder propagationCallGraphBuilder) throws CancelException {
        return null;
    }

    @Override
    protected PythonSSAPropagationCallGraphBuilder getCallGraphBuilder(IClassHierarchy cha, AnalysisOptions options, IAnalysisCacheView cache2) {
        IAnalysisCacheView cache = new AnalysisCacheImpl(AstIRFactory.makeDefaultFactory(), options.getSSAOptions());

        options.setSelector(new ClassHierarchyClassTargetSelector(cha));
        options.setSelector(new ClassHierarchyMethodTargetSelector(cha));

        addBypassLogic(cha, options);

        options.setUseConstantSpecificKeys(true);

        SSAOptions ssaOptions = options.getSSAOptions();
        ssaOptions.setDefaultValues(new SSAOptions.DefaultValues() {
            @Override
            public int getDefaultValue(SymbolTable symtab, int valueNumber) {
                return symtab.getNullConstant();
            }
        });
        options.setSSAOptions(ssaOptions);

        PythonSSAPropagationCallGraphBuilder builder =
                new PythonSSAPropagationCallGraphBuilder(cha, options, cache, new AstCFAPointerKeys());

        AstContextInsensitiveSSAContextInterpreter interpreter = new AstContextInsensitiveSSAContextInterpreter(options, cache);
        builder.setContextInterpreter(interpreter);

        //From com.ibm.wala.ipa.callgraph.impl.Util.java::makeZeroCFABuilder::353 -> com.ibm.wala.ipa.callgraph.propagation.cfa.ZeroXCFABuilder::40
        builder.setContextSelector(new DefaultContextSelector(options, cha));

        builder.setInstanceKeys(new PythonScopeMappingInstanceKeys(builder, new ZeroXInstanceKeys(
                options,
                cha,
                interpreter,
                ZeroXInstanceKeys.NONE)));//From com.ibm.wala.ipa.callgraph.impl.Util.java::makeZeroCFABuilder::381

        new PythonSuper(cha).handleSuperCalls(builder, options);

        return builder;
    }
}
