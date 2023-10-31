package net.yoedtos.wakeonfx.service;

import net.yoedtos.wakeonfx.control.Index;
import net.yoedtos.wakeonfx.core.WakeOn;
import net.yoedtos.wakeonfx.core.net.INetHandler;
import net.yoedtos.wakeonfx.core.net.LocalNetHandler;
import net.yoedtos.wakeonfx.core.net.NetHandler;
import net.yoedtos.wakeonfx.core.net.Packet;
import net.yoedtos.wakeonfx.exceptions.*;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.HostRepository;
import net.yoedtos.wakeonfx.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WakeOnService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WakeOnService.class);

    private Repository<Host> repository;

    public WakeOnService() {
        this.repository = new HostRepository();
    }

    public void wake(Index index) throws ServiceException {
        try {
            var host = repository.findById(index.getId());
            wake(host);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

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

