package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String PYAN_FRAMEWORK = "Pyan";
    public static final String PYAN_CALL_GRAPH = "PyanCG";
    public static final String CODE2FLOW_FRAMEWORK = "Code2flow";
    public static final String CODE2FLOW_CALL_GRAPH = "Code2flowCG";
    public static final String WALA_FRAMEWORK = "Wala";
    public static final String WALA_NCFA_CALL_GRAPH = "WalaNCFA";
    public static final String WALA_VANILLA_ZERO_CFA_CALL_GRAPH = "WalaVanilla0CFA";
    public static final String WALA_ZERO_CFA_CALL_GRAPH = "Wala0CFA";
    public static final String WALA_ZERO_CONTAINER_CFA_CALL_GRAPH = "Wala0ContainerCFA";
    public static final String WALA_ZERO_ONE_CFA_CALL_GRAPH = "Wala01CFA";
    public static final String WALA_ZERO_ONE_CONTAINER_CFA_CALL_GRAPH = "Wala01ContainerCFA";

    public static final String RESOURCES_PATH = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator;
    public static final String DOT_SUFFIX = "dot";
    public static final String PY_SUFFIX = "py";
    public static final String PYCALLGRAPH_DYNAMIC_CG_PATH = "pycallgraph";
    public static final String PYAN_STATIC_CG_PATH = "pyan";
    public static final String CODE2FLOW_STATIC_CG_PATH = "code2flow";
    public static final String ENTRYPOINTS_RESOURCES_FOLDER = "entryPoints";

    public static final String PYAN_RUN_TIME_NODE = "pyan_run_time";
    public static final String CODE2FLOW_RUN_TIME_NODE = "code2flow_run_time";

    public static final String DEFAULT_REPORT_DIR = "reports";
    public static final String[] REPORTS_CSV_HEADER = {"RealWorldLib", "Precision", "Recall", "Runtime",
            "TotalStaticEdges", "TotalDynamicEdges", "TruePositives", "FalsePositives", "FalseNegatives", "Exception"};
    public static final String[] FRAMEWORK_COMPARISON_REPORT_CSV_HEADER = {"RealWorldLib", "Framework", "TotalDynamicEdges",
            "TotalStaticEdges", "CommonEdges", "PresentDynEdges"};
    public static final String PYAN_CSV_NAME = "Pyan.csv";
    public static final String CODE2FLOW_CSV_NAME = "Code2flow.csv";
    public static final String WALA_NCFA_CSV_NAME = "WalaNCFA.csv";
    public static final String WALA_V01_CFA_CSV_NAME = "WalaVanillaZeroOneCFA.csv";
    public static final String WALA_0_CFA_CSV_NAME = "WalaZeroCFA.csv";
    public static final String WALA_0C_CFA_CSV_NAME = "WalaZeroContainerCFA.csv";
    public static final String WALA_01_CFA_CSV_NAME = "WalaZeroOneCFA.csv";
    public static final String WALA_01C_CFA_CSV_NAME = "WalaZeroOneContainerCFA.csv";
    public static final String FRAMEWORK_COMPARISON_CSV_NAME = "FrameworkComparison.csv";

    public static final List<String> realWorldLibraries = new ArrayList<String>(){{
        add("PythonRobotics");
        add("mitmproxy");
        add("cookiecutter");
        add("ycm");
        add("thefuck");
    }};
}
