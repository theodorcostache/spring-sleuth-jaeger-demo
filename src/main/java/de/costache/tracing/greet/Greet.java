package de.costache.tracing.greet;

public class Greet {

    private final String id;
    private final String text;

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
}