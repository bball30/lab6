package serverModule.commands;

import common.data.Coordinates;
import common.data.FormOfEducation;
import common.data.Person;
import common.data.StudyGroup;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.StudyGroupNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.StudyGroupLite;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;


import java.time.LocalDateTime;

/**
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            int id = Integer.parseInt(argument);
            StudyGroup studyGroup = collectionManager.getById(id);
            StudyGroupLite groupLite = (StudyGroupLite) objectArgument;

            if (studyGroup == null) throw new StudyGroupNotFoundException();

            String name = groupLite.getName() == null ? studyGroup.getName() : groupLite.getName();
            Coordinates coordinates = groupLite.getCoordinates() == null ? studyGroup.getCoordinates() : groupLite.getCoordinates();
            LocalDateTime creationDate = studyGroup.getCreationDate();
            long studentsCount = groupLite.getStudentsCount() == -1 ? studyGroup.getStudentsCount() : groupLite.getStudentsCount();
            long shouldBeExpelled = groupLite.getShouldBeExpelled() == -1 ? studyGroup.getShouldBeExpelled() : groupLite.getShouldBeExpelled();
            int averageMark = groupLite.getAverageMark() == -1 ? studyGroup.getAverageMark() : groupLite.getAverageMark();
            FormOfEducation formOfEducation = groupLite.getFormOfEducation() == null ? studyGroup.getFormOfEducation() : groupLite.getFormOfEducation();
            Person groupAdmin = groupLite.getGroupAdmin() == null ? studyGroup.getGroupAdmin() : groupLite.getGroupAdmin();

            collectionManager.removeFromCollection(studyGroup);

            collectionManager.addToCollection(new StudyGroup(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    studentsCount,
                    shouldBeExpelled,
                    averageMark,
                    formOfEducation,
                    groupAdmin
            ));
            ResponseOutputer.append("Группа успешно изменена!\n");
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
