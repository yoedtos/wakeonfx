package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.util.TestConstants.TYPED_STRING;
import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

class LimitedTextFieldTest extends UIBaseTest {
    private static final String ID_FIELD = "txtLimited";

    private LimitedTextField textField;

    @Override
    public void start(Stage stage) {
        textField = new LimitedTextField();
        textField.setId(ID_FIELD);
        var scene = new Scene(textField);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void whenLimitIsFiveShouldHaveFieldWithFiveCharacter() {
        textField.setLimit(5);
        clickOn("#"+ID_FIELD).write(TYPED_STRING);

        assertThat(textField.getText()).isEqualTo(TYPED_STRING.substring(0, 5));
    }

    @Test
    void whenLimitedReachedPressBackSpaceAndEnterDash() {
        textField.setLimit(5);
        clickOn("#"+ID_FIELD).write(TYPED_STRING).push(KeyCode.BACK_SPACE).write("-");
        assertThat(textField.getText()).isEqualTo("This-");
    }
}
