package de.costache.tracing.greet;

import java.util.Objects;

public class Greet {

    private String id;
    private String text;

    public Greet() {
    }

    public Greet(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greet greet = (Greet) o;
        return id.equals(greet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}