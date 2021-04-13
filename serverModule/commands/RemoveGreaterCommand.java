package serverModule.commands;

import common.data.StudyGroup;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.StudyGroupNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.StudyGroupLite;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * 'remove_greater' command. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            StudyGroupLite groupLite = (StudyGroupLite) objectArgument;
            StudyGroup studyGroupToFind = new StudyGroup(
                    collectionManager.generateNextId(),
                    groupLite.getName(),
                    groupLite.getCoordinates(),
                    LocalDateTime.now(),
                    groupLite.getStudentsCount(),
                    groupLite.getShouldBeExpelled(),
                    groupLite.getAverageMark(),
                    groupLite.getFormOfEducation(),
                    groupLite.getGroupAdmin()
            );
            StudyGroup studyGroup = collectionManager.getByValue(studyGroupToFind);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeGreater(studyGroup);
            ResponseOutputer.append("Удалено групп: " + (collectionManager.collectionSize() - collectionSize));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (StudyGroupNotFoundException exception) {
            ResponseOutputer.append("Группы с такими параметрами в коллекции нет!\n");
        }
        return false;
    }
}
