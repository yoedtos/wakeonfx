package net.yoedtos.wakeonfx.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Address {
    private static final Logger LOGGER = LoggerFactory.getLogger(Address.class);

    private final String ip;
    private final String[] mac;
    private final String[] secureOn;

    public Address(String ip, String[] mac, String[] secureOn) {
        this.ip = ip;
        this.mac = mac;
        this.secureOn = secureOn;
    }

    public String getIp() {
        return ip;
    }

    public String[] getMac() {
        return mac;
    }

    public String[] getSecureOn() {
        return secureOn;
    }
}
