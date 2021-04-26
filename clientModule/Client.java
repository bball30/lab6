package clientModule;

import clientModule.util.Console;
import common.exceptions.CommandNotFoundException;
import common.exceptions.IncorrectInputInScriptException;
import common.utility.Request;
import common.utility.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private final static String path = System.getenv().get("LAB5");
    private String host;
    private int port;
    private Console console;
    private SocketChannel socketChannel;
    private  SocketAddress address;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);

    public Client(String host, int port, Console console) {
        this.host = host;
        this.port = port;
        this.console = console;
    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }

    public void run(){
        try {
            socketChannel = SocketChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            socketChannel.connect(address);
            makeByteBufferToRequest(new Request("loadCollection", path));
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            Response responseLoading = deserialize();
            System.out.println(responseLoading.getResponseBody());
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode()) :
                        console.interactiveMode(null);
                if (requestToServer.isEmpty()) continue;
                makeByteBufferToRequest(requestToServer);
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                socketChannel.read(byteBuffer);
                serverResponse = deserialize();
                System.out.print(serverResponse.getResponseBody());
            } while(!requestToServer.getCommandName().equals("exit"));

        } catch (IOException | ClassNotFoundException | IncorrectInputInScriptException exception) {
            System.out.println("Произошла ошибка при работе с сервером!");
            System.exit(0);
        }
    }
}
    /**private final static String path = System.getenv().get("LAB5");
    private String host;
    private int port;
    private Console console;
    private Socket socket;
    //private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private String address = "127.0.0.1";
    private boolean connectionFlag = false;


    public Client(String host, int port, Console console) throws IOException, IncorrectInputInScriptException {
        this.host = host;
        this.port = port;
        this.console = console;
    }

    /**
     * Метод, отвечающий за подключение к серверу.
     * Устанавливает флаг connectionFlag.
     *
     * @throws IOException
     */
    /**private void connect() throws IOException {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(String.valueOf(address), port));
            if (!connectionFlag)
                System.out.println("Подключение: " + socket.getRemoteSocketAddress() + " прошло успешно.");
            connectionFlag = true;
            System.out.println("Введите команду: ");
        } catch (java.net.ConnectException e) {
            System.out.println("Сервер временно недоступен.");
            System.out.println("Прекращаем работу программы.");
            System.exit(0);
        }
    }

    /**
     * Метод для чтения ответа от сервера.
     *
     * @param socket - получает на вход socket, который подключен к серверу.
     */
    /** private void read(Socket socket) {
        try {
            byte[] buffer = new byte[100 * 100];
            InputStream is = socket.getInputStream();
            int numRead = is.read(buffer);
            if (numRead != 0) {
                StringBuilder s = new StringBuilder();
                Scanner cin = new Scanner(new String(buffer));
                while (cin.hasNextLine()) {
                    s.append(cin.nextLine());
                    if (s.charAt(0) == ' ' || s.charAt(0) == '[')
                        s.deleteCharAt(0);
                    System.out.println(s.toString().replace("]", "").replace(",", "").trim());
                    s.delete(0, s.length());
                }
                if (socket.isConnected()) {
                    System.out.println("Введите команду: ");
                } else {
                    System.out.println("Произошёл сбой соединения.");
                    System.out.println("Завершение программы.");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Соединение с сервером разорвано.");
            System.out.println("Завершение программы.");
            System.exit(0);
        }
    }**/


    /**
     * Метод, реализуюший запуск клиентского приложения.
     * Отвечает за выбор команды по вводу пользователя, отправку и чтение ответа.
     *
     * @throws IOException
     */
    /**void run() throws IOException, IncorrectInputInScriptException {
        try{connect();
            write(socket, new Request("loadCollection", path));
            Response responseLoading = read(socket);
            System.out.println(responseLoading.getResponseBody());
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                if (serverResponse != null) {
                    requestToServer = console.interactiveMode(serverResponse.getResponseCode());
                }
                else {
                    requestToServer = console.interactiveMode(null);
                }
                if (requestToServer.isEmpty()) continue;
                write(socket, requestToServer);
                serverResponse = read(socket);
                if (serverResponse == null) {
                    System.out.println("ERROR");
                }
                else {
                System.out.println(serverResponse.getResponseBody());
                }
            } while (!requestToServer.getCommandName().equals("exit"));
        } catch(ArrayIndexOutOfBoundsException | IncorrectInputInScriptException | ClassNotFoundException e) {
            System.out.println("Команда введена некорректно и не может быть отправлена, необходимо наличие аргумента.");
            run();
        }
    }

    /**
     * Метод для отправки команды на сервер.
     * @param socket - socket, по которому установлено соединеие с сервером.
     * @param request - команда, которая будет отправлена на сервер.
     */
/**private void write(Socket socket, Request request) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(baos.toByteArray(), 0, baos.toByteArray().length);
            bos.flush();
        } catch (IOException e) {
            System.out.println("Сервер временно недоступен.");
            System.out.println("Завершаем программу.");
            System.exit(0);
        }
    }

    private Response read(Socket socket) throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[100 * 100];
        InputStream is = socket.getInputStream();
        is.read(buffer);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }
}

/**
    public void run() throws IOException, IncorrectInputInScriptException, ClassNotFoundException {
        try {
            System.out.println("Клиент запущен!");
            socketChannel = SocketChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            socketChannel.connect(address);



            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode()) :
                        console.interactiveMode(null);
                if (requestToServer.isEmpty()) continue;
                makeByteBufferToRequest(requestToServer);
                socketChannel.write(byteBuffer);
                socketChannel.read(byteBuffer);
                serverResponse = deserialize();
                byteBuffer.flip();
                System.out.println(serverResponse.getResponseBody());
            } while (!requestToServer.getCommandName().equals("exit"));
        } catch (IOException | IncorrectInputInScriptException  | ClassNotFoundException ex ) {
            System.out.println("К сожалению, сервер не найден!");
            System.out.println("Давайте подождем 15 секунд пока сервер оживет!");
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }

    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException, ClassCastException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }
}
**/

/**
public class Client {
    private final static String path = System.getenv().get("LAB5");
    private String host;
    private int port;
    private Console console;
    private Socket socket;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private SocketChannel socketChannel;
    private SocketAddress address;
    private Selector selector;

    public Client(String host, int port, Console console){
        this.host = host;
        this.port = port;
        this.console = console;
    }

    public void run() throws IOException, IncorrectInputInScriptException, NullPointerException {
        try{
            System.out.println("Клиент запущен!");
            socketChannel = SocketChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            socketChannel.connect(address);
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            send(new Request("loadCollection", path));
            Response responseLoading = receive();
            System.out.println(responseLoading.getResponseBody());
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode()) :
                        console.interactiveMode(null);
                if (requestToServer.isEmpty()) continue;
                send(requestToServer);
                serverResponse = receive();
                System.out.println(serverResponse.getResponseBody());
            } while (!requestToServer.getCommandName().equals("exit"));
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("К сожалению, сервер не найден!");
            System.out.println("Давайте подождем 15 секунд пока сервер оживет!");
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException, ClassCastException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Response response = (Response) objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            byteBuffer.clear();
            return response;
    }

    private void send(Request request) throws IOException {
        makeByteBufferToRequest(request);
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (SocketChannel) key.channel();
                    channel.write(byteBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private Response receive() throws IOException, ClassNotFoundException {
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (SocketChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }
}
**/
