package net.yoedtos.wakeonfx.util;

import static net.yoedtos.wakeonfx.util.Constants.TIMEOUT;
import static net.yoedtos.wakeonfx.util.TestConstants.*;

import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;

import java.util.Arrays;
import java.util.List;

public class TestDataSet {
    public static Address createSimpleAddress() {
            return new Address(IP_ADD_ONE, MAC_ADD_ONE, null);
    }

    public static Address createSecureOnAddress() {
        return new Address(IP_ADD_TWO, MAC_ADD_TWO, SECURE_ON);
    }

    public static Host createSimpleHost() {
        return new Host(SIMPLE_HOST,
                        PORT_NUM_ONE, createSimpleAddress());
    }

    public static Host createSimpleHostMod() {
        return new Host(SIMPLE_HOST,
                PORT_NUM_TWO, createSecureOnAddress());
    }

    public static Host createSecureOnHost() {
        return new Host(SECURE_HOST,
                        PORT_NUM_TWO, createSecureOnAddress());
    }

    public static List<String> createLinuxPing() {
       return Arrays.asList("ping", "-c1", "-W", String.valueOf(TIMEOUT), IP_ADD_ONE);
    }
}
