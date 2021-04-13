package serverModule.commands;

import common.data.StudyGroup;
import common.exceptions.WrongAmountOfArgumentsException;
import common.utility.StudyGroupLite;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty()|| objectArgument == null) throw new WrongAmountOfArgumentsException();
            StudyGroupLite groupLite = (StudyGroupLite) objectArgument;
            collectionManager.addToCollection(new StudyGroup(
                            collectionManager.generateNextId(),
                            groupLite.getName(),
                            groupLite.getCoordinates(),
                            LocalDateTime.now(),
                            groupLite.getStudentsCount(),
                            groupLite.getShouldBeExpelled(),
                            groupLite.getAverageMark(),
                            groupLite.getFormOfEducation(),
                            groupLite.getGroupAdmin()
                    )
            );
            ResponseOutputer.append("Группа успешно добавлена в коллекцию!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}
