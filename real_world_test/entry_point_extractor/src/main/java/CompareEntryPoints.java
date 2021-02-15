import utils.CSVWriter;
import utils.Constants;
import utils.EntryPointMetrics;
import utils.ResourceReader;
import wala.WalaNCFA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Assumes that the respective entry points are already present in the resources - src/main/resources/prunedEntryPoints/
 * of the respective framework
 * java -cp entry_point_extractor.jar CompareEntryPoints _library1_ _library2_ _libraryn_
 */
public class CompareEntryPoints {

    private String realWorldLib, outputDir;

    public CompareEntryPoints(String outDir) {
        outputDir = outDir.length() > 0 ? outDir : Constants.COMMON_EP_OUTPUT_DIR;;
    }

    public void writeToFile(Set<String> commonEPs) throws IOException {
        File dir = new File(outputDir);
        File f = new File(outputDir + File.separator + realWorldLib + ".txt");

        if (!dir.exists()){
            dir.mkdir();
        }

        FileWriter outFile = new FileWriter(f);
        for (String entryPoint: commonEPs){
            outFile.write(entryPoint + System.lineSeparator());
        }

        outFile.close();
    }

    public void writeExceptionsToFile(Map<String, String> excepMap) throws IOException {
        String filePath = Constants.RESOURCES_PATH + File.separator + realWorldLib + "EntryPointExceptions" + ".txt";
        File f = new File(filePath);
        FileWriter outFile = new FileWriter(f);
        for (Map.Entry<String, String> excep: excepMap.entrySet()){
            outFile.write("Exception " + excep.getValue() + " in file " + excep.getKey());
        }

        outFile.close();
    }

    private Set<String> executeWala(EntryPointMetrics cur) throws IOException {
        WalaNCFA walaNCFA = new WalaNCFA();
        List<File> sourceFiles = ResourceReader.getFilesInResource(realWorldLib);
        cur.setTotalSourceFiles(sourceFiles.size());
        Set<String> prunedWalaFiles = walaNCFA.pruneSourceFiles(sourceFiles);
        cur.setSuccessfullyRun(prunedWalaFiles.size());
        writeExceptionsToFile(walaNCFA.getExceptionMap());
        return prunedWalaFiles;
    }

    public void process(String... arguments) throws IOException {
        List<EntryPointMetrics> m = new ArrayList<>();
        for (String lib: arguments) {
            realWorldLib = lib;
            EntryPointMetrics pyan = new EntryPointMetrics("Pyan", lib);
            Set<String> firstSource = ResourceReader.getEntryPointsAfterSplit(realWorldLib, "pyan");
            pyan.setSuccessfullyRun(firstSource.size());

            EntryPointMetrics wala = new EntryPointMetrics("Wala", lib);
            Set<String> walaPrunedSources = executeWala(wala);
            firstSource.retainAll(walaPrunedSources);
            wala.setSuccessfullyRun(walaPrunedSources.size());
            
            wala.setCommonFiles(firstSource.size());
            pyan.setCommonFiles(firstSource.size());
            pyan.setTotalSourceFiles(wala.getTotalSourceFiles());

            m.add(wala);
            m.add(pyan);
            writeToFile(firstSource);
        }
        CSVWriter.writeEntryPointMetrics(m);
    }

    public static void main(String... args) throws IOException {
        CompareEntryPoints obj = new CompareEntryPoints("");
        obj.process(args);
    }
}
