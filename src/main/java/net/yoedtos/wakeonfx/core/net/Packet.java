package net.yoedtos.wakeonfx.core.net;

import java.util.Arrays;
import java.util.Objects;

public class Packet {
    private String target;
    private int port;
    private byte[] data;

    public Packet(String target, int port, byte[] data) {
        this.target = target;
        this.port = port;
        this.data = data;
    }

    public String getTarget() {
        return target;
    }

    public int getPort() {
        return port;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "target='" + target + '\'' +
                ", port=" + port +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return port == packet.port && Objects.equals(target, packet.target) && Arrays.equals(data, packet.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(target, port);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
