package net.yoedtos.wakeonfx.control;

import org.testfx.framework.junit5.ApplicationTest;

public abstract class UIBaseTest extends ApplicationTest {
    protected String[] txtComIds = new String[]
            {"#txtName", "#txtIp",
            "#txtMac0", "#txtMac1", "#txtMac2",
            "#txtMac3", "#txtMac4", "#txtMac5",
            "#txtPort"};

    protected String[] txtSecOnIds = new String[]
            {"#txtSec0", "#txtSec1", "#txtSec2",
            "#txtSec3", "#txtSec4", "#txtSec5"};

}
