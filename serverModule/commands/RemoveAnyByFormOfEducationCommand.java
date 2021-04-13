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
public class RemoveAnyByFormOfEducationCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveAnyByFormOfEducationCommand(CollectionManager collectionManager) {
        super("remove_any_by_form_of_education formOfEducation", " удалить из коллекции один элемент, значение поля formOfEducation которого эквивалентно заданному");
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
            StudyGroup studyGroup = collectionManager.getByFormOfEducation(argument);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            collectionManager.removeFromCollection(studyGroup);
            ResponseOutputer.append("Группа успешно удалена!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (StudyGroupNotFoundException exception) {
            ResponseOutputer.append("Группы с такой формой обучения в коллекции нет!\n");
        }
        return false;
    }
}
