package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.util.TestConstants.ID_ONE;
import static net.yoedtos.wakeonfx.util.TestConstants.SIMPLE_HOST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;

import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.service.MonitorService;
import net.yoedtos.wakeonfx.service.WakeOnService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class HostControlTest extends UIBaseTest {

    @Mock
    private WakeOnService mockWakeOnService;
    @Mock
    private MonitorService mockMonitorService;
    @InjectMocks
    private HostControl hostControl;

    private Index indexOne;

    public HostControlTest() {
        hostControl = new HostControl();
        indexOne = new Index(ID_ONE, SIMPLE_HOST);
    }

    @BeforeEach
    void setup() {
        openMocks(this);

        assertHostName();
    }

    @Override
    public void start(Stage stage) {
        var scene = new Scene(hostControl.getHostView(indexOne));
        stage.setScene(scene);
        stage.show();
    }

    @Nested
    class WakeOnHostTest {
        @Test
        void whenClickWakeSuccessShouldShowStatus_Red() throws ServiceException {
            doNothing().when(mockWakeOnService).wake(any(Index.class));
            clickOn("#btnWake");
            WaitForAsyncUtils.waitForFxEvents();

            verify(mockWakeOnService, times(1)).wake(indexOne);
            assertStatus("CRIMSON");
        }

        @Test
        void whenClickWakeFailedShouldShowAlert() throws ServiceException {
            doThrow(new ServiceException()).when(mockWakeOnService).wake(any(Index.class));

            clickOn("#btnWake");
            WaitForAsyncUtils.waitForFxEvents();

            verify(mockWakeOnService, times(1)).wake(indexOne);
            assertStatus("SILVER");
        }
    }

    @Nested
    class MonitorHostTest {
        @BeforeEach
        void setup() {
            hostControl.initiate();
        }

        @AfterEach
        void teardown() {
            hostControl.shutdown();
        }

        @Test
        void whenMonitorHostIsOnlineShouldShowStatus_Lime() throws ServiceException {
            when(mockMonitorService.monitor(any(Index.class))).thenReturn(Status.ON_LINE);
            WaitForAsyncUtils.sleep(4, TimeUnit.SECONDS);
            assertStatus("LIME");
        }

        @Test
        void whenMonitorHostIsErrorShouldShowStatus_Gold() throws ServiceException {
            when(mockMonitorService.monitor(any(Index.class))).thenReturn(Status.IS_ERROR);
            WaitForAsyncUtils.sleep(4, TimeUnit.SECONDS);
            assertStatus("GOLD");
        }

        @Test
        void whenMonitorHostIsOfflineShouldShowStatus_Crimson() throws ServiceException {
            when(mockMonitorService.monitor(any(Index.class))).thenReturn(Status.OFF_LINE);
            WaitForAsyncUtils.sleep(4, TimeUnit.SECONDS);
            assertStatus("CRIMSON");
        }
    }


    private void assertHostName() {
        verifyThat(SIMPLE_HOST, NodeMatchers.isVisible());
    }

    private void assertStatus(String color) {
        var status = (Rectangle) getNode("#rtStatus", Rectangle.class);
        assertThat(status.getFill()).isEqualTo(Paint.valueOf(color));
    }
}
