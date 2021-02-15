import utils.DotUtils;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MergeDotGraphs {

    static {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.FINE);
        }
    }

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String... args) throws IOException {
        for (String realWorldLib : args) {
            LOG.fine("Merging the dot graphs of the library: " + realWorldLib);
            DotUtils.mergeDotGraphs(realWorldLib);
        }
    }
}
