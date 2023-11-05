package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.Constants.MAIN_VIEW_SIZE;
import static net.yoedtos.wakeonfx.control.View.Icons.APP;
import static net.yoedtos.wakeonfx.control.View.MAIN_VIEW;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var scene = new FxmlHandler(MAIN_VIEW).createScene(MAIN_VIEW_SIZE);
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass()
                        .getResource("/styles/styles.css")).toExternalForm());
        stage.setTitle("WakeOn FX!");
        stage.setScene(scene);
        stage.getIcons().add(new Image(APP));
        stage.show();
        stage.centerOnScreen();
    }

    public static void begin() {
        launch();
    }
}