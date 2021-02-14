package utils;

import com.paypal.digraph.parser.GraphParser;

import java.io.*;

/***
 * Utility class for file reading and parsing operations.
 */
public class DotfileReader {

    /**
     * Method to read a file.
     * @param filePath path of the file
     * @return file reader object of the file
     */
    public static FileReader readDotFile(String filePath){
        FileReader dotFile = null;
        try {
            dotFile = new FileReader(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dotFile;
    }

    /**
     * Method to parse the dot graph.
     * @param dotFileStream file reader object containing the dot graph
     * @return parser object of the dot graph
     */
    public static GraphParser parseDotFile(InputStream dotFileStream){
        GraphParser parser = new GraphParser(dotFileStream);
        return parser;
    }

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
}
