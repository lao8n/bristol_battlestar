package swarm_wars_library.network;

public class TerminalLogger {

    private boolean isLogging = true;

    private static TerminalLogger logger = new TerminalLogger();

    private TerminalLogger(){}

    public static TerminalLogger getInstance() {
        return logger;
    }

    public void log(String msg) {
        if (isLogging) System.out.println(msg);
    }
}
