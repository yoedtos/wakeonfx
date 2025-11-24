package net.yoedtos.wakeonfx.validator;

import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.createSimpleHost;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import net.yoedtos.wakeonfx.model.Address;
import net.yoedtos.wakeonfx.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HostValidatorTest {

    private HostValidator hostValidator;

    @BeforeEach
    void init() {
        hostValidator = new HostValidator();
    }

    @Test
    void whenHostIsValidShouldPass() {
        Host validHost = createSimpleHost();
        assertThatCode(() -> hostValidator.validate(validHost))
                .doesNotThrowAnyException();
    }

    @Test
    void whenHostNameIsInvalidShouldFail() {
        Host nameInvalidHost = new Host("", PORT_NUM_ONE, TIME_OUT_ONE, null);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(nameInvalidHost));
    }

    @Test
    void whenHostPortIsInvalidShouldFail() {
        Host portInvalidHost = new Host(SIMPLE_HOST, 0, TIME_OUT_ONE, null);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(portInvalidHost));
    }
    @Test
    void whenHostPortIsOutOfRangeShouldFail() {
        Host portInvalidHost = new Host(SIMPLE_HOST, 66000, TIME_OUT_ONE, null);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(portInvalidHost));
    }

    @Test
    void whenHostTimeoutIsInvalidShouldFail() {
        Host timeoutInvalidHost = new Host(SIMPLE_HOST, PORT_NUM_ONE, 0, null);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(timeoutInvalidHost));
    }

    @Test
    void whenHostTimeoutIsOutOfRangeShouldFail() {
        Host timeoutInvalidHost = new Host(SIMPLE_HOST, PORT_NUM_ONE, 301, null);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(timeoutInvalidHost));
    }

    @Test
    void whenHostIpAddressIsInvalidShouldFail() {
        Address ipInvalid = new Address(IP_ADD_BAD, null, null);
        Host host = new Host(SIMPLE_HOST, PORT_NUM_ONE, TIME_OUT_ONE, ipInvalid);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(host));
    }

    @Test
    void whenHostMacAddressIsInvalidShouldFail() {
        Address invalidMac = new Address(IP_ADD_ONE, BAD_HEXS, null);
        Host host = new Host(SIMPLE_HOST, PORT_NUM_ONE, TIME_OUT_ONE, invalidMac);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(host));
    }

    @Test
    void whenHostSecureOnIsInvalidShouldFail() {
        Address invalidSecOn = new Address(IP_ADD_ONE, MAC_ADD_ONE, BAD_HEXS);
        Host host = new Host(SIMPLE_HOST, PORT_NUM_ONE, TIME_OUT_ONE, invalidSecOn);
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> hostValidator.validate(host));
    }
}
