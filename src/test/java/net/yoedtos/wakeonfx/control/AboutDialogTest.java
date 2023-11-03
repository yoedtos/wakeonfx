package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.Constants.ABOUT_DLG_SIZE;
import static net.yoedtos.wakeonfx.control.View.ABOUT_DLG;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

class AboutDialogTest extends UIBaseTest {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new FxmlHandler(ABOUT_DLG).createScene(ABOUT_DLG_SIZE));
        stage.show();
    }

    @Test
    void whenShowAboutShouldHaveCorrectInformation() {
        verifyThat("#imgView", NodeMatchers.isVisible());
        verifyThat("WakeOn FX!", NodeMatchers.isVisible());
        verifyThat("JavaFX Wake On Lan client", NodeMatchers.isVisible());
        verifyThat("Version: 1.0", NodeMatchers.isVisible());
        verifyThat("Author: yoedtos", NodeMatchers.isVisible());
    }
}
