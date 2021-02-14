package reports;

import com.opencsv.CSVWriter;
import metrics.Metrics;
import utils.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to write metrics into a csv file.
 */
public class CSVReporter {

    private String fileName, reportDir;

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public CSVReporter(String file, String reportDirectory){
        fileName = file;
        reportDir = reportDirectory.length() > 0 ? reportDirectory : Constants.DEFAULT_REPORT_DIR;
    }

    public void writeToFile(Map<String, List<Metrics>> metricsMap){
        File dir = new File(reportDir);
        File f = new File(reportDir + File.separator + fileName);

        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            FileWriter outFile = new FileWriter(f);
            CSVWriter csvWriter = new CSVWriter(outFile);
            csvWriter.writeNext(Constants.REPORTS_CSV_HEADER);
            for (Map.Entry<String, List<Metrics>> metricsEntry: metricsMap.entrySet()){
                String benchmarkCategory = metricsEntry.getKey();
                List<Metrics> metricsList = metricsEntry.getValue();
                for (Metrics m: metricsList){
                    String[] row = {benchmarkCategory,
                            m.getFileName(),
                            Double.toString(m.getPrecision()),
                            Double.toString(m.getRecall()),
                            Double.toString(m.getRunTime()),
                            Double.toString(m.getF1score()),
                            m.getErrorString(),
                            Integer.toString(m.getTotalDynamicEdges()),
                            Integer.toString(m.getTotalStaticEdges()),
                            Integer.toString(m.getTpEdges().size()),
                            Integer.toString(m.getFpEdges().size()),
                            Integer.toString(m.getFnEdges().size())
                    };
                    csvWriter.writeNext(row);
                }
            }
            csvWriter.close();
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }
}
