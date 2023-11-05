package net.yoedtos.wakeonfx;

import net.yoedtos.wakeonfx.control.MainGUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WakeOnFx {
    private static final Logger LOGGER = LoggerFactory.getLogger(WakeOnFx.class);

    public static void main(String[] args) {
        try {
            MainGUI.begin();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}