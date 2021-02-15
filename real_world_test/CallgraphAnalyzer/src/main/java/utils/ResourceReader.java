package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ResourceReader {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Retrieve the corresponding file from the respective framework folder in the resources
     * @param framework framework folder to look into
     * @param fileName name of the file
     * @return full path of the found resource
     */
    public static String getResource(String framework, String fileName){
        Collection<File> files = FileUtils.listFiles(
                new File(Constants.RESOURCES_PATH + framework),
                new RegexFileFilter(fileName),
                DirectoryFileFilter.DIRECTORY
        );
        for (File file : files) {
            if(file.getAbsolutePath().contains(fileName)) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * Retrieve the files in the directory recursively
     * @param directory name of the directory
     * @param files list object in which the files are stored
     */
    private static void getFilesRecursively(String directory, String suffix, List<File> files){
        File dir = new File(directory);
        if (dir.isDirectory()){
            for (File f: dir.listFiles()){
                if (f.isFile() && f.getName().endsWith(suffix) &&
                !f.getAbsolutePath().contains("tests") && !f.getName().startsWith("Test")) { //TODO be carefull as the path includes real_world test in its path
                    files.add(f);
                } else if (f.isDirectory()){
                  getFilesRecursively(f.getAbsolutePath(), suffix, files);
                }
            }
        }
    }

    /**
     * Retrieve the files in the respective folder from resources
     * @param framework first level folder name
     * @param realWorldLib sub folder inside framework folder name
     * @return list of files found
     */
    public static List<File> getFilesInResource(String framework, String realWorldLib){
        File[] folderList = new File(Constants.RESOURCES_PATH + framework).listFiles();
        ArrayList<File> fileList = new ArrayList<>();
        for (File category: folderList){
            if (category.getName().contains(realWorldLib)){
                getFilesRecursively(category.getAbsolutePath(), Constants.PY_SUFFIX, fileList);
            }
        }
        return fileList;
    }

    public static void main(String...args) {
        System.out.println(ResourceReader.getFilesInResource("realWorldLibSrc", "httpie"));
    }
}
