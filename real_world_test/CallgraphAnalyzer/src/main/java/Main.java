import adapters.*;
import metrics.CompareFrameworks;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import reports.CSVReporter;
import utils.Constants;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s - %5$s%6$s%n");
    }

    private List<FrameworkAdapter> frameworkAdapters;

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Main(){
        frameworkAdapters = new ArrayList<>();
    }

    public void addAdapter(String framework, String reportsDir){
        switch (framework){
            case Constants.PYAN_FRAMEWORK:
                frameworkAdapters.add(new Pyan(reportsDir));
                break;

            case Constants.CODE2FLOW_FRAMEWORK:
                frameworkAdapters.add(new Code2flow(reportsDir));
                break;

            case Constants.WALA_FRAMEWORK:
                frameworkAdapters.add(new WalaNCFA(reportsDir));
//                frameworkAdapters.add(new WalaVanillaZeroOneCFA(reportsDir));
//                frameworkAdapters.add(new WalaZeroCFA(reportsDir));
//                frameworkAdapters.add(new WalaZeroContainerCFA(reportsDir));
//                frameworkAdapters.add(new WalaZeroOneCFA(reportsDir));
//                frameworkAdapters.add(new WalaZeroOneContainerCFA(reportsDir));
                break;

            default:
                break;
        }
    }

    protected void setLogLevel(boolean debug){
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        if (debug){
            rootLogger.setLevel(Level.FINE);
            for (Handler h : rootLogger.getHandlers()) {
                h.setLevel(Level.FINE);
            }
        } else {
            rootLogger.setLevel(Level.INFO);
            for (Handler h : rootLogger.getHandlers()) {
                h.setLevel(Level.INFO);
            }
        }
    }

    private void compareFrameworks(List<FrameworkAdapter> adapters) throws FileNotFoundException {
        List<String> walaAlgs = new ArrayList<String>() {{
            add(Constants.WALA_VANILLA_ZERO_CFA_CALL_GRAPH);
            add(Constants.WALA_ZERO_CFA_CALL_GRAPH);
            add(Constants.WALA_ZERO_CONTAINER_CFA_CALL_GRAPH);
            add(Constants.WALA_ZERO_ONE_CFA_CALL_GRAPH);
            add(Constants.WALA_ZERO_ONE_CONTAINER_CFA_CALL_GRAPH);
        }};

        Iterator<FrameworkAdapter> it = adapters.iterator();
        while (it.hasNext()) {
            FrameworkAdapter a = it.next();
            if (walaAlgs.contains(a.getCgAlgName())) {
                it.remove();
            }
        }

        new CSVReporter(Constants.FRAMEWORK_COMPARISON_CSV_NAME, "").writeFrameworkMetricsToFile(
                new CompareFrameworks().compareFrameworks(adapters)
        );
    }

    public void execute() throws FileNotFoundException {
        for (FrameworkAdapter adapter: frameworkAdapters){
            LOG.info("Executing the framework adapter of: " + adapter.getFrameworkName() + "" +
                    ", CG algorithm: " + adapter.getCgAlgName());
            adapter.evaluate();
        }
        compareFrameworks(frameworkAdapters);
    }

    public static void main(String...args) throws ParseException, FileNotFoundException {
        CommandLineParser parser = new DefaultParser();
        CommandLine options = parser.parse(new CommandLineOptions(), args);
        Main m = new Main();
        if (options.hasOption("wala")){
            m.addAdapter(Constants.WALA_FRAMEWORK, options.getOptionValue("reportsDir", ""));
        }
        if (options.hasOption("pyan")){
            m.addAdapter(Constants.PYAN_FRAMEWORK, options.getOptionValue("reportsDir", ""));
        }
        if (options.hasOption("code2flow")) {
            m.addAdapter(Constants.CODE2FLOW_FRAMEWORK, options.getOptionValue("reportsDir", ""));
        }
        m.setLogLevel(options.hasOption("verbose"));
        m.execute();
    }
}
