package net.yoedtos.wakeonfx.repository;

import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.model.Host;

import java.util.List;

public class HostRepository implements Repository<Host> {

    @Override
    public Object persist(Host host) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(int id) throws RepositoryException {

    }

    @Override
    public Host findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    public Object merge(Host host) throws RepositoryException {
        return null;
    }

    @Override
    public List<Host> findAll() throws RepositoryException {
        return null;
    }
}
