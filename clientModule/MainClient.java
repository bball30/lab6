package clientModule;

import clientModule.util.Console;
import common.exceptions.IncorrectInputInScriptException;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    private static String host = "localhost";
    private static int port = 8778;

    public static void main(String[] args) throws IOException, IncorrectInputInScriptException {
        Scanner scanner = new Scanner(System.in);
        Console console = new Console(scanner);
        Client client = new Client(host, port, console);
        client.run();
        scanner.close();
    }
}
