package net.yoedtos.wakeonfx.core;

import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public abstract class WakeOn {
    private static final Logger LOGGER = LoggerFactory.getLogger(WakeOn.class);

    protected byte[] packet;
    private byte[] macBytes;

    protected WakeOn(Address address) throws ValidationException {
        this.macBytes = Address.hexToBytes(address.getMac());
    }

    public final byte[] create() throws CoreException {
        try {
            Arrays.fill(packet, 0, 6,(byte) 0xFF);
            for (int i = 6; i < packet.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, packet, i, macBytes.length);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CoreException("Failed to create packet");
        }
        return build();
    }

    protected abstract byte[] build();

    public static WakeOn newInstance(Host host) throws ValidationException {
        try {
            Address address = host.getAddress();
            if(address.getSecureOn() != null) {
                return new SecureWakeOn(address);
            }
            return new SimpleWakeOn(address);
        } catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
