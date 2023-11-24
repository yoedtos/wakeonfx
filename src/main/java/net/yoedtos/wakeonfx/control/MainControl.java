package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.Constants.HOST_DATA_SIZE;
import static net.yoedtos.wakeonfx.control.View.Icons.APP;
import static net.yoedtos.wakeonfx.control.View.Text.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MainControl implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainControl.class);

    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDrop;
    @FXML
    private TilePane tpHosts;

    private List<Index> indexList;
    private final List<HostControl> hostControls;
    private Index index;
    private VBox vBox;
    private final HostService hostService;

    public MainControl() {
        hostService = new HostService();
        hostControls = new ArrayList<>();
        loadHosts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexList.forEach(this::createHostViewAndControl);
    }

    private void loadHosts() {
        if (indexList == null) {
            try {
                var task = new Callable<List<Index>>() {
                    @Override
                    public List<Index> call() throws Exception {
                        return hostService.getIndices();
                    }
                };
                indexList = task.call();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @FXML
    public void add() {
        try {
            var controller = createStageAndShow(ADD_HOST, null);
            var indexNew = ((HostDataControl) controller).getIndex();
            if (indexNew != null)
                createHostViewAndControl(indexNew);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.LOAD_RSC).show();
        }
    }

    @FXML
    public void edit() {
        try {
            createStageAndShow(EDIT_HOST, index);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.LOAD_RSC).show();
        }
        disableEditDrop(true, false);
    }

    @FXML
    public void drop() {
        if (vBox != null && index != null && showIsOkDropAlert())
            dropHost(vBox, index);
    }

    @FXML
    public void quit() {
        hostControls.forEach( h -> {
            if (h != null) h.shutdown();
        });
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void showAbout() {
        try {
            new AboutDialog().showAndWait();
        } catch (CoreException e) {
            LOGGER.error(e.getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.LOAD_RSC).show();
        }
    }

    /**
     * This method control Edit and Drop button avalabilty,
     * value is true it will disable
     * setTimer is true it will disable em 10 seconds
     *
     * @param value
     * @param setTimer
     */
    private void disableEditDrop(boolean value, boolean setTimer) {
        btnEdit.setDisable(value);
        btnDrop.setDisable(value);
        if (setTimer) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> {
                    disableEditDrop(true, false);
                    index = null;
                    vBox = null;
                });
            }).start();
        }
    }

    private void dropHost(VBox vBox, Index index) {
        try {
            var id = index.getId();
            hostService.remove(id);
            hostControls.get(id).shutdown();
            hostControls.set(id, null);
            tpHosts.getChildren().remove(vBox);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.DROP).show();
        }
        disableEditDrop(true, false);
    }

    private Control createStageAndShow(String title, Index index) throws Exception {
        var fxmlLoader = new FxmlHandler(View.HOST_DATA);

        var stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(fxmlLoader.createScene(HOST_DATA_SIZE));
        stage.getIcons().add(new Image(APP));

        var controller = (Control) fxmlLoader.getController();
        controller.onStageDefined(stage);
        if (index != null) {
            controller.onObjectDefined(index.getId());
        }
        stage.showAndWait();

        return controller;
    }

    private void createHostViewAndControl(Index index) {
        var control = new HostControl();
        VBox box = control.getHostView(index);
        var label = (Label) box.getChildren().get(0);
        label.setOnMouseClicked(event -> {
            disableEditDrop(false, true);
            this.vBox = box;
            this.index = index;
        });
        tpHosts.getChildren().add(box);
        control.initiate();
        hostControls.add(index.getId(), control);
    }

    private boolean showIsOkDropAlert() {
        var dropAlert = new Alert(Alert.AlertType.CONFIRMATION);
        dropAlert.setTitle(DROP_HOST);
        dropAlert.setContentText(DROP_MSG + index.getName() + "?");
        var result = dropAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}