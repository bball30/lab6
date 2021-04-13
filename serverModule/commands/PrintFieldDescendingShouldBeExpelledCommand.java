package serverModule.commands;

import common.exceptions.WrongAmountOfArgumentsException;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

public class PrintFieldDescendingShouldBeExpelledCommand extends Command {
    private CollectionManager collectionManager;

    public PrintFieldDescendingShouldBeExpelledCommand(CollectionManager collectionManager) {
        super("print_field_descending_should_be_expelled", " вывести значения поля shouldBeExpelled всех элементов в порядке убывания");
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
            ResponseOutputer.append("значения поля shouldBeExpelled всех элементов в порядке убывания: " + collectionManager.sortedByShouldBeExpelled() + "\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }
}
