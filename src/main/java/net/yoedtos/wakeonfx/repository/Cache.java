package net.yoedtos.wakeonfx.repository;

import static net.yoedtos.wakeonfx.util.Constants.HOSTS_FILE;

import net.yoedtos.wakeonfx.exceptions.FSException;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.fs.HostsFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);

    private HostsFileHandler fileHandler;
    public final List<Host> hosts;
    private static Cache cache;

    private Cache() throws FSException {
        try {
            fileHandler = new HostsFileHandler(Paths.get(HOSTS_FILE));
            this.hosts = new ArrayList<>(fileHandler.read());
        } catch (FSException e) {
            LOGGER.error(e.getMessage());
            throw new FSException(e.getMessage());
        }
    }

    public void update(List newHosts) throws FSException {
        try {
            fileHandler.write(newHosts);
        } catch (FSException e) {
            LOGGER.error(e.getMessage());
            throw new FSException(e.getMessage());
        }
    }

    public static synchronized Cache getInstance() {
        if (cache == null) {
            try {
                cache = new Cache();
                LOGGER.info("Created Cache id: {}", cache);
            } catch (FSException e) {
                LOGGER.error("Failed to create due to: {}", e.getMessage());
                return null;
            }
        }
        return cache;
    }
}
