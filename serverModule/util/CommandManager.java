package serverModule.util;

import common.exceptions.HistoryIsEmptyException;
import serverModule.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 14;
    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];

    private final List<Command> commands = new ArrayList<>();
    private final Command helpCommand;
    private final Command infoCommand;
    private final Command showCommand;
    private final Command addCommand;
    private final Command updateCommand;
    private final Command removeByIdCommand;
    private final Command clearCommand;
    private final Command saveCommand;
    private final Command executeScriptCommand;
    private final Command exitCommand;
    private final Command removeGreaterCommand;
    private final Command removeLowerCommand;
    private final Command historyCommand;
    private final Command removeAnyByFormOfEducationCommand;
    private final Command printUniqueGroupAdminCommand;
    private final Command printFieldDescendingShouldBeExpelledCommand;
    private final Command loadCollection;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand, Command saveCommand, Command executeScriptCommand, Command exitCommand, Command removeGreaterCommand, Command removeLowerCommand, Command historyCommand, Command removeAnyByFormOfEducationCommand, Command printUniqueGroupAdminCommand, Command printFieldDescendingShouldBeExpelledCommand, Command loadCollection) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.historyCommand = historyCommand;
        this.removeAnyByFormOfEducationCommand = removeAnyByFormOfEducationCommand;
        this.printUniqueGroupAdminCommand = printUniqueGroupAdminCommand;
        this.printFieldDescendingShouldBeExpelledCommand = printFieldDescendingShouldBeExpelledCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.loadCollection = loadCollection;


        // serverModule.commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(historyCommand);
        commands.add(removeGreaterCommand);
        commands.add(removeLowerCommand);
        commands.add(removeAnyByFormOfEducationCommand);
        commands.add(printUniqueGroupAdminCommand);
        commands.add(printFieldDescendingShouldBeExpelledCommand);
        commands.add(loadCollection);
    }

    /**
     * @return List of manager's com.serverModule.commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    public boolean loadCollection(String argument, Object objectArgument) {
        return loadCollection.execute(argument, objectArgument);
    }

    /**
     * Prints info about the all com.serverModule.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument, Object objectArgument) {
        if (helpCommand.execute(argument, objectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendTable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument, Object objectArgument) {
        return infoCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean show(String argument, Object objectArgument) {
        return showCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean add(String argument, Object objectArgument) {
        return addCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean update(String argument, Object objectArgument) {
        return updateCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String argument, Object objectArgument) {
        return removeByIdCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument, Object objectArgument) {
        return clearCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean save(String argument, Object objectArgument) {
        return saveCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument, Object objectArgument) {
        return exitCommand.execute(argument,objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String argument, Object objectArgument) {
        return executeScriptCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeGreater(String argument, Object objectArgument) {
        return removeGreaterCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String argument, Object objectArgument) {
        return removeLowerCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeAny(String argument, Object objectArgument){ return removeAnyByFormOfEducationCommand.execute(argument, objectArgument);}

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printUniqueGroupAdmin(String argument, Object objectArgument){ return printUniqueGroupAdminCommand.execute(argument, objectArgument);}

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printFieldDescendingShouldBeExpelled(String argument, Object objectArgument){ return printFieldDescendingShouldBeExpelledCommand.execute(argument, objectArgument);}


    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints the history of used serverModule.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean history(String argument, Object objectArgument) {
        if (historyCommand.execute(argument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.append("Последние использованные команды:\n");
                for (int i=0; i<commandHistory.length; i++) {
                    if (commandHistory[i] != null) ResponseOutputer.append(" " + commandHistory[i] + "\n");
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.append("Ни одной команды еще не было использовано!\n");
            }
        }
        return false;
    }
}
