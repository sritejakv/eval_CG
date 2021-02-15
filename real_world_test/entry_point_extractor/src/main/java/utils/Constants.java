package utils;

import java.io.File;

public class Constants {

    public static final String DOT_SUFFIX = "dot";
    public static final String PYTHON_SUFFIX = "py";
    public static final String JSON_SUFFIX = "json";
    public static final String PYTHON_EXTENSION = ".py";
    public static final String RESOURCES_PATH = "src" + File.separator + "main" + File.separator + "resources";
    public static final String PYCALLGRAPHS_FOLDER_NAME = "pycallgraphs";
    public static final String MERGED_PYCALLGRAPH_DIR = "merged_pycallgraphs";
    public static final String REAL_WORLD_LIB_FOLDER_NAME = "realWorldLibSrc";
    public static final String PRUNED_ENTRY_POINTS_FOLDER_NAME = "prunedEntryPoints";

    public static final String DEFAULT_OUTPUT_DIR = "entryPoints";
    public static final String COMMON_EP_OUTPUT_DIR = "commonEntryPoints";
    public static final String EP_METRICS_OUTPUT_DIR = "entryPointMetrics";
    public static final String EP_METRICS_FILE_NAME = "entry_point_metrics.csv";

    public static final String[] REPORTS_CSV_HEADER = {"RealWorldLib", "Framework", "TotalSourceFiles",
            "SuccessfullyRunFiles", "CommonFiles"};
}
