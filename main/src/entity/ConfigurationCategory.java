package entity;

public enum ConfigurationCategory {
    PROCESSOR("Processor"),
    RAM("RAM"),
    COLOR("Color"),
    ACCESSORIES("Accessories");

    private final String name;

    ConfigurationCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
