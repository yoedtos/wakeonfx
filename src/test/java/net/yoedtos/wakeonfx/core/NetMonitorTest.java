package net.yoedtos.wakeonfx.core;

import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.createLinuxPing;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import net.yoedtos.wakeonfx.core.net.NetMonitor;
import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.NetworkException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class NetMonitorTest {

    private static ServerSocket serverSocket;
    private List<String> linuxPing;
    private Host host;

    @Mock
    private CommandRunner runnerMock;
    @InjectMocks
    private NetMonitor monitor;

    @BeforeAll
    static void init() throws IOException {
        serverSocket = new ServerSocket(PORT_NUM_ONE);
    }

    @AfterAll
    static void destroy() throws IOException {
        serverSocket.close();
    }

    @BeforeEach
    void setup() {
        host = createSimpleHost();
        monitor = new NetMonitor(host);
        linuxPing = createLinuxPing();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHostServiceIsOnLine() throws NetworkException {
        assertTrue(monitor.isHostServiceOnLine());
    }
    @Test
    void testHostServiceIsOffLine() throws NetworkException {
        monitor = new NetMonitor(new Host(host.getName(), 8000, TIME_OUT_ONE, host.getAddress()));
        assertFalse(monitor.isHostServiceOnLine());
    }

    @Test
    void testHostIsOnLine() throws CoreException, NetworkException {
        when(runnerMock.execute(anyList())).thenReturn(0);
        assertTrue(monitor.isHostOnline());
        verify(runnerMock, times(1)).execute(linuxPing);
    }

    @Test
    void testHostIsOffLine() throws CoreException {
        when(runnerMock.execute(anyList())).thenThrow(new CoreException(COMMAND_ERROR));

        assertThatExceptionOfType(NetworkException.class)
                .isThrownBy(() -> monitor.isHostOnline())
                .withMessage(COMMAND_ERROR);

        verify(runnerMock, times(1)).execute(linuxPing);
    }
}
