package net.yoedtos.wakeonfx.control;

public enum Status {
    ON_LINE("LIME"),
    IS_ERROR("GOLD"),
    OFF_LINE("CRIMSON");

    final String value;

    Status(String value) {
        this.value = value;
    }
}
