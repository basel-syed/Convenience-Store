package Milestone;

import java.util.ArrayList;


/**
 * @author Mohamed Selim, Basel Syed
 */
public class StoreManager {

    //Attributes
    private Inventory inventory;
    private ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();

    /* This constructor can be used if there is a ready made inventory for us to use. However it was unclear whether we
    needed it for milestone 2.*/
    public StoreManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public StoreManager() {
        this.inventory = new Inventory();
    }


    /**
     * This method adds a new cart and returns its index to be used as the cartID.
     *
     * @return int   the index of the added cart, to be used as cartID.
     */
    public int assignNewCartID() {
        shoppingCarts.add(new ShoppingCart());
        return shoppingCarts.size() - 1; //Index of new shopping cart
    }

    /**
     * Add an item to the cart
     *
     * @param cartID   int representing the cart to check
     * @param option   an int representing the item to add
     * @param quantity an int representing quantity to add
     * @return returns a boolean value depending on if the addition was successful
     */
    public boolean addToCart(int cartID, int option, int quantity) {
        if (option < 0 || option >= inventory.getProductList().size() || cartID < 0 || cartID >= shoppingCarts.size()) {
            return false;
        }
        if (quantity <= getStock(inventory.getProductList().get(option))) {
            shoppingCarts.get(cartID).addProductQuantity(inventory.getProductList().get(option), quantity);
            inventory.removeProductQuantity(inventory.getProductList().get(option), quantity);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Remove an item from a cart in shoppingCarts
     *
     * @param cartID   an int representing the cart to check
     * @param option   an int representing the item to remove
     * @param quantity an int representing quantity to remove
     * @return returns a boolean value depending on if the subtraction was successful
     */
    public boolean removeFromCart(int cartID, int option, int quantity) {
        if (option < 0 || option >= inventory.getProductList().size() || cartID < 0 || cartID >= shoppingCarts.size()) {
            return false;
        }
        boolean removed = shoppingCarts.get(cartID).removeProductQuantity(inventory.getProductList().get(option), quantity);
        if (removed) {
            inventory.addProductQuantity(inventory.getProductList().get(option), quantity);
            return true;
        }
        return false;


    }

    /**
     * Find stock information of a product
     *
     * @param product the product to find the stock of
     * @return An integer representing the amount of stock available
     */
    public int getStock(Product product) {
        if (product == null) {
            return 0;
        }
        return inventory.getProductQuantity(product);
    }

    /**
     * Returns the productList of the Inventory object associated with the StoreManager
     *
     * @return ArrayList<Product>   a list of the Products in the Inventory of the StoreManager.
     */
    public ArrayList<Product> getProductList() {
        return this.inventory.getProductList();
    }

    /**
     * Returns the stockList of the Inventory object associated with the StoreManager
     *
     * @return ArrayList<Integer>   a list of the Amounts of products in the Inventory
     */
    public ArrayList<Integer> getStockList() {
        return this.inventory.getStockList();
    }

    /**
     * Getter method for shoppingCarts.
     *
     * @return ArrayList<ShoppingCart>   the ArrayList of Shopping Carts
     */
    public ArrayList<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    /**
     * Checks out the items in a shopping cart
     *
     * @param cartID represents the shopping cart to checkout
     * @return the total as a double
     */
    public double checkout(int cartID) {
        if (cartID < 0 || cartID >= shoppingCarts.size()) {
            return 0;
        }
        double total = 0;
        ShoppingCart cart = shoppingCarts.get(cartID);
        for (Product product : cart.getProducts()) {        //Loop through the array
            total += product.getPrice() * cart.getProductQuantity(product);
        }
        return total;
    }


}
