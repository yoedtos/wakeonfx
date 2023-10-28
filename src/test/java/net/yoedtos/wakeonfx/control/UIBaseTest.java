package net.yoedtos.wakeonfx.control;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

public abstract class UIBaseTest extends ApplicationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UIBaseTest.class);

    protected String[] txtComIds = new String[]
            {"#txtName", "#txtIp",
            "#txtMac0", "#txtMac1", "#txtMac2",
            "#txtMac3", "#txtMac4", "#txtMac5",
            "#txtPort"};

    protected String[] txtSecOnIds = new String[]
            {"#txtSec0", "#txtSec1", "#txtSec2",
            "#txtSec3", "#txtSec4", "#txtSec5"};

    @BeforeAll
    static void initSetup() {
        if(System.getProperty("headless") != null) {
            LOGGER.info("Running in headless mode");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    protected List<TextField> extractFields(String... ids) {
        var fields = new ArrayList<TextField>();
        for (int i = 0; i < ids.length ; i++) {
            fields.add((TextField)getNode(ids[i], TextField.class));
        }
        return fields;
    }

    protected void clearFields(List<TextField> fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    protected Node getNode(String nodeId, Class clazz) {
        return  lookup(nodeId).queryAs(clazz);
    }
}
