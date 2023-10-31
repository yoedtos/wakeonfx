package net.yoedtos.wakeonfx.service;

import static net.yoedtos.wakeonfx.control.Status.*;
import static net.yoedtos.wakeonfx.util.TestConstants.ID_ONE;
import static net.yoedtos.wakeonfx.util.TestDataSet.createIndexOne;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import net.yoedtos.wakeonfx.core.net.NetMonitor;
import net.yoedtos.wakeonfx.exceptions.RepositoryException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.repository.HostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MonitorServiceTest {

    @Mock
    private HostRepository mockHostRepo;
    @Spy
    @InjectMocks
    private MonitorService monitorService;

    private Host host;

    @BeforeEach
    void setup() {
        host = createSimpleHost();
    }

    @Test
    void whenHostIsOK_ShouldReturnOnline() throws ServiceException {
        try (MockedConstruction<NetMonitor> mocked = mockConstruction(NetMonitor.class,
                (mock, context) -> {
                    when(mock.isHostOnline()).thenReturn(true);
                    when(mock.isHostServiceOnLine()).thenReturn(true);
                })) {

            MonitorService monitorService = new MonitorService();
            var status = monitorService.monitor(host);
            assertThat(status).isEqualTo(ON_LINE);
            assertThat(mocked.constructed()).hasSize(1);
        }
    }

    @Test
    void whenHostServiceIsNG_ShouldReturnIsError() throws ServiceException {
        try (MockedConstruction<NetMonitor> mocked = mockConstruction(NetMonitor.class,
                (mock, context) -> {
                    when(mock.isHostOnline()).thenReturn(true);
                    when(mock.isHostServiceOnLine()).thenReturn(false);
                })) {

            MonitorService monitorService = new MonitorService();
            var status = monitorService.monitor(host);
            assertThat(status).isEqualTo(IS_ERROR);
            assertThat(mocked.constructed()).hasSize(1);
        }
    }

    @Test
    void whenHostIsNG_ShouldReturnOffline() throws ServiceException {
        try (MockedConstruction<NetMonitor> mocked = mockConstruction(NetMonitor.class,
                (mock, context) -> {
                    when(mock.isHostOnline()).thenReturn(false);
                    when(mock.isHostServiceOnLine()).thenReturn(false);
                })) {

            MonitorService monitorService = new MonitorService();
            var status = monitorService.monitor(host);
            assertThat(status).isEqualTo(OFF_LINE);
            assertThat(mocked.constructed()).hasSize(1);
        }
    }

    @Test
    void whenMonitorWithIndexShouldCallRightHost() throws RepositoryException, ServiceException {
        when(mockHostRepo.findById(ID_ONE)).thenReturn(host);
        monitorService.monitor(createIndexOne());
        verify(monitorService, times(1)).monitor(host);
    }
}

