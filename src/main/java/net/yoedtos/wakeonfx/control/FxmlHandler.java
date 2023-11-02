package net.yoedtos.wakeonfx.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import net.yoedtos.wakeonfx.exceptions.CoreException;

import java.io.IOException;

public class FxmlHandler {

    private final FXMLLoader fxmlLoader;

    public FxmlHandler(String fxmlView) {
        this.fxmlLoader = new FXMLLoader(View.class.getResource(fxmlView));
    }

    public FXMLLoader getFxmlLoader() throws CoreException {
        if (fxmlLoader != null) {
            return fxmlLoader;
        }
        throw new CoreException("Initialization failed");
    }

    public Scene createScene(int[] size) throws IOException {
        return new Scene(fxmlLoader.load(), size[0], size[1]);
    }

    public Object getController() {
        return fxmlLoader.getController();
    }
}
