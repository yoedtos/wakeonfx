package net.yoedtos.wakeonfx.service;

import static net.yoedtos.wakeonfx.util.Constants.BROADCAST_ADD;
import static net.yoedtos.wakeonfx.util.Constants.UDP_PORT;
import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.createLocalHost;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import net.yoedtos.wakeonfx.core.net.INetHandler;
import net.yoedtos.wakeonfx.core.net.LocalNetHandler;
import net.yoedtos.wakeonfx.core.net.NetHandler;
import net.yoedtos.wakeonfx.core.net.Packet;
import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class WakeOnServiceTest {

    private Host simpleHost;
    private Host localHost;

    @BeforeEach
    void setup() {
        localHost = createLocalHost();
        simpleHost = createSimpleHost();
    }

    @Test
    void testLocalNetHandlerCreation() throws CoreException {
        INetHandler netHandler = new WakeOnService().createNetHandler(localHost.getAddress());
        assertThat(netHandler).isInstanceOf(LocalNetHandler.class);
    }

    @Test
    void testNetHandlerCreation() throws CoreException {
        INetHandler netHandler = new WakeOnService().createNetHandler(simpleHost.getAddress());
        assertThat(netHandler).isInstanceOf(NetHandler.class);
    }

    @Test
    void whenWakeOnLocalNetShouldSendCorrectPacket() throws ServiceException {
        var localPacket = new Packet(BROADCAST_ADD, UDP_PORT, SIMPLE_PACKET);
        try (MockedConstruction<NetHandler> mocked = mockConstruction(NetHandler.class,
                (mock, context) -> doAnswer((i) -> {
                    assertThat((Packet)i.getArgument(0)).isEqualTo(localPacket);
                    return null;
                }).when(mock).send(any()))) {

            WakeOnService wakeOnService = new WakeOnService();
            wakeOnService.wake(localHost);
            assertThat(mocked.constructed()).hasSize(1);
        }
    }

    @Test
    void whenWakeOnNetShouldSendCorrectPacket() throws ServiceException {
        var simplePacket = new Packet(IP_ADD_ONE, PORT_NUM_ONE, SIMPLE_PACKET);
        try (MockedConstruction<NetHandler> mocked = mockConstruction(NetHandler.class,
                (mock, context) -> doAnswer((i) -> {
                    var packet = i.getArgument(0);
                    assertThat(packet).isEqualTo(simplePacket);
                    return null;
                }).when(mock).send(any()))) {

            WakeOnService wakeOnService = new WakeOnService();
            wakeOnService.wake(simpleHost);
            assertThat(mocked.constructed()).hasSize(1);
        }
    }
}

