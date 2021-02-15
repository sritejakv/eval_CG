import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import utils.Constants;
import utils.DotUtils;
import utils.ResourceReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ExtractEntryPoints {
    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINE);
        }
    }

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Set<String> visitedGraphNodes, entryPoints;
    private String realWorldLib, outputDirectory;
    private List<File> sourceFiles;

    public ExtractEntryPoints(String lib, String outputDir){
        visitedGraphNodes = new HashSet<>();
        entryPoints = new HashSet<>();
        realWorldLib = lib;
        outputDirectory = outputDir.length() > 0 ? outputDir : Constants.DEFAULT_OUTPUT_DIR;
        sourceFiles = ResourceReader.getFilesInResource(realWorldLib);
    }

    private void checkForPythonExtension(String fileName){
        fileName = fileName + Constants.PYTHON_EXTENSION;
        for (File sourceFile : sourceFiles) {
            if (sourceFile.getName().equalsIgnoreCase(fileName)) {
                entryPoints.add(sourceFile.getAbsolutePath());
                break;
            }
        }
    }

    private List<String> pruneEntryPoint(String graphNode){
        String[] splitNode = graphNode.split(File.separator);
        List<String> splitNodeList = new LinkedList<>();
        for (String splitString : splitNode) {
            if (!(splitString.startsWith("<"))) {
                splitNodeList.add(splitString);
            }
        }
        return splitNodeList;
    }

    private void recordEntryPoint(String graphNode){
        List<String> splitList = pruneEntryPoint(graphNode);
        for (String splitString : splitList){
            checkForPythonExtension(splitString);
        }
    }

    private void analyzeNode(String graphNode){
        graphNode = graphNode.replace("\"", "");
        graphNode = graphNode.replace(".", File.separator);
        if (!visitedGraphNodes.contains(graphNode)){
            visitedGraphNodes.add(graphNode);
            recordEntryPoint(graphNode);
        }
    }

    public void extractEntryPoints(){
        GraphParser p = DotUtils.parseDotFile(Constants.RESOURCES_PATH + File.separator +
                Constants.MERGED_PYCALLGRAPH_DIR + File.separator + realWorldLib + ".dot");
        Map<String, GraphNode> dotNodes = p.getNodes();
        for(GraphNode node: dotNodes.values()){
            analyzeNode(node.getId());
        }
    }

    public void writeToFile() throws IOException {
        File dir = new File(outputDirectory);
        File f = new File(outputDirectory + File.separator + realWorldLib + ".txt");
        if (!dir.exists()){
            dir.mkdir();
        }

        FileWriter outFile = new FileWriter(f);
        for (String entryPoint: entryPoints){
            outFile.write(entryPoint + System.lineSeparator());
        }
        outFile.close();
    }

    public static void main(String... args) throws IOException {
        for (String realWorldLib : args) {
            LOG.fine("Extracting entry points of the library: " + realWorldLib);
            ExtractEntryPoints obj = new ExtractEntryPoints(realWorldLib, "");
            obj.extractEntryPoints();
            obj.writeToFile();
        }
    }

}