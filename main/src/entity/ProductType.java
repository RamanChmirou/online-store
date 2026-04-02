package entity;

public enum ProductType {
    Computer("Computer"),
    Smartphone("Smartphone"),
    Electronics("Electronics");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}