package inventory.model;

public class Product {

    private String sku;
    private String name;
    private double price;
    private int categoryId;
    private int stock;

    public Product(String sku, String name, double price, int categoryId, int stock) {

        this.sku = sku;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;

    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getStock() {
        return stock;
    }

}
