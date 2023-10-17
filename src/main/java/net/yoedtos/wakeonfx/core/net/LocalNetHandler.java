package net.yoedtos.wakeonfx.core.net;

import static net.yoedtos.wakeonfx.util.Constants.BROADCAST_ADD;
import static net.yoedtos.wakeonfx.util.Constants.UDP_PORT;

import net.yoedtos.wakeonfx.exceptions.NetworkException;

public class LocalNetHandler implements INetHandler {
    private NetHandler netHandler;

    public LocalNetHandler() {
        this.netHandler = new NetHandler();
    }

    @Override
    public void send(Packet packet) throws NetworkException {
        netHandler.send(new Packet(BROADCAST_ADD, UDP_PORT, packet.getData()));
    }
}
