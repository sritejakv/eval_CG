package reports;

import com.opencsv.CSVWriter;
import metrics.FrameworkMetrics;
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
                List<Metrics> metricsList = metricsEntry.getValue();
                for (Metrics m: metricsList){
                    Double totalStaticEdges = m.getTotalStaticEdges() == null ? 0.0 : m.getTotalStaticEdges().size();
                    Double totalDynamicEdges = m.getTotalDynamicEdges() == null ? 0.0 : m.getTotalDynamicEdges().size();
                    Double totalTPs = m.getTpEdges() == null ? 0.0 : m.getTpEdges().size();
                    String[] row = {
                            m.getRealWorldLib(),
                            Double.toString(m.getPrecision()),
                            Double.toString(m.getRecall()),
                            Double.toString(m.getRunTime()),
                            Double.toString(totalStaticEdges),
                            Double.toString(totalDynamicEdges),
                            Double.toString(totalTPs),
                            Double.toString(m.getFpEdges()),
                            Double.toString(m.getFnEdges()),
                            m.getErrorString()
                    };
                    csvWriter.writeNext(row);
                }
            }
            csvWriter.close();
        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }

    public void writeFrameworkMetricsToFile(Map<String, Map<String, FrameworkMetrics>> frameworkMetrics) {
        File dir = new File(reportDir);
        File f = new File(reportDir + File.separator + fileName);

        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            FileWriter outFile = new FileWriter(f);
            CSVWriter csvWriter = new CSVWriter(outFile);
            csvWriter.writeNext(Constants.FRAMEWORK_COMPARISON_REPORT_CSV_HEADER);
            for (Map.Entry<String, Map<String, FrameworkMetrics>> libMetricsMap: frameworkMetrics.entrySet()){
                String libraryName = libMetricsMap.getKey();

                Map<String, FrameworkMetrics> frameworkMetricsMap = libMetricsMap.getValue();
                for (Map.Entry<String, FrameworkMetrics> frameworks: frameworkMetricsMap.entrySet()) {
                    String cgAlgName = frameworks.getKey();
                    FrameworkMetrics m = frameworks.getValue();
                    Double totalStaticEdges = m.getStaticEdges() == null ? 0.0 : m.getStaticEdges().size();
                    Double totalDynamicEdges = m.getDynEdges() == null ? 0.0 : m.getDynEdges().size();
                    Double totalPresentEdges = m.getPresentDynEdges() == null ? 0.0 : m.getPresentDynEdges().size();
                    Double totalCommonEdges = m.getCommonEdges() == null ? 0.0 : m.getCommonEdges().size();
                    String[] row = {
                            libraryName,
                            cgAlgName,
                            Double.toString(totalDynamicEdges),
                            Double.toString(totalStaticEdges),
                            Double.toString(totalCommonEdges),
                            Double.toString(totalPresentEdges),
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
