package net.yoedtos.wakeonfx.util;

public class TestConstants {
    public static final String[] MAC_ADD_ONE = {"00","90","27","8B","56","20"};
    public static final String[] MAC_ADD_TWO = {"00","1B","27","8B","56","4F"};
    public static final String[] MAC_ADD_MOD = {"0C","1B","27","BB","56","4F"};
    public static final String[] BAD_HEXS = {"0C","1B","EH","BB","56","G1"};
    public static final String[] SECURE_ON = {"00","00","00","00","00","00"};
    public static final String IP_ADD_ONE = "127.0.0.1";
    public static final String IP_ADD_TWO = "127.0.0.2";
    public static final String IP_ADD_MOD = "127.0.0.3";
    public static final String IP_ADD_BAD = "127.0.0";
    public static final int PORT_NUM_ONE = 9000;
    public static final int PORT_NUM_TWO = 8000;
    public static final int PORT_NUM_MOD = 5000;
    public static final String SIMPLE_HOST = "Simple Host";
    public static final String SECURE_HOST = "SecureOn Host";
    public static final String MOD_HOST = "Mod Simple Host";
    public static final int ID_ONE = 1;
    public static final int ID_TWO = 2;
    public static final String JSON_HOSTS =
            "[{\"name\":\"Simple Host\"," +
                    "\"address\":{\"ip\":\"127.0.0.1\",\"mac\":[\"00\",\"90\",\"27\",\"8B\",\"56\",\"20\"]}," +
                    "\"port\":9000}," +
             "{\"name\":\"SecureOn Host\"," +
                    "\"address\":{\"ip\":\"127.0.0.2\",\"mac\":[\"00\",\"1B\",\"27\",\"8B\",\"56\",\"4F\"]," +
                    "\"secureOn\":[\"00\",\"00\",\"00\",\"00\",\"00\",\"00\"]}," +
                    "\"port\":8000}]";
}
