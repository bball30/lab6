package clientModule.util;

import common.data.Coordinates;
import common.data.FormOfEducation;
import common.data.Person;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.NoAccessToFileException;
import common.exceptions.ScriptRecursionException;
import common.exceptions.WrongAmountOfParametersException;
import common.utility.Request;
import common.utility.ResponseCode;
import common.utility.StudyGroupLite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Operates command input.
 */
public class Console {
    private Scanner userScanner;
    private Stack<File> scriptFileNames = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public Console(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }

    /**
     * Mode for catching serverModule.commands from user input.
     */
    public Request interactiveMode(ResponseCode serverResponseCode) throws IncorrectInputInScriptException {
        String userInput;
        String[] userCommand = {"", ""};
        ProcessCode processCode;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.SERVER_EXIT || serverResponseCode == ResponseCode.ERROR)) {
                        throw new IncorrectInputInScriptException();
                    }
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        System.out.println("Возвращаюсь из скрипта '" + scriptFileNames.pop().getName() + "'!");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            System.out.print("$ ");
                            System.out.println(userInput);
                        }
                    } else {
                        System.out.print("$ ");
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    System.out.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                }
                processCode = checkCommand(userCommand[0], userCommand[1]);
            } while (processCode == ProcessCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                switch (processCode) {
                    case OBJECT:
                        StudyGroupLite groupToInsert = generateGroupToInsert();
                        return new Request(userCommand[0], userCommand[1], groupToInsert);
                    case UPDATE_OBJECT:
                        StudyGroupLite groupToUpdate = generateGroupToUpdate();
                        return new Request(userCommand[0], userCommand[1], groupToUpdate);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptFileNames.isEmpty() && scriptFileNames.search(scriptFile) != -1) {
                            throw new ScriptRecursionException();
                        }
                        scannerStack.push(userScanner);
                        scriptFileNames.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        System.out.println("Выполняю скрипт '" + scriptFile.getName() + "'!");
                        break;
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                System.out.println("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            System.out.println("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Launches the command.
     * @param command Command to launch.
     * @return Exit code.
     */
    private ProcessCode checkCommand(String command, String argument) {
        try {
            switch (command) {
                case "":
                    return ProcessCode.ERROR;
                case "help":
                case "info":
                case "show":
                case "clear":
                case "history":
                case "print_unique_group_admin":
                case "print_field_descending_should_be_expelled":
                case "exit":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "add":
                case "remove_greater":
                case "remove_lower":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OBJECT;
                case "remove_any_by_form_of_education":
                case "remove_by_id":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "update":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.UPDATE_OBJECT;
                case "execute_script":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.SCRIPT;
                case "save":
                    System.out.println("Эта команда недоступна клиентам!");
                    return ProcessCode.ERROR;
                default:
                    System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessCode.ERROR;
            }
        } catch (WrongAmountOfParametersException e) {
            System.out.println("Проверьте правильность ввода аргументов!");
        }
        return ProcessCode.OK;
    }

    private StudyGroupLite generateGroupToInsert() throws IncorrectInputInScriptException {
        Interactor builder = new Interactor(userScanner);
        if (fileMode()) {
            builder.setFileMode();
        } else {
            builder.setUserMode();
        }
        return new StudyGroupLite(
                builder.askGroupName(),
                builder.askCoordinates(),
                builder.askStudentsCount(),
                builder.askShouldBeExpelled(),
                builder.askAverageMark(),
                builder.askFormOfEducation(),
                builder.askGroupAdmin()
        );
    }

    private StudyGroupLite generateGroupToUpdate() throws IncorrectInputInScriptException{
        Interactor builder = new Interactor(userScanner);
        if (fileMode()) {
            builder.setFileMode();
        } else {
            builder.setUserMode();
        }
        String name = builder.askQuestion("Хотите изменить имя группы?") ?
                builder.askGroupName() : null;
        Coordinates coordinates = builder.askQuestion("Хотите изменить координаты группы?") ?
                builder.askCoordinates() : null;
        long studentsCount = builder.askQuestion("Хотите изменить количество студентов?") ?
                builder.askStudentsCount() : -1;
        long shouldBeExpelled  = builder.askQuestion("Хотите изменить количество студентов, которые должны быть отчислены?") ?
                builder.askShouldBeExpelled() : -1;
        int averageMark  = builder.askQuestion("Хотите изменить средний балл студентов?") ?
                builder.askAverageMark() : -1;
        FormOfEducation formOfEducation = builder.askQuestion("Хотите изменить форму обучения?") ?
                builder.askFormOfEducation() : null;
        Person groupAdmin  = builder.askQuestion("Хотите изменить админа группы?") ?
                builder.askGroupAdmin() : null;
        return new StudyGroupLite(
                name,
                coordinates,
                studentsCount,
                shouldBeExpelled,
                averageMark,
                formOfEducation,
                groupAdmin
        );
    }

    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error: toOut.toString() to Console
     * @param toOut Error to print
     */
    public static void printerror(Object toOut) {
        System.out.println("error: " + toOut);
    }

    /**
     * Prints formatted 2-element table to Console
     * @param element1 Left element of the row.
     * @param element2 Right element of the row.
     */
    public static void printtable(Object element1, Object element2) {
        System.out.printf("%-60s%-1s%n", element1, element2);
    }
}