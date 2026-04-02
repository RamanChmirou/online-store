package entity;

public enum ProductType {
    COMPUTER("Computer"),
    SMARTPHONE("Smartphone"),
    ELECTRONICS("Electronics");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}