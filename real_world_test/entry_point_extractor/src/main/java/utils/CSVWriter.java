package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class CSVWriter {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void writeEntryPointMetrics(List<EntryPointMetrics> entryPointMetricsList) {
        File dir = new File(Constants.EP_METRICS_OUTPUT_DIR);
        File f = new File(Constants.EP_METRICS_OUTPUT_DIR + File.separator +
                Constants.EP_METRICS_FILE_NAME + ".txt");

        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            FileWriter outFile = new FileWriter(f);
            com.opencsv.CSVWriter csvWriter = new com.opencsv.CSVWriter(outFile);
            csvWriter.writeNext(Constants.REPORTS_CSV_HEADER);
            for (EntryPointMetrics m: entryPointMetricsList) {
                String[] row = {
                        m.getRealWorldLib(),
                        m.getFramework(),
                        String.valueOf(m.getTotalSourceFiles()),
                        String.valueOf(m.getSuccessfullyRun()),
                        String.valueOf(m.getCommonFiles())
                };
                csvWriter.writeNext(row);
            }
            csvWriter.close();
        } catch (IOException e) {
            LOG.warning("Exception in writing the file count to CSV: " + e.getMessage());
        }
    }
}
