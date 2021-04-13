package serverModule;

import serverModule.commands.*;
import serverModule.util.CollectionManager;
import serverModule.util.CommandManager;
import serverModule.util.FileManager;
import serverModule.util.RequestManager;

import java.io.IOException;

public class MainServer {
    public static final int PORT = 8778;

    public static void main(String[] args) throws IOException {
        final String envVariable = "LAB5";
        FileManager fileManager = new FileManager(envVariable);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new RemoveGreaterCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new HistoryCommand(),
                new RemoveAnyByFormOfEducationCommand(collectionManager),
                new PrintUniqueGroupAdminCommand(collectionManager),
                new PrintFieldDescendingShouldBeExpelledCommand(collectionManager),
                new LoadCollectionCommand(collectionManager));
        RequestManager requestManager = new RequestManager(commandManager);
        Server server = new Server(PORT, requestManager);
        server.run();
        collectionManager.saveCollection();
    }
}
