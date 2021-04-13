package serverModule.commands;

import common.exceptions.WrongAmountOfArgumentsException;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

public class PrintUniqueGroupAdminCommand extends Command{
    private CollectionManager collectionManager;

    public PrintUniqueGroupAdminCommand(CollectionManager collectionManager) {
        super("print_unique_group_admin", " вывести уникальные значения поля groupAdmin всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            ResponseOutputer.append("уникальные значения поля groupAdmin всех элементов в коллекции: " + collectionManager.uniqueGroupAdmin() + "\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }
}
