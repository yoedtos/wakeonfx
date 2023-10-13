package net.yoedtos.wakeonfx.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", port=" + port +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return port == host.port && Objects.equals(name, host.name) && Objects.equals(address, host.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, port);
    }
}
