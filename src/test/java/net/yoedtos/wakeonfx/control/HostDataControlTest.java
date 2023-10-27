package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.Constants.HOST_DATA_SIZE;
import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.service.HostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class HostDataControlTest extends UIBaseTest {

    private String[] txtDataOne = new String[]
            {SIMPLE_HOST, IP_ADD_ONE,
            MAC_ADD_ONE[0], MAC_ADD_ONE[1], MAC_ADD_ONE[2],
            MAC_ADD_ONE[3], MAC_ADD_ONE[4], MAC_ADD_ONE[5],
            String.valueOf(PORT_NUM_ONE)};

    private String[] txtDataTwo = new String[]
            {SECURE_HOST, IP_ADD_TWO,
            MAC_ADD_TWO[0], MAC_ADD_TWO[1], MAC_ADD_TWO[2],
            MAC_ADD_TWO[3], MAC_ADD_TWO[4], MAC_ADD_TWO[5],
            String.valueOf(PORT_NUM_TWO)};

    private String[] txtDataSecOn = new String[]
            {SECURE_ON[0], SECURE_ON[1], SECURE_ON[2],
            SECURE_ON[3], SECURE_ON[4], SECURE_ON[5]};

    private Host hostOne;

    @Mock
    private HostService mockHostService;
    @InjectMocks
    private HostDataControl hostDataControl;

    @BeforeEach
    void setup() {
        hostOne = createSimpleHost();
        openMocks(this);
    }

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(HostDataControl.class.getResource(View.HOST_DATA));
        var scene = new Scene(fxmlLoader.load(), HOST_DATA_SIZE[0], HOST_DATA_SIZE[1]);
        stage.setScene(scene);
        stage.show();
        hostDataControl = fxmlLoader.getController();
        hostDataControl.onStageDefined(stage);
    }

    @Test
    void whenAddThenSaveSimpleHostShouldHaveIndexObject() throws ServiceException {
        var indexOne = new Index(ID_ONE, SIMPLE_HOST);
        when(mockHostService.create(any())).thenReturn(indexOne);

        for (int i = 0; i < txtComIds.length; i++) {
            clickOn(txtComIds[i]).write(txtDataOne[i]);
        }

        clickOn("#btnSave");
        var index = hostDataControl.getIndex();
        assertThat(index).isEqualTo(indexOne);
        verify(mockHostService, times(1)).create(hostOne);
    }

    @Test
    void whenAddThenSaveSecureHostShouldHaveIndexObject() throws ServiceException {
        var hostTwo = createSecureOnHost();
        var indexTwo = new Index(ID_TWO, SECURE_HOST);
        when(mockHostService.create(any())).thenReturn(indexTwo);

        for (int i = 0; i < txtComIds.length; i++) {
            clickOn(txtComIds[i]).write(txtDataTwo[i]);
        }

        clickOn("#ckBxSecure");
        for (int i = 0; i < txtSecOnIds.length; i++) {
            clickOn(txtSecOnIds[i]).write(txtDataSecOn[i]);
        }

        clickOn("#btnSave");
        var index = hostDataControl.getIndex();
        assertThat(index).isEqualTo(indexTwo);
        verify(mockHostService, times(1)).create(hostTwo);
    }

    @Test
    void whenSaveHostFailedShouldShowAlertMessage() throws ServiceException {
        when(mockHostService.create(any())).thenThrow(new ServiceException());

        for (int i = 0; i < txtComIds.length; i++) {
            clickOn(txtComIds[i]).write(txtDataOne[i]);
        }
        clickOn("#btnSave");
        verifyThat(View.Error.SAVE, NodeMatchers.isVisible());
    }
}