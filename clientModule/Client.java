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
import java.util.Set;

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

    public void run() throws IOException, IncorrectInputInScriptException {
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
            System.exit(0);
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

    private Response deserialize() throws IOException, ClassNotFoundException {
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
