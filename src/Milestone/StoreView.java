package Milestone;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Mohamed Selim, Basel Syed
 */

public class StoreView {
    /**
     * Cart Identification for the Milestone.StoreView Object
     */
    private final int cartID;
    /**
     * Milestone.StoreManager for the StoreView object
     */
    private final StoreManager storeManager;

    /**
     * Constructor for Milestone.StoreView
     *
     * @param storeManager of type Milestone.StoreManager, represents the store manager of the new store.
     * @param cartID       Gives a new cartID in the store
     */
    public StoreView(StoreManager storeManager, int cartID) {
        this.cartID = cartID;
        this.storeManager = storeManager;
    }

    /**
     * This method takes a directory and returns an ArrayList that includes all the image files with extensions (jpg, png and jpeg)
     * in the main directory of that folder (doesn't go into subdirectories).
     *
     * @param directory File   the folder to be searched for images
     * @return results   ArrayList<BufferedImage>   an ArrayList of the images in the passed directory
     * @throws IOException if any issues occur with processing the input
     */
    private ArrayList<BufferedImage> getImages(File directory) throws IOException {
        File[] files = directory.listFiles();
        ArrayList<BufferedImage> results = new ArrayList<>();
        assert files != null;
        for (File f : files) {
            if ((f != null) && ((f.getName().toLowerCase().endsWith(".jpg")) ||
                    (f.getName().toLowerCase().endsWith(".png")) ||
                    (f.getName().toLowerCase().endsWith("jpeg")))) {
                results.add(ImageIO.read(new File(f.getCanonicalPath())));
            }
        }
        return results;
    }

