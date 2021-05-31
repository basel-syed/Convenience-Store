package Milestone;

/**
 * An interface for a class that contains a Product and its Stock
 * @author Mohamed Selim, Basel Syed
 */
public interface ProductStockContainer {

    /*
      Returns the available quantity of the passed product.
     */
    int getProductQuantity(Product product);
    /*
      Adds the passed Quantity to the current stock of the product.
    */
    void addProductQuantity(Product product, int quantity);
    /*
      Removes the passed quantity from the current stock of the product.
     */
    boolean removeProductQuantity(Product product, int quantity);
    /*
        Returns the total number of Products in the container.
     */
    int getNumOfProducts();
}
