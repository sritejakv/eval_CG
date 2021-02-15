import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandLineOptions extends Options {

    public CommandLineOptions(){
        Option pyan = Option.builder().longOpt("pyan").hasArg(false).desc("Compare Pyan call graphs").build();
        addOption(pyan);
        Option wala = Option.builder().longOpt("wala").hasArg(false).desc("Compare Wala call graphs").build();
        addOption(wala);
        Option code2flow = Option.builder().longOpt("code2flow").hasArg(false).desc("Compare Code2flow call graphs").build();
        addOption(code2flow);
        Option reportsDir = Option.builder().longOpt("reportsDir").hasArg(false).
                desc("Directory to store the reports").build();
        addOption(reportsDir);
        Option verbose = Option.builder().longOpt("verbose").hasArg(false).desc("Enable log output").build();
        addOption(verbose);
    }
}
