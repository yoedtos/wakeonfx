package net.yoedtos.wakeonfx.model;

import static net.yoedtos.wakeonfx.util.Constants.FRAME_LENGTH;
import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import net.yoedtos.wakeonfx.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void testMacAddressConvertToBytes() throws ValidationException {
        var result = Address.hexToBytes(MAC_ADD_ONE);
        assertThat(result.length).isEqualTo(FRAME_LENGTH);
        assertThat(result).contains(BIN_MAC_ADDRESS);
    }

    @Test
    void testSecureOnConvertToBytes() throws ValidationException {
        var result = Address.hexToBytes(SECURE_ON);
        assertThat(result.length).isEqualTo(FRAME_LENGTH);
        assertThat(result).contains(BIN_SECURE_ON);
    }

    @Test
    void testInvalidValueConvertToBytes() {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> Address.hexToBytes(BAD_HEXS));
    }
}
