package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;


    /** LOGICAL ERROR
     * Found issue when deleting query and the searchbar is empty. The table will be populated with original table
     * and with the search result found before deleting query. Issue was that there was no condition set for what to do
     * if searchbar is empty. In order to fix this a condition was set that when the searchbar is empty the original items
     * will be fetched and then populate the table.
     *
     * @author Livan Martinez
     */
public class MainScreenController implements Initializable {

    /**
     * Parts Table with columns
     */
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partID;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInv;
    @FXML private TableColumn<Part, Double> partPrice;

    /**
     * Product Table with columns
     */
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productID;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Integer> productInv;
    @FXML private TableColumn<Product, Double> productPrice;

    /**
     * Searchbars
     */
    @FXML private TextField searchPart;
    @FXML private TextField searchProduct;

    /**
     * Buttons
     */
    @FXML private Button addPart;
    @FXML private Button modifyPart;
    @FXML private Button deletePart;
    @FXML private Button addProduct;
    @FXML private Button modifyProduct;
    @FXML private Button deleteProduct;
    @FXML private Button exitProgram;


    /***
     * Method to return to Main screen. Will be used on other scenes.
     * @param event
     * @throws IOException
     */
    public static void mainScreen(ActionEvent event) throws IOException{
            Parent addPartParent = FXMLLoader.load(MainScreenController.class.getResource("/view/MainScreen.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
    }

    /**
     * The Add button under the Parts TableView opens the add part form.
     * @param event
     * @throws IOException
     */
    public void pressAddPartButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

        /***
         * The Modify button under the Parts TableView opens modify part form.
         * If no part is selected then an alert appears prompting to select a part.
         * Will determine if selected part is using the In house model or Outsource model.
         *
         * @param event
         * @throws IOException
         */
    public void pressModifyPartButton(ActionEvent event) throws IOException {

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Part");
            alert.showAndWait();
        } if (selectedPart instanceof InHouse) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            ModifyPartController controller = loader.getController();
            controller.partData(partsTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        if (selectedPart instanceof Outsourced){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            ModifyPartController controller = loader.getController();
            controller.partData2(partsTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }

    }

    /***
     * The Add button under the Products TableView opens the add product form.
     *
     * @param event
     * @throws IOException
     */
    public void pressAddProductButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    /**
     * The Modify button under the Products TableView opens modify product form.
     * If no product is selected then an alert appears prompting to select a product.
     *
     * @param event
     * @throws IOException
     */
    public void pressModifyProductButton(ActionEvent event) throws IOException {

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Product");
            alert.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            ModifyProductController controller = loader.getController();
            controller.productData(productTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    /***
     * When pressing the delete button if no item selected, an alert prompting to select a part appears.
     * When pressing the delete button if there is an item selected a confirmation pop up box appears.
     * When confirmed item is deleted.
     *
     * @param actionEvent
     */
    public void pressDeletePart(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Part");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to delete part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }

        }
    }

    /***
     * When pressing delete button if no item selected, an alert prompting to select a product appears.
     * When pressing delete button if button has an associated part then alert will appear.
     * When pressing delete button if selected item contains no associated parts a confirmation pop up box appears.
     * When confirmed item is deleted.
     *
     * @param actionEvent
     */
    public void pressDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Product");
            alert.showAndWait();
        }
        else {
            ObservableList<Part> Aparts = selectedProduct.getAllAssociatedParts();
            if (Aparts.size() <= 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Do you want to delete product?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
            }
                } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Associated Parts");
                alert.setContentText("Must remove associated parts first");
                alert.showAndWait();
            }

        }
    }

    /***
     * When typing name or ID in searchbar the parts table will be populated with found parts.
     * If no part found matching search, then parts table will say no content in table.
     * When the searchbar is empty the original table will be re-populated.
     *
     * @param keyEvent
     */
    public void searchPartEvent(KeyEvent keyEvent) {
        String search = searchPart.getText();
        ObservableList<Part> searchResults = Inventory.lookUpPart(search);
        ObservableList<Part> allParts = Inventory.getAllParts();
        partsTable.setItems(searchResults);
        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(search)) {
                searchResults.add(part);
            }
            if (search.isEmpty()) {
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /***
     * When typing name or ID in searchbar the product table will be populated with found parts.
     * If no product found matching search, then product table will say no content in table.
     * When the searchbar is empty the original table will be re-populated.
     *
     * @param keyEvent
     */
    public void searchProductEvent(KeyEvent keyEvent) {
        String search = searchProduct.getText();
        ObservableList<Product> searchResults = Inventory.lookUpProduct(search);
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        productTable.setItems(searchResults);
        for (Product product : allProducts){
            if (String.valueOf(product.getId()).contains(search)) {
                searchResults.add(product);
            }
            if (search.isEmpty()) {
                productTable.setItems(Inventory.getAllProducts());
            }
        }
    }

    /***
     * The Exit button closes the application.
     * @param event
     */
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) exitProgram.getScene().getWindow();
        stage.close();
    }

    /***
     * Initiates the tableviews.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}