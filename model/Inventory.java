package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Locale;

/**
 *
 * @author Livan Martinez
 */
public class Inventory {

    /**
     * Create observable list for parts to fill parts Tableview
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Create observable list for products to fill parts Tableview
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part to observable list
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Add part to observable list
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Method to search partId in table
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId) {

        for(int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == partId)
                return part;
        }
        return null;
    }

    /**
     * Method to search productId in table
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = FXCollections.observableArrayList();

        for(int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getId() == productId)
                return p;
        }
        return null;
    }

    /**
     * Method to search part name in table
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part part : allParts) {
            if (part.getName().toLowerCase(Locale.ROOT).contains(partName) || part.getName().contains(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Method to search product name in table
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = FXCollections.observableArrayList();

        for (Product product : getAllProducts()){
            if(product.getName().toLowerCase(Locale.ROOT).contains(productName) || product.getName().contains(productName)){
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Updates part list with modified part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update product list with modified part
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes part in table
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Deletes product in table
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}