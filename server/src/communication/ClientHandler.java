package communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ClientHandler {
    private ServerSocketChannel server;
    private Selector selector;
    public String response;

    public ClientHandler(int port) throws IOException {
        Logger log = Logger.getLogger(ClientHandler.class.getName());
        this.server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);
        this.selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("Сервер запущен, прослушивает порт "+port+". Ожидание подключений");
    }

    public Selector getSelector() {
        return selector;
    }

    public void acceptConnection() throws IOException {
        HandshakeHandler.acceptConnection(server, selector);
    }

    public TransferObject readRequest(SocketChannel channel) throws IOException, ClassNotFoundException {
        return RequestHandler.readRequest(channel);
    }

    public void sendResponse(SocketChannel channel) throws IOException {
        ResponseHandler.sendResponse(channel, response);
    }
}
