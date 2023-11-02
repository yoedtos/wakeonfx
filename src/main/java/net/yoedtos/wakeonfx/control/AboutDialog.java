package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.View.ABOUT_DLG;
import static net.yoedtos.wakeonfx.control.View.Icons.APP;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.CoreException;

import java.io.IOException;

class AboutDialog extends Dialog<Void> {

    public AboutDialog() throws CoreException {
        super.setTitle("About");
        super.setResizable(false);
        try {
            var fxmlHandler = new FxmlHandler(ABOUT_DLG).getFxmlLoader();
            super.getDialogPane().setContent(fxmlHandler.load());
        } catch (CoreException | IOException e) {
            throw new CoreException(e.getMessage());
        }
        var stage = (Stage) super.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(APP));
        super.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }
}
