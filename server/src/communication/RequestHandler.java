package communication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class RequestHandler {
    public static TransferObject readRequest(SocketChannel channel) throws IOException, ClassNotFoundException {
        Logger log = Logger.getLogger(RequestHandler.class.getName());
        ByteBuffer bb = ByteBuffer.allocate(5 * 1024);
        channel.read(bb);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bb.array()))) {
            TransferObject TO = (TransferObject) ois.readObject();
            log.info("Получены данные от клиента "+channel.getRemoteAddress());
            return TO;
        }
    }
}
