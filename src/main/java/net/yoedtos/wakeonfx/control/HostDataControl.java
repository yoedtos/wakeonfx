package net.yoedtos.wakeonfx.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.ServiceException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostDataControl implements Control {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostDataControl.class);

    @FXML
    TextField txtName, txtIp, txtPort;
    @FXML
    TextField txtMac0, txtMac1, txtMac2, txtMac3, txtMac4, txtMac5;
    @FXML
    TextField txtSec0, txtSec1, txtSec2, txtSec3, txtSec4, txtSec5;
    @FXML
    HBox hboxSec;
    @FXML
    CheckBox ckBxSecure;

    private Stage stage;
    private Index index;
    private HostService hostService;

    public HostDataControl() {
        this.hostService = new HostService();
    }

    public Index getIndex() {
        return index;
    }

    @FXML
    public void setSecure() {
        if (hboxSec.isDisabled()) {
            hboxSec.setDisable(false);
        }
    }

    @FXML
    public void save() {
       var host = mapFromUIToHost();
        try {
           index = hostService.create(host);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            new Alert(Alert.AlertType.ERROR, View.Error.SAVE).show();
        }
        stage.close();
    }

    @FXML
    public void cancel() {
        stage.close();
    }

    @Override
    public void onStageDefined(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void onObjectDefined(Object object) throws Exception {

    }

    private Host mapFromUIToHost() {
        String[] secureOn = null;
        if (!hboxSec.isDisabled()) {
            secureOn = new String[6];
            secureOn[0] = txtSec0.getText();
            secureOn[1] = txtSec1.getText();
            secureOn[2] = txtSec2.getText();
            secureOn[3] = txtSec3.getText();
            secureOn[4] = txtSec4.getText();
            secureOn[5] = txtSec5.getText();
        }
        String[] mac = new String[6];
        mac[0] = txtMac0.getText();
        mac[1] = txtMac1.getText();
        mac[2] = txtMac2.getText();
        mac[3] = txtMac3.getText();
        mac[4] = txtMac4.getText();
        mac[5] = txtMac5.getText();

        return  new Host(txtName.getText(),
                        Integer.parseInt(txtPort.getText()),
                        new Address(txtIp.getText(), mac, secureOn));
    }
}
