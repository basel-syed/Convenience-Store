package Milestone;

import java.util.ArrayList;

/**
 * @author Mohamed Selim, Basel Syed
 */
public class ShoppingCart implements ProductStockContainer {
    private ArrayList<Product> products;
    private ArrayList<Integer> amount;

    /**
     * This is the default constructor for Milestone.ShoppingCart.
     * <p>
     * It initializes products to an empty HashMap.
     */
    public ShoppingCart() {
        products = new ArrayList<Product>();
        amount = new ArrayList<Integer>();
    }

    /**
     * This method adds the given quantity of the passed Product to the cart.
     * The method compares the Product ID field for each of the Products in the cart.
     * If the item is not found, the method adds it to the HashMap products with the given quantity.
     *
     * @param product  Product   the item we want to add more of.
     * @param quantity int   the amount of the item that we want to add.
     */
    @Override
    public void addProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            if (product.getId() == products.get(i).getId()) {
                amount.set(i, amount.get(i) + quantity);
                return;
            }
        }
        products.add(product);
        amount.add(quantity);
    }

    /**
     * A getter method for the products field.
     *
     * @return ArrayList<Product>   a list of the products in the cart
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * The number of products in the cart.
     *
     * @return int   the total number of products in the cart
     */
    @Override
    public int getNumOfProducts() {
        int number = 0;
        for (int i = 0; i < amount.size(); i++) {
            number += amount.get(i);
        }
        return number;
    }

    /**
     * Find stock information of a product
     *
     * @param product the product to find the stock of
     * @return int   An integer representing the amount of stock available
     */
    @Override
    public int getProductQuantity(Product product) {
        if (product == null) {
            return 0;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                return amount.get(i);
            }
        }
        return -1;
    }

    /**
     * This method removes the given quantity of the passed Product to the cart,
     * returning true if successful
     * .
     * The method compares the Product ID field for each of the Products in the cart.
     * If the item is not found or the quantity present is less than the
     * quantity to be removed, the method returns false without changing any fields.
     *
     * @param product  Product   the product to be removed.
     * @param quantity int   the amount we want to remove.
     * @return boolean   true if the removal was successful, false if it wasn't.
     */
    @Override
    public boolean removeProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        for (int i = 0; i < products.size(); i++) {
            if (product.getId() == products.get(i).getId()) {
                if (amount.get(i) >= quantity) {
                    amount.set(i, amount.get(i) - quantity);
                    if (amount.get(i) == 0) {
                        products.remove(i);
                        amount.remove(i);
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
