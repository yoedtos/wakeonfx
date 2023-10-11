package net.yoedtos.wakeonfx.validator;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;

public class HostValidator {
    private static final int NAME_MAX = 20;
    private static final int PORT_MIN = 1;
    private static final int PORT_MAX = 65536;
    private static final int BLOCK_SIZE = 4;

    public void validate(Host host) throws ValidationException {
        if (host.getName().isEmpty() || host.getName().length() > NAME_MAX) {
            throw new ValidationException("Name has invalid size");
        }
        if(host.getPort() < PORT_MIN || host.getPort() > PORT_MAX) {
            throw new ValidationException("Port range is invalid");
        }
        Address address = host.getAddress();
        var blocks = address.getIp().split("\\.");
        if(blocks.length != BLOCK_SIZE) {
            throw new ValidationException("IP address is invalid");
        }
        try {
            hexValidation(address.getMac());
            if (address.getSecureOn() != null) {
             hexValidation(address.getSecureOn());
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid value");
        }
    }

    private void hexValidation(String[] mac) {
        for (String hex: mac) {
            Integer.parseInt(hex, 16);
        }
    }
}
