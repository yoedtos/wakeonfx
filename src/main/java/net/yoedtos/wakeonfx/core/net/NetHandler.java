package net.yoedtos.wakeonfx.core.net;

import net.yoedtos.wakeonfx.exceptions.NetworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class NetHandler implements INetHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetHandler.class);

    @Override
    public void send(Packet packet) throws NetworkException {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            var packetBytes = packet.getData();
            var socketAddress = new InetSocketAddress(packet.getTarget(), packet.getPort());
            var data = new DatagramPacket(packetBytes, packetBytes.length, socketAddress);
            socket.send(data);
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new NetworkException(e.getMessage());
        } finally {
            assert socket != null;
            socket.close();
        }
    }
}
