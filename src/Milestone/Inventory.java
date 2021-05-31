package Milestone;

import java.util.ArrayList;

/**
 * @author Mohamed Selim, Basel Syed
 */
public class Inventory implements ProductStockContainer {
    private ArrayList<Product> productList = new ArrayList<Product>();
    private ArrayList<Integer> stockList = new ArrayList<Integer>();

    /**
     * This is the constructor currently used. It initializes the inventory with a set of default values to help with testing.
     */
    public Inventory() {
        initializeForTesting();
    }

    /**
     * This is a private method that would help keep the other methods readable and easy to understand.
     * It takes a product object and returns its index in the productList.
     *
     * @param product Product   the product to get the index of.
     * @return int   the index of product in productList.
     */
    private int indexOfProduct(Product product) {
        return productList.indexOf(product);
    }

    /**
     * This method returns the number of products in the inventory
     *
     * @return int   the number of products in the Inventory.
     */
    @Override
    public int getNumOfProducts() {
        int Total = 0;
        for (Integer x : stockList) {
            Total += x;
        }
        return Total;
    }

    /**
     * This is the method that is run when the constructor is called.
     */
    private void initializeForTesting() {
        Product testProduct1 = new Product("Salt", 12, 1.95);
        Product testProduct2 = new Product("Black Pepper", 24, 1.90);
        Product testProduct3 = new Product("Clorox", 40, 4.98);
        Product testProduct4 = new Product("Lays BBQ", 123, 3.55);
        Product testProduct5 = new Product("Chocolate", 2021, 4.19);
        Product testProduct6 = new Product("Tomatoes", 200, 1.20);
        Product testProduct7 = new Product("Shoelace", 190, 5.97);
        this.addProductQuantity(testProduct1, 4);
        this.addProductQuantity(testProduct2, 9);
        this.addProductQuantity(testProduct3, 3);
        this.addProductQuantity(testProduct4, 17);
        this.addProductQuantity(testProduct5, 20);
        this.addProductQuantity(testProduct6, 70);
        this.addProductQuantity(testProduct7, 15);
    }

    /**
     * This method takes a product and returns the amount of it currently in the inventory.
     * It returns 0 if the product is not found.
     *
     * @param product the product in question
     * @return the amount of the Product currently in stock
     */
    @Override
    public int getProductQuantity(Product product) {
        for (Product target : productList) {
            if (target.getId() == (product.getId())) {
                return stockList.get(indexOfProduct(target));
            }
        }
        return 0;
    }

    /**
     * This method adds a quantity of a certain product, and creates a new product in the productList if there are no previous records of it.
     *
     * @param product  Product   the product to be added
     * @param quantity int   the quantity to be added
     */
    @Override
    public void addProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return;
        }
        for (Product target : productList) {
            if (target.getId() == (product.getId())) {
                stockList.set(indexOfProduct(target), stockList.get(indexOfProduct(target)) + quantity);
                return;
            }
        }
        //If passed Product Object has no previous records in the inventory:
        productList.add(product);
        stockList.add(quantity);
        //This if Statement was made during testing to test for possible bugs to make sure the two ArrayLists were growing together when they needed to.
        //if (productList.size() != stockList.size()) {
        //System.out.println("SOMETHING WRONG");
        //}
    }

    /**
     * This method returns the price of a Product given its ID.
     * This method was implemented to help with the operation of Milestone.StoreManager
     *
     * @param productID int   the ID of the product
     * @return double   the price of the product
     */
    public double getPrice(int productID) {
        for (Product target : this.productList) {
            if (target.getId() == (productID)) {
                return target.getPrice();
            }
        }
        return -1;
    }

    /**
     * This product returns a product given its ID.
     *
     * @param productID int   the ID of the product
     * @return Product   the product with the ID passed
     */
    public Product getProduct(int productID) {
        for (Product product : productList) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * This method returns string information about a product given its ID.
     *
     * @param productID int   the ID of the product.
     * @return String   information about the product with the passed ID.
     */
    public String getInfo(int productID) {
        for (Product target : this.productList) {
            if (target.getId() == (productID)) {
                return ("Product info:\n" +
                        "Name:" + target.getName() +
                        "\nID:" + target.getId() +
                        "\nPrice:" + target.getPrice() +
                        "\nStock:" + this.getProductQuantity(target) +
                        "\n");
            }
        }
        return ("Product not found.");
    }

    /**
     * This method removes a specified quantity of the product with the passed product argument from the inventory.
     *
     * @param product  The product to remove
     * @param quantity int   the quantity to be removed
     * @return boolean   true if the removal worked, false if it failed.
     */
    @Override
    public boolean removeProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        for (Product target : productList) {
            if (target.getId() == (product.getId())) {
                if (stockList.get(indexOfProduct(target)) >= quantity) {
                    stockList.set(indexOfProduct(target), stockList.get(indexOfProduct(target)) - quantity);
                    return true;
                }
                //We were not sure what to do when asked to remove an amount bigger than the present stock, so we commented this print statement out for now.
                //System.out.println("Amount to remove can not be greater than current stock.");
                return false;
            }
        }
        return false;
    }

    /**
     * This method returns the contents of the productList field.
     *
     * @return ArrayList<Product>   the productList of this Inventory object.
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    /**
     * This method returns the contents of the stockList field.
     *
     * @return ArrayList<Integer>   the stockList of this Inventory object.
     */
    public ArrayList<Integer> getStockList() {
        return stockList;
    }
}