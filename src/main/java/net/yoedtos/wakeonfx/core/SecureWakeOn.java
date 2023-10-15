package net.yoedtos.wakeonfx.core;

import static net.yoedtos.wakeonfx.util.Constants.SECURE_SIZE;
import static net.yoedtos.wakeonfx.util.Constants.SIMPLE_SIZE;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;

final class SecureWakeOn extends WakeOn {

    private final byte[] secureOnBytes;

    SecureWakeOn(Address address) throws ValidationException {
        super(address);
        this.secureOnBytes = Address.hexToBytes(address.getSecureOn());
        super.packet = new byte[SECURE_SIZE];
    }

    @Override
    protected byte[] build() {
        System.arraycopy(secureOnBytes, 0, packet, SIMPLE_SIZE, secureOnBytes.length);
        return packet;
    }
}
