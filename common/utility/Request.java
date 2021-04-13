package common.utility;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String argument;
    private Serializable objectArgument;

    public Request(String commandName, String argument, Serializable objectArgument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = objectArgument;
    }

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
        this.objectArgument = null;
    }

    public Request() {
        this.commandName = "";
        this.argument = "";
        this.objectArgument = null;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Object getObjectArgument() {
        return objectArgument;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && argument.isEmpty() && objectArgument == null;
    }
}
