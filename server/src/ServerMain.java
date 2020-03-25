import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import collection.CityCollection;
import collection.CollectionManager;
import commands.CommandInvoker;
import communication.ClientHandler;
import communication.TransferObject;
import utils.JsonReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.*;

public class ServerMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {//TODO модули
        final int PORT = 1984;

        Logger log = Logger.getLogger(ServerMain.class.getName());
        JsonReader jr = new JsonReader();
        CommandInvoker ci = new CommandInvoker();
        CityCollection collection;
        String response = "sample response";

        if (args.length > 0) {
            try {
                collection = new CityCollection(jr.read(args[0]));
                log.info("Инициализирована коллекция из файла "+Paths.get(args[0]).toAbsolutePath().toString());
            } catch (ValueInstantiationException e) {
                collection = new CityCollection();
                log.warning("Инициализирована пустая коллекция. Коллекия в файле "+
                        Paths.get(args[0]).toAbsolutePath().toString()+"не валидна");
            } catch (IOException e) {
                collection = new CityCollection();
                log.warning("Ошибка при попытке чтения из файла "+
                                Paths.get(args[0]).toAbsolutePath().toString()+". Инициализирована пустая коллекция");
            }
        } else {
            collection = new CityCollection();
            log.info("Файл не указан. Инициализирована пустая коллекция");
        }
        CollectionManager cm = new CollectionManager(collection);

        ClientHandler clientHandler = new ClientHandler(PORT);

        while (true) {
            clientHandler.getSelector().select();

            Iterator keys = clientHandler.getSelector().selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    clientHandler.acceptConnection();
                }
                else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    TransferObject TO = clientHandler.readRequest(channel);
                    clientHandler.response = ci.executeCommand(cm, TO) + "\n";
                    key.interestOps(SelectionKey.OP_WRITE);
                }
                else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    clientHandler.sendResponse(channel);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}
