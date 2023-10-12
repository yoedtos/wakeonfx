package net.yoedtos.wakeonfx.service;

import net.yoedtos.wakeonfx.control.Index;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.HostRepository;
import net.yoedtos.wakeonfx.repository.Repository;
import net.yoedtos.wakeonfx.validator.HostValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostService.class);

    private Repository<Host> repository;
    private HostValidator hostValidator;

    public HostService() {
        this.repository = new HostRepository();
        this.hostValidator = new HostValidator();
    }

    public Index create(Host host) throws ServiceException {
        Object id;
        try {
            hostValidator.validate(host);
            id = repository.persist(host);
        } catch (RepositoryException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new Index((Integer) id, host.getName());
    }

    public Index get(int id) throws ServiceException {
        Host host;
        try {
            host = repository.findById(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new Index(id, host.getName());
    }

    public void remove(int id) throws ServiceException {
        try {
            repository.remove(id);
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public Index modify(Host host) throws ServiceException {
        Object id;
        try {
            hostValidator.validate(host);
            id = repository.merge(host);
        } catch (RepositoryException | ValidationException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new Index((Integer) id, host.getName());
    }

    public List<Index> getIndices() throws ServiceException {
        List<Index> indices;
        try {
            var hosts = repository.findAll();
            indices = hosts.stream().map(host -> {
                int id = hosts.indexOf(host) + 1;
                return new Index(id, host.getName());
            }).collect(Collectors.toList());
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return indices;
    }
}
