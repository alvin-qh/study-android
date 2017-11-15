package alvin.base.net.socket.common.models;

public class Command {
    private String cmd;
    private String[] arguments;

    public Command(String cmd) {
        this.cmd = cmd;
        this.arguments = null;
    }

    public Command(String cmd, String[] arguments) {
        this.cmd = cmd;
        this.arguments = arguments;
    }

    public String getCmd() {
        return cmd;
    }

    public String[] getArguments() {
        return arguments;
    }
}
