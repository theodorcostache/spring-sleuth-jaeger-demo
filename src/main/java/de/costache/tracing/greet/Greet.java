package de.costache.tracing.greet;

public class Greet {

    private final long id;
    private final String text;

    public Greet(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}