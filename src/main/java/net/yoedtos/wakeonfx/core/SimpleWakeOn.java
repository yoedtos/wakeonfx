package net.yoedtos.wakeonfx.core;

import static net.yoedtos.wakeonfx.util.Constants.SIMPLE_SIZE;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;

final class SimpleWakeOn extends WakeOn {

    SimpleWakeOn(Address address) throws ValidationException {
        super(address);
        super.packet = new byte[SIMPLE_SIZE];
    }

    @Override
    protected byte[] build() {
        return packet;
    }
}
