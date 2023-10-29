package net.yoedtos.wakeonfx.control;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.service.MonitorService;
import net.yoedtos.wakeonfx.service.WakeOnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HostControl implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostControl.class);

    private Index index;
    private Rectangle status;

    private WakeOnService wakeOnService;
    private MonitorService monitorService;
    private ScheduledExecutorService scheduler;

    private final Service<Status> wakeTask = new Service<>() {
        @Override
        protected Task<Status> createTask() {
            return new Task<>() {
                @Override
                protected Status call() throws Exception {
                    wakeOnService.wake(index);
                    return Status.OFF_LINE;
                }
            };
        }
    };

    public HostControl() {
        wakeOnService = new WakeOnService();
        monitorService = new MonitorService();
    }

    public VBox getHostView(Index index) {
        this.index = index;
        var vBox = new VBox(3);
        vBox.prefHeight(180);
        vBox.prefWidth(200);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("hostVbox");

        var lbName = new Label();
        lbName.setText(index.getName());
        lbName.getStyleClass().add("hostLabel");

        status = new Rectangle();
        status.setId("rtStatus");
        status.setHeight(35);
        status.setWidth(160);
        status.setArcHeight(5.0);
        status.setArcWidth(5.0);
        status.setFill(Paint.valueOf("SILVER"));

        var btnWake = new Button(null, new ImageView(View.Icons.HOST));
        btnWake.setId("btnWake");
        btnWake.setMaxSize(160, 100);
        btnWake.setContentDisplay(ContentDisplay.TOP);
        btnWake.setOnAction(this);

        VBox.setMargin(btnWake, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(lbName);
        vBox.getChildren().add(btnWake);
        vBox.getChildren().add(status);
        return vBox;
    }

    public void initiate() {
        startScheduler();
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    @Override
    public void handle(Event event) {
        wakeTask.setOnSucceeded(e -> {
            status.setFill(Paint.valueOf(wakeTask.getValue().value));
        });

        wakeTask.setOnFailed(e -> {
            LOGGER.error(e.getSource().getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.WAKE).show();
        });
        wakeTask.start();
    }

    private void startScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
                    Status result = null;
                    try {
                        result = monitorService.monitor(index);
                    } catch (ServiceException e) {
                        LOGGER.error(e.getMessage());
                    }
                    var finalResult = result;
                    Platform.runLater(() -> {
                        assert finalResult != null;
                        status.setFill(Paint.valueOf(finalResult.value));
                    });
                },
                3, 20, TimeUnit.SECONDS);
    }
}
