package Milestone;

/**
 * A Product.
 *
 * @author Mohamed Selim, Basel Syed
 */
public class Product {
    private String name;
    private int id;
    private double price;

    /**
     * A constructor for the Product object.
     *
     * @param name  String   the name of the Product.
     * @param id    int   the ID of the Product.
     * @param price double   the price of the Product.
     */
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * Returns the name of the Product.
     *
     * @return String the name of the Product.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the numberical ID of the Product
     *
     * @return int   the ID of the Product.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the price of the Product.
     *
     * @return double   price of the Product.
     */
    public double getPrice() {
        return this.price;
    }

}
