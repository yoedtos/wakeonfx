package net.yoedtos.wakeonfx.repository;

import net.yoedtos.wakeonfx.exceptions.FSException;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HostRepository implements Repository<Host> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostRepository.class);

    private Cache cache;
    private List<Host> hosts;

    public HostRepository() {
            cache = Cache.getInstance();
            hosts = cache.hosts;
    }

    @Override
    public Object persist(Host host) throws RepositoryException {
        int id;
        try {
            id = hosts.size();
            hosts.add(id, host);
            cache.write();
        } catch (FSException e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
        return id;
    }

    @Override
    public void remove(int id) throws RepositoryException {
        try {
            hosts.set(id, null);
            var newHosts = new ArrayList<>(hosts);
            newHosts.remove(id);
            cache.update(newHosts);
        } catch (FSException e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Host findById(int id) throws RepositoryException {
        try {
            return hosts.get(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Object merge(Host host) throws RepositoryException {
        int id = 0;
        try {
            for (int i = 0; i < hosts.size() ; i++) {
                if (host.getName().equals(hosts.get(i).getName())) {
                    id = i;
                    hosts.set(i ,host);
                    cache.write();
                    break;
                }
            }
        } catch (FSException e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
        return id;
    }

    @Override
    public List<Host> findAll() throws RepositoryException {
        try {
            return hosts;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(e.getMessage());
        }
    }
}
