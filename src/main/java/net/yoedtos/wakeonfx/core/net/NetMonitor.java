package net.yoedtos.wakeonfx.core.net;

import static net.yoedtos.wakeonfx.util.Constants.TIMEOUT;

import net.yoedtos.wakeonfx.core.CommandRunner;
import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.NetworkException;
import net.yoedtos.wakeonfx.model.Host;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetMonitor.class);

    private Host host;
    private CommandRunner commandRunner;

    public NetMonitor(Host host) {
        this.host = host;
        commandRunner = new CommandRunner();
    }

    public boolean isHostOnline() throws NetworkException {
        try {
            if (!isReachable(host.getAddress().getIp())) {
                LOGGER.error("Error: Is not reachable");
                throw new NetworkException("Can't reach host");
            }
        } catch (CoreException e) {
            LOGGER.error("Error: {} ", e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        return true;
    }

    public boolean isHostServiceOnLine() throws NetworkException {
        boolean result = false;
        try (Socket socket = new Socket(host.getAddress().getIp(), host.getPort())) {
            if (socket.isConnected()) {
                result = true;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to connect: {}", e.getMessage());
            throw new NetworkException(e.getMessage());
        }
        return result;
    }

    private boolean isReachable(String ip) throws CoreException {
        List<String> command = new ArrayList<>();
        command.add("ping");
        if (SystemUtils.IS_OS_LINUX) {
            command.add("-c1");
            command.add("-W");
            command.add(String.valueOf(TIMEOUT));
        } else if (SystemUtils.IS_OS_UNIX || SystemUtils.IS_OS_MAC_OSX) {
            command.add("-c1");
            command.add("-t");
            command.add(String.valueOf(TIMEOUT));
        } else if (SystemUtils.IS_OS_WINDOWS) {
            command.add("-n1");
            command.add("-w");
            String timeout = String.valueOf(TIMEOUT * 1000);
            command.add(timeout); //in milliseconds
        }
        command.add(ip);
        return commandRunner.execute(command) == 0;
    }
}
