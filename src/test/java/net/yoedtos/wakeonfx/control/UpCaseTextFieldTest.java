package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.util.TestConstants.TYPED_STRING;
import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

class UpCaseTextFieldTest extends UIBaseTest {
    private static final String ID_FIELD = "txtUpCase";

    private UpCaseTextField textField;

    @Override
    public void start(Stage stage) {
        textField = new UpCaseTextField();
        textField.setId(ID_FIELD);
        var scene = new Scene(textField);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void whenLimitIsFiveShouldHaveFieldWithFiveCharacterInUpperCase() {
        textField.setLimit(5);
        clickOn("#"+ID_FIELD).write(TYPED_STRING);

        var expected = TYPED_STRING.substring(0, 5).toUpperCase();
        assertThat(textField.getText()).isEqualTo(expected);
    }
}