    /**
     * This method sets up the main window of the GUI.
     *
     * @throws IOException if any errors occur with the call to getImages()
     */
    public void initializeGUI() throws IOException {
        JFrame mainWindow = new JFrame();
        JPanel enclosingPanel = new JPanel(new GridLayout(7, 0));
        mainWindow.setTitle("Store");
        //Setup the components of the window
        JPanel options = new JPanel(new BorderLayout());
        JPanel itemList = new JPanel(new GridLayout(0, 2));
        JScrollPane scrollBar = new JScrollPane(itemList);
        scrollBar.getVerticalScrollBar().setUnitIncrement(12);
        //Setup Images
        File imageFolder = new File("ProductImages");
        ArrayList<BufferedImage> images = getImages(imageFolder);
        //Number of products in the inventory
        int NUMBER_OF_PRODUCT_TYPES = storeManager.getProductList().size();
        //Set Sizes if needed:
        //Setup itemList:
        JPanel[] itemArray = new JPanel[NUMBER_OF_PRODUCT_TYPES];
        JLabel[] productName = new JLabel[NUMBER_OF_PRODUCT_TYPES];
        JLabel[] productInfo = new JLabel[NUMBER_OF_PRODUCT_TYPES];
        JButton[] addItemButtons = new JButton[NUMBER_OF_PRODUCT_TYPES];
        JButton[] removeItemButtons = new JButton[NUMBER_OF_PRODUCT_TYPES];
        for (int i = 0; i < NUMBER_OF_PRODUCT_TYPES; i++) {
            itemArray[i] = new JPanel(new GridBagLayout());
            Border border = BorderFactory.createLineBorder(Color.black);
            itemArray[i].setBorder(border);
        }
        for (int i = 0; i < NUMBER_OF_PRODUCT_TYPES; i++) {
            productName[i] = new JLabel(storeManager.getProductList().get(i).getName());
            productName[i].setPreferredSize(new Dimension(200, 50));
            productInfo[i] = new JLabel(getProductInfo(i));
            addItemButtons[i] = new JButton("+");
            if (getProductInfo(i).endsWith("Out of Stock")) {
                addItemButtons[i].setEnabled(false);
            }
            int finalI = i;
            addItemButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (storeManager.getStock(storeManager.getProductList().get(finalI)) == 1) {
                        storeManager.addToCart(cartID, finalI, 1);
                        removeItemButtons[finalI].setEnabled(true);
                        productInfo[finalI].setText(productInfo[finalI].getText().substring(0, productInfo[finalI].getText().length() - 2));
                        productInfo[finalI].setText(productInfo[finalI].getText() + ":0");
                        addItemButtons[finalI].setEnabled(false);
                        return;
                    }
                    storeManager.addToCart(cartID, finalI, 1);
                    productInfo[finalI].setText(getProductInfo(finalI));
                    removeItemButtons[finalI].setEnabled(true);
                }
            });
            removeItemButtons[i] = new JButton("-");
            removeItemButtons[i].setEnabled(false);
            int finalI1 = i;
            removeItemButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (storeManager.removeFromCart(cartID, finalI1, 1)) {
                        productInfo[finalI].setText(getProductInfo(finalI));
                        addItemButtons[finalI].setEnabled(true);
                        //Check if the product can still be taken from
                        if (storeManager.removeFromCart(cartID, finalI1, 1)) {
                            storeManager.addToCart(cartID, finalI1, 1);
                            return;
                        }
                        removeItemButtons[finalI1].setEnabled(false);
                    }

                }
            });
            //Constraints:
            GridBagConstraints namePosition = new GridBagConstraints();
            namePosition.anchor = GridBagConstraints.NORTHWEST;
            namePosition.weightx = 1;
            namePosition.gridx = 0;
            namePosition.gridy = 0;
            GridBagConstraints infoPosition = new GridBagConstraints();
            infoPosition.anchor = GridBagConstraints.NORTHWEST;
            infoPosition.weightx = 0.33;
            infoPosition.gridx = 0;
            infoPosition.gridy = 1;
            GridBagConstraints addButtonPosition = new GridBagConstraints();
            addButtonPosition.anchor = GridBagConstraints.LAST_LINE_START;
            addButtonPosition.fill = GridBagConstraints.HORIZONTAL;
            addButtonPosition.weightx = 1;
            addButtonPosition.gridx = 1;
            addButtonPosition.gridy = 3;
            GridBagConstraints removeButtonPosition = new GridBagConstraints();
            removeButtonPosition.anchor = GridBagConstraints.LAST_LINE_START;
            removeButtonPosition.fill = GridBagConstraints.HORIZONTAL;
            removeButtonPosition.weightx = 1;
            removeButtonPosition.gridx = 2;
            removeButtonPosition.gridy = 3;
            //Add the components
            itemArray[i].add(productName[i], namePosition);
            itemArray[i].add(productInfo[i], infoPosition);
            itemArray[i].add(addItemButtons[i], addButtonPosition);
            itemArray[i].add(removeItemButtons[i], removeButtonPosition);
            itemArray[i].setPreferredSize(new Dimension(280, 350));
            itemList.add(itemArray[i]);
        }
        //Add the images. Products with no images will have an empty space instead.
        GridBagConstraints picPosition = new GridBagConstraints();
        picPosition.gridwidth = 3;
        picPosition.gridx = 0;
        picPosition.gridy = 2;
        for (BufferedImage img : images) {
            JLabel productPic = new JLabel(new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_FAST)));
            itemArray[images.indexOf(img)].add(productPic, picPosition);
        }
        //Setup the options panel:
        JButton viewCart = new JButton("View Cart");
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart(mainWindow);
            }
        });
        JButton checkout = new JButton("Checkout");
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout(mainWindow);
            }
        });
        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    mainWindow.setVisible(false);
                    mainWindow.dispose();
                }
            }
        });
        //Options components add
        options.add(viewCart, BorderLayout.NORTH);
        options.add(checkout, BorderLayout.WEST);
        options.add(help, BorderLayout.EAST);
        options.add(quit, BorderLayout.SOUTH);
        Border optionsBorder = BorderFactory.createLineBorder(Color.black);
        options.setBorder(optionsBorder);
        //Setup enclosedPanel (will include the options panel and a logo.
        BufferedImage titleImg = ImageIO.read(new File("otherImages/Title.jpg"));
        enclosingPanel.add(new JLabel(new ImageIcon(titleImg.getScaledInstance(150, 150, Image.SCALE_FAST))));
        enclosingPanel.add(options, 1);
        //pack
        mainWindow.add(scrollBar, BorderLayout.LINE_START);
        mainWindow.add(enclosingPanel, BorderLayout.LINE_END);
        mainWindow.setLocation(500, 0);
        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    mainWindow.setVisible(false);
                    mainWindow.dispose();
                }
            }
        });

    }

    /**
     * Get the cartID
     *
     * @return Returns an int representing the cartID
     */
    public int getCartID() {
        return cartID;
    }

    /**
     * This method returns information about the product in the productList of the Inventory associated with the StoreManager of this StoreView
     * with the passed index.
     * The returned String has the format:
     * Price: ($price) - Stock:amount
     * Where   price -> price of each unit of the product
     * amount -> the number of units of the product available in the inventory
     *
     * @param productIndex int   the index of the intended product in productsList of the Inventory associated with the StoreManager field.
     * @return str   String   String with information about the product
     */
    private String getProductInfo(int productIndex) {
        String price = String.format("%.2f", storeManager.getProductList().get(productIndex).getPrice());
        if (storeManager.getStockList().get(productIndex) == 0) {
            return "Price: ($" + price + ") - Out of Stock";
        }
        return "Price: ($" + price + ") | Stock:"
                + storeManager.getStockList().get(productIndex);
    }

    /**
     * Find the total of the users shopping cart
     *
     * @param owner JFrame   the JFrame that will be the owner of the dialogue
     */
    public void checkout(JFrame owner) {
        JDialog checkoutDialog = new JDialog(owner, true);
        checkoutDialog.setLocationRelativeTo(null);
        checkoutDialog.setTitle("Checkout");
        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton noButton = new JButton("Go back to store");
        JButton yesButton = new JButton("Checkout");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutDialog.dispose();
            }
        });
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);
        ShoppingCart currentCart = storeManager.getShoppingCarts().get(cartID);
        StringBuilder receipt = new StringBuilder("<html><div style = 'text-align: justify;'>");
        StringBuilder s = new StringBuilder();

        for (Product product : currentCart.getProducts()) {
            String price = String.format("%.2f", product.getPrice());
            receipt.append("Product: ").append(product.getName()).append(" Quantity: ").append(currentCart.getProductQuantity(product)).append(" Price: $").append(price).append("<br/>");

        }

        receipt.append(" </div></html>");
        s.append(String.format("Your total is: $%.2f", storeManager.checkout(cartID)));
        String price = s.toString();
        JLabel itemsPurchased = new JLabel(receipt + "</div></html>");
        JLabel itemsTotal = new JLabel(price);


        borderPanel.add(itemsPurchased, BorderLayout.NORTH);
        borderPanel.add(itemsTotal, BorderLayout.CENTER);
        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
        checkoutDialog.add(borderPanel);
        checkoutDialog.pack();
        checkoutDialog.setPreferredSize(new Dimension(500, 200));
        checkoutDialog.setVisible(true);
    }

    /**
     * Show the user a window with help on how to use the application.
     */
    public void help() {
        JFrame helpWindow = new JFrame("Help");
        JPanel gridPanel = new JPanel(new GridLayout(5, 1));
        JLabel plusMinus = new JLabel("<html> <h1> +/- Buttons</h1> These buttons are to add or remove items from your cart.</html");
        JLabel viewCart = new JLabel("<html> <h1>View Cart</h1> This button allows you to see what items you have in your cart.</html>");
        JLabel checkout = new JLabel("<html> <h1>Checkout</h1> This button allows you to see your total and checkout the items in your cart." +
                " Checking out will close the application.</html>");
        JLabel helpLabel = new JLabel("<html> <h1>Help</h1> Lets you see all available functions.</html>");
        JButton okButton = new JButton("Got it!");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpWindow.dispose();
            }
        });
        gridPanel.add(plusMinus);
        gridPanel.add(viewCart);
        gridPanel.add(checkout);
        gridPanel.add(helpLabel);
        gridPanel.add(okButton);

        helpWindow.add(gridPanel);
        helpWindow.pack();
        helpWindow.setVisible(true);

    }

    /**
     * This method shows a dialogue with the current contents of the User's cart
     *
     * @param owner JFrame   the JFrame that will be the owner of the dialogue
     */
    public void viewCart(JFrame owner) {
        ShoppingCart cart = storeManager.getShoppingCarts().get(cartID);
        JDialog viewCartDialog = new JDialog(owner, true);
        viewCartDialog.setLocationRelativeTo(null);
        viewCartDialog.setTitle("Your Cart");
        JPanel itemListPanel = new JPanel();
        StringBuilder productList = new StringBuilder("<html><div style = 'text-align: justify;'>");
        for (Product product : cart.getProducts()) {
            String price = String.format("%.2f", product.getPrice());
            productList.append("Product: ").append(product.getName()).append(" Quantity: ")
                    .append(cart.getProductQuantity(product)).append(" Price: $").append(price).append("<br/>");

        }

        productList.append(" </div></html>");
        JLabel itemList = new JLabel(productList.toString());
        itemListPanel.add(itemList);
        //pack
        viewCartDialog.add(itemListPanel);
        viewCartDialog.pack();
        viewCartDialog.setPreferredSize(new Dimension(500, 200));

        viewCartDialog.setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm, sm.assignNewCartID());
        sv1.initializeGUI();

    }
}
