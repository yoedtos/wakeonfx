package net.yoedtos.wakeonfx.core;

import static net.yoedtos.wakeonfx.util.Constants.SECURE_SIZE;
import static net.yoedtos.wakeonfx.util.Constants.SIMPLE_SIZE;
import static net.yoedtos.wakeonfx.util.TestConstants.SECURE_PACKET;
import static net.yoedtos.wakeonfx.util.TestConstants.SIMPLE_PACKET;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSecureOnHost;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.Assertions.assertThat;

import net.yoedtos.wakeonfx.exceptions.CoreException;
import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WakeOnTest {

    private Host simpleHost;
    private Host secureOnHost;

    @BeforeEach
    void setup() {
        simpleHost = createSimpleHost();
        secureOnHost = createSecureOnHost();
    }

    @Test
    void testCreateWakeOnSimpleObject() throws ValidationException {
        WakeOn wakeOn = WakeOn.newInstance(simpleHost);
        assertThat(wakeOn).isInstanceOf(SimpleWakeOn.class);
    }
    @Test
    void testCreateWakeOnSecureOnObject() throws ValidationException {
        WakeOn wakeOn = WakeOn.newInstance(secureOnHost);
        assertThat(wakeOn).isInstanceOf(SecureWakeOn.class);
    }

    @Test
    void testSimplePacketBlock() throws CoreException, ValidationException {
        var packet = WakeOn.newInstance(simpleHost).create();
        assertThat(packet).hasSize(SIMPLE_SIZE).containsExactly(SIMPLE_PACKET);
    }

    @Test
    void testSecureOnPacketBlock() throws CoreException, ValidationException {
        var packet = WakeOn.newInstance(secureOnHost).create();
        assertThat(packet).hasSize(SECURE_SIZE).containsExactly(SECURE_PACKET);
    }
}
