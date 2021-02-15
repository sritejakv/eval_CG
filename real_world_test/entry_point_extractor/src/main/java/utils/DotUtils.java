package utils;

import com.paypal.digraph.parser.GraphParser;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DotUtils {

    /**
     * Method to parse the dot graph.
     * @param dotFilePath path of the file
     * @return parser object of the dot graph
     */
    public static GraphParser parseDotFile(String dotFilePath){
        GraphParser parser = null;
        try {
            parser = new GraphParser(new FileInputStream(dotFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public static void mergeDotGraphs(String realWorldLibrary) throws IOException {
        MutableGraph mutableGraph = Factory.
                mutGraph(realWorldLibrary)
                .setDirected(true);
        try (Stream<Path> walk = Files.walk(Paths.get(Constants.RESOURCES_PATH + File.separator +
                Constants.PYCALLGRAPHS_FOLDER_NAME + File.separator + realWorldLibrary), Integer.MAX_VALUE)) {

            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(Constants.DOT_SUFFIX)).collect(Collectors.toList());

            result.forEach(f -> {
                try {
                    new Parser().read(new File(f)).addTo(mutableGraph);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        String directoryName = Constants.RESOURCES_PATH + File.separator + Constants.MERGED_PYCALLGRAPH_DIR;
        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdir();
        }
        FileWriter fileWriter = new FileWriter(directoryName + File.separator + realWorldLibrary + ".dot");
        fileWriter.write(mutableGraph.toString());
        fileWriter.close();
    }
}
