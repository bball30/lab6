package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;
import serverModule.util.CollectionManager;
import serverModule.util.ResponseOutputer;

public class LoadCollectionCommand extends Command{
    private CollectionManager collectionManager;

    public LoadCollectionCommand(CollectionManager collectionManager) {
        super("loadCollection", "загружает коллекцию (эта команда недоступна пользователю)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.loadCollection();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Должно быть передано название файла!\n");
        }
        return false;
    }
}
