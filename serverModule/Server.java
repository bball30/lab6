package serverModule;

import common.utility.Request;
import common.utility.Response;
import serverModule.util.RequestManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Scanner;

public class Server {
    private int port;
    private RequestManager requestManager;
    private SocketAddress address;
    private ServerSocketChannel serverSocketChannel;
    private Scanner scanner;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(2048);
    private int numRead = -1;
    private int previous;
    private Response response;


    public Server(int port, RequestManager requestManager) throws IOException {
        this.port = port;
        this.requestManager = requestManager;
        selector = Selector.open();
        address = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(address);
        selector = SelectorProvider.provider().openSelector();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        checkInput();
    }

    /**
     * Метод, запускающий новый поток для реализациии особых серверных комманд.
     */
    private void checkInput() {
        scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (!userCommand[0].equals("save") && !userCommand[0].equals("exit")) {

                        System.out.println("Сервер не может сам принимать такую команду!");
                        return;
                    }
                    if (userCommand[0].equals("exit")) {
                        System.out.println("Сервер заканчивает работу!");
                        System.exit(0);
                    }
                    Response response = executeRequest(new Request(userCommand[0], userCommand[1]));
                    System.out.println(response.getResponseBody());
                }
            } catch (Exception ignored){}
        };
        Thread thread = new Thread(userInput);
        thread.start();
    }


    private Response executeRequest(Request request) {
        return requestManager.manage(request);
    }

    public void run() throws IOException {
        System.out.println("Сервер запущен!");
        while (true) {
            //TODO Requests
            selector.select();
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();
                if (!key.isValid())
                    continue;
                if (key.isAcceptable()) {
                    accept(key);
                }
                if (key.isReadable())
                    read(key);
                if (key.isWritable())
                    write(key);
            }
        }
    }

    /**
     * Метод, реализующий получение нового подключения.
     * @param key
     * @throws IOException
     */
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Метод, реализующий чтение команды и её десериализацию.
     * @param key
     */

    private void read(SelectionKey key) {
        try {
            Request request;
            SocketChannel socketChannel = (SocketChannel) key.channel();
            numRead = socketChannel.read(readBuffer);
            previous = readBuffer.position();
            ByteArrayInputStream bais = new ByteArrayInputStream(readBuffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            request = (Request) ois.readObject();
            System.out.println("Получена команда: " + request.getCommandName());
            System.out.println();
            System.out.println("Выполнена команда: " + request.getCommandName());
            bais.close();
            ois.close();
            this.response = executeRequest(request);
            readBuffer.clear();
            SelectionKey selectionKey = socketChannel.keyFor(selector);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selector.wakeup();

        } catch (IOException | ClassNotFoundException ignored) {
        }
    }

    /**
     * Метод, реализующий отправку ответа клиенту.
     * @param key
     * @throws IOException
     */
    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        socketChannel.write(ByteBuffer.wrap(buffer));
        SelectionKey selectionKey = socketChannel.keyFor(selector);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }


/**
    private byte[] serialize(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Request deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }
    **/
}
