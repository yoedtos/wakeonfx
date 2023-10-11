package net.yoedtos.wakeonfx.util;

import static net.yoedtos.wakeonfx.util.TestConstants.*;

import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;

public class TestDataSet {
    public static Address createSimpleAddress() {
            return new Address(IP_ADD_ONE, MAC_ADD_ONE, null);
    }

    public static Host createSimpleHost() {
        return new Host(SIMPLE_HOST,
                        PORT_NUM_ONE, createSimpleAddress());
    }
}
