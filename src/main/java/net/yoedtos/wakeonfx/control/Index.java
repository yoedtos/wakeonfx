package net.yoedtos.wakeonfx.control;

import java.util.Objects;

public class Index {
    private int id;
    private String name;

    public Index(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Index{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return id == index.id && Objects.equals(name, index.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
