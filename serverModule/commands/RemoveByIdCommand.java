package serverModule.commands;

import common.data.StudyGroup;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.StudyGroupNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

/**
 * 'remove_by_id' command. Removes the element by its ID.
 */
public class RemoveByIdCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Integer id = Integer.parseInt(argument);
            StudyGroup studyGroup = collectionManager.getById(id);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            collectionManager.removeFromCollection(studyGroup);
            ResponseOutputer.append("Группа успешно удалена!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NumberFormatException exception) {
            ResponseOutputer.append("ID должен быть представлен числом!\n");
        } catch (StudyGroupNotFoundException exception) {
            ResponseOutputer.append("Группы с таким ID в коллекции нет!\n");
        }
        return false;
    }
}