package net.yoedtos.wakeonfx.core.net;

import net.yoedtos.wakeonfx.exceptions.NetworkException;

public interface INetHandler {
    void send(Packet packet) throws NetworkException;
}
