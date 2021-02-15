package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ResourceReader {
    /**
     * Retrieve the files in the directory recursively
     * @param directory name of the directory
     * @param files list object in which the files are stored
     */
    private static void getFilesRecursively(String directory, List<File> files){
        File dir = new File(directory);
        if (dir.isDirectory()){
            for (File f: dir.listFiles()){
                if (f.isFile() && f.getName().endsWith(Constants.PYTHON_SUFFIX)) {
//                    if (!f.getAbsolutePath().contains("tests" + File.separator))
                    files.add(f);
                } else if (f.isDirectory()){
                    getFilesRecursively(f.getAbsolutePath(), files);
                }
            }
        }
    }

    /**
     * Retrieve the files in the respective folder from resources
     * @param realWorldLibrary real world library from which source files needs to be fetched
     * @return list of files found
     */
    public static List<File> getFilesInResource(String realWorldLibrary){
        File folder = new File(Constants.RESOURCES_PATH + File.separator
                + Constants.REAL_WORLD_LIB_FOLDER_NAME + File.separator + realWorldLibrary);
        ArrayList<File> fileList = new ArrayList<>();
        getFilesRecursively(folder.getAbsolutePath(), fileList);
        return fileList;
    }

    public static Set<String> getEntryPointsAfterSplit(String realWorldLib, String cgFramework) throws MalformedURLException {
        Set<String> ep = new HashSet<>();
        BufferedReader reader = null;
        try {
            String filePath = Constants.RESOURCES_PATH + File.separator + Constants.PRUNED_ENTRY_POINTS_FOLDER_NAME +
                    File.separator + cgFramework + File.separator + realWorldLib + "PrunedEntryPoints" + ".txt";
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null){
                ep.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new MalformedURLException("Error reading entryPoints for library - " + realWorldLib + ", " +
                    "with exception: " + e.getMessage());
        }
        return ep;
    }

    public static Set<String> getEntryPoints(String realWorldLib) throws MalformedURLException {
        Set<String> ep = new HashSet<>();
        BufferedReader reader = null;
        File f = new File(Constants.DEFAULT_OUTPUT_DIR + File.separator + realWorldLib + ".txt");
        try {
            reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while (line != null){
                ep.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new MalformedURLException("Error reading entryPoints from file - " + realWorldLib);
        }
        return ep;
    }
}
