package net.yoedtos.wakeonfx.service;

import net.yoedtos.wakeonfx.core.WakeOn;
import net.yoedtos.wakeonfx.core.net.INetHandler;
import net.yoedtos.wakeonfx.core.net.LocalNetHandler;
import net.yoedtos.wakeonfx.core.net.NetHandler;
import net.yoedtos.wakeonfx.core.net.Packet;
import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.NetworkException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WakeOnService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WakeOnService.class);

    public void wake(Host host) throws ServiceException {
        try {
            INetHandler netHandler = createNetHandler(host.getAddress());
            var data = WakeOn.newInstance(host).create();
            netHandler.send(new Packet(host.getAddress().getIp(), host.getPort(), data));
        } catch (CoreException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException("Object creation failed");
        } catch (NetworkException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException("There is a network issue");
        }
    }

    public INetHandler createNetHandler(Address address) throws CoreException {
        if (!address.getIp().contains("192.168.")) {
            return new NetHandler();
        }
        return new LocalNetHandler();
    }
}

