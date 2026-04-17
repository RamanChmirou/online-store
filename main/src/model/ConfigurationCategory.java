package model;

public enum ConfigurationCategory {
    PROCESSOR("Procesor"),
    RAM("RAM"),
    COLOR("Kolor"),
    ACCESSORIES("Dodatki");

    private final String name;

    ConfigurationCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
