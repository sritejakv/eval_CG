package utils;

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

    public static final String PYAN_STATIC_CG_PATH = "pyan";
    public static final String PYCALLGRAPH_DYNAMIC_CG_PATH = "pycallgraph";
    public static final String CODE2FLOW_STATIC_CG_PATH = "code2flow";
    public static final String BENCHMARK_SUITE_FOLDER_NAME = "BenchmarkSuite";

    public static final String PYAN_RUN_TIME_NODE = "pyan_run_time";
    public static final String CODE2FLOW_RUN_TIME_NODE = "code2flow_run_time";

    public static final String DOT_SUFFIX = "dot";

    public static final String DEFAULT_REPORT_DIR = "reports";
    public static final String[] REPORTS_CSV_HEADER = {"Category", "File", "Precision", "Recall", "Runtime", "F1Score", "Exception",
            "TotalDynamicEdges", "TotalStaticEdges", "TruePositives", "FalsePositives", "FalseNegatives"};
    public static final String[] ANALYZE_CSV_HEADER = {"Category", "File", "DynamicEdges", "TruePositives", "FalsePositives", "FalseNegatives"};
    public static final String PYAN_CSV_NAME = "Pyan.csv";
    public static final String CODE2FLOW_CSV_NAME = "Code2flow.csv";
    public static final String WALA_NCFA_CSV_NAME = "WalaNCFA.csv";
    public static final String WALA_V01_CFA_CSV_NAME = "WalaVanillaZeroOneCFA.csv";
    public static final String WALA_0_CFA_CSV_NAME = "WalaZeroCFA.csv";
    public static final String WALA_0C_CFA_CSV_NAME = "WalaZeroContainerCFA.csv";
    public static final String WALA_01_CFA_CSV_NAME = "WalaZeroOneCFA.csv";
    public static final String WALA_01C_CFA_CSV_NAME = "WalaZeroOneContainerCFA.csv";

    public static final String RESOURCES_PATH = "src/main/resources/";

    public static final List<String> benchmarkCategories = new ArrayList<String>(){{
        add("branching");
        add("code_generation");
        add("decorators");
        add("direct_functions");
        add("duck_typing");
        add("lambda_functions");
        add("library_loading");
        add("object_changes");
        add("polymorphic_functions");
        add("recursion");
        add("reflection");
        add("static_functions");
        add("nested_code");
    }};
    public static final String PYTHON_INIT = "__init__";
}
