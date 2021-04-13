package serverModule.commands;

import java.time.LocalDateTime;

import common.exceptions.WrongAmountOfArgumentsException;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

/**
 * 'info' command. Prints information about the collection.
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
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
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toString();

            ResponseOutputer.append("Сведения о коллекции:\n");
            ResponseOutputer.append(" Тип: " + collectionManager.collectionType() + "\n");
            ResponseOutputer.append(" Количество элементов: " + collectionManager.collectionSize() + "\n");
            ResponseOutputer.append(" Дата последнего сохранения: " + lastSaveTimeString + "\n");
            ResponseOutputer.append(" Дата последней инициализации: " + lastInitTimeString + "\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}