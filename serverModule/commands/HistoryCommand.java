package serverModule.commands;

import common.exceptions.WrongAmountOfArgumentsException;
import serverModule.util.ResponseOutputer;

/**
 * Command 'history'. Checks for wrong arguments then do nothing.
 */
public class HistoryCommand extends Command{

    public HistoryCommand() {
        super("history", "вывести историю использованных команд");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }
}
