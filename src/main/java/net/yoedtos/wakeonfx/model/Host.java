package net.yoedtos.wakeonfx.model;

public class Host {
    private String name;
    private Address address;
    private int port;

    public Host(String name, int port, Address address) {
        this.name = name;
        this.port = port;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public Address getAddress() {
        return address;
    }
}
