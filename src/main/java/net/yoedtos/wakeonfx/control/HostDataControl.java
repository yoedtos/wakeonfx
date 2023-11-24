package net.yoedtos.wakeonfx.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import net.yoedtos.wakeonfx.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostDataControl implements Control {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostDataControl.class);

    @FXML
    private TextField txtName, txtIp, txtPort;
    @FXML
    private TextField txtMac0, txtMac1, txtMac2, txtMac3, txtMac4, txtMac5;
    @FXML
    private TextField txtSec0, txtSec1, txtSec2, txtSec3, txtSec4, txtSec5;
    @FXML
    private HBox hboxSec;
    @FXML
    private CheckBox ckBxSecure;

    private Stage stage;
    private Index index;
    private HostService hostService;
    private boolean editMode;

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
            if (editMode)
                index = hostService.modify(host);
            else
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
        try {
            var id = (int)object;
            var host = hostService.get(id);
            txtName.setText(host.getName());
            txtName.setDisable(true);
            mapFromHostToUI(host);
            editMode = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
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

    private void mapFromHostToUI(Host host) {
        txtName.setText(host.getName());
        txtPort.setText(String.valueOf(host.getPort()));
        txtIp.setText(host.getAddress().getIp());
        txtMac0.setText(host.getAddress().getMac()[0]);
        txtMac1.setText(host.getAddress().getMac()[1]);
        txtMac2.setText(host.getAddress().getMac()[2]);
        txtMac3.setText(host.getAddress().getMac()[3]);
        txtMac4.setText(host.getAddress().getMac()[4]);
        txtMac5.setText(host.getAddress().getMac()[5]);
        if (host.getAddress().getSecureOn() != null) {
            ckBxSecure.setDisable(false);
            ckBxSecure.setSelected(true);
            txtSec0.setText(host.getAddress().getSecureOn()[0]);
            txtSec1.setText(host.getAddress().getSecureOn()[1]);
            txtSec2.setText(host.getAddress().getSecureOn()[2]);
            txtSec3.setText(host.getAddress().getSecureOn()[3]);
            txtSec4.setText(host.getAddress().getSecureOn()[4]);
            txtSec5.setText(host.getAddress().getSecureOn()[5]);
        }
    }
}