package net.yoedtos.wakeonfx.model;

import static net.yoedtos.wakeonfx.util.Constants.FRAME_LENGTH;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

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

    public static byte[] hexToBytes(String[] hexStr) throws ValidationException {
        byte[] hexBytes = new byte[FRAME_LENGTH];
        try {
            for (int i = 0; i < hexBytes.length; i++) {
                hexBytes[i] = (byte) Integer.parseInt(hexStr[i], 16);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return hexBytes;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", mac=" + Arrays.toString(mac) +
                ", secureOn=" + Arrays.toString(secureOn) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(ip, address.ip) && Arrays.equals(mac, address.mac) && Arrays.equals(secureOn, address.secureOn);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ip);
        result = 31 * result + Arrays.hashCode(mac);
        result = 31 * result + Arrays.hashCode(secureOn);
        return result;
    }
}
