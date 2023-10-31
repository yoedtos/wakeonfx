package net.yoedtos.wakeonfx.service;

import net.yoedtos.wakeonfx.control.Index;
import net.yoedtos.wakeonfx.control.Status;
import net.yoedtos.wakeonfx.core.net.NetMonitor;
import net.yoedtos.wakeonfx.exceptions.NetworkException;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.HostRepository;
import net.yoedtos.wakeonfx.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);

    private Repository<Host> repository;

    public MonitorService() {
        this.repository = new HostRepository();
    }
    public Status monitor(Index index) throws ServiceException {
        try {
            var host = repository.findById(index.getId());
            return monitor(host);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public Status monitor(Host host) throws ServiceException {
        NetMonitor netMonitor = new NetMonitor(host);
        try {
            if(!netMonitor.isHostOnline()) {
                return Status.OFF_LINE;
            }
            if (!netMonitor.isHostServiceOnLine()) {
                return Status.IS_ERROR;
            }
        } catch (NetworkException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return Status.ON_LINE;
    }
}
