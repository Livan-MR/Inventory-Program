package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.*;

    /** LOGICAL ERROR
     * When loading modify product form the data from the associated part was not loading in. Instead and empty table
     * appeared. In order to solve issue in the productData method the associated parts from the selected product was
     * fetched and the items were then set to the associated parts table in this form.
     *
     * @author Livan Martinez
     */
public class ModifyProductController implements Initializable {

    /**
     * TextFields
     */
    @FXML private TextField searchPart;
    @FXML private TextField addId;
    @FXML private TextField addName;
    @FXML private TextField addInv;
    @FXML private TextField addPrice;
    @FXML private TextField addMax;
    @FXML private TextField addMin;

    /**
     * Buttons
     */
    @FXML private Button addProduct;
    @FXML private Button removeProduct;
    @FXML private Button saveProduct;
    @FXML private Button cancelProduct;

    /**
     * Parts Table
     */
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partID;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInv;
    @FXML private TableColumn<Part, Double> partPrice;

    /**
     * Associated Part Table
     */
    @FXML private TableView<Part> ApartsTable;
    @FXML private TableColumn<Part, Integer> ApartID;
    @FXML private TableColumn<Part, String> ApartName;
    @FXML private TableColumn<Part, Integer> ApartInv;
    @FXML private TableColumn<Part, Double> ApartPrice;

    /**
     * Observable list for associated parts to be stored
     */
    private ObservableList<Part> Aparts = FXCollections.observableArrayList();

    /**
     * Canceling or exiting this form redirects users to the Main form.
     *
     * @param event
     * @throws IOException
     */
    public void pressCancelButton(ActionEvent event) throws IOException {
        MainScreenController.mainScreen(event);
    }

    /**
     * When typing name or ID items in searchbar the parts table will be populated with found parts.
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

        /**
         * If no part is selected then alert prompted to select a part appears.
         * If item is selected then part will be added to associated part table.
         *
         * @param event
         */
    public void pressAddButton(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Part");
            alert.showAndWait();
        } else {
            Aparts.add(selectedPart);
            ApartsTable.setItems(Aparts);
        }
    }

    /**
     * If no part is selected then alert prompted to select a part appears.
     * If item is selected then message asking to confirm action will appear.
     * After confirmation part is deleted from table.
     *
     * @param event
     */
    public void removePart(ActionEvent event) {
        Part selectedPart = ApartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Part");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to delete part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Aparts.remove(selectedPart);
                ApartsTable.setItems(Aparts);
            }
        }
    }

    /**
     * Data that is passed from Main screen to Modify product form.
     * This will set the corresponding fields with the data from the selected product.
     * Associated parts table is also filled corresponding to the product.
     *
     * @param product
     */
    private Product selectedProduct;
    public void productData (Product product) {
        selectedProduct = product;
        addId.setText(String.valueOf(selectedProduct.getId()));
        addName.setText(selectedProduct.getName());
        addInv.setText(String.valueOf(selectedProduct.getStock()));
        addPrice.setText(String.valueOf(selectedProduct.getPrice()));
        addMax.setText(String.valueOf(selectedProduct.getMax()));
        addMin.setText(String.valueOf(selectedProduct.getMin()));
        Aparts = selectedProduct.getAllAssociatedParts();
        ApartsTable.setItems(Aparts);
    }

    /**
     * When pressing save button a series of checks will be run. If any test fails the corresponding alert displays.
     * Test 1: Check name field for data.
     * Test 2: Is the corresponding type value in correct field for price, Inv, Min and Max.
     * Test 3: Is the Min field less than Max.
     * If all tests pass then updated product will be added to table.
     *
     * @param event
     * @throws IOException
     */
    public void pressSaveButton(ActionEvent event) throws IOException {
        String Name = addName.getText();
        int ID = Integer.parseInt(addId.getText());
        double Price = 0;
        int Inv = 0;
        int Min = 0;
        int Max = 0;

        if (Name.isEmpty()) {
            Error(1);
        } else {
            try {
                Price = Double.parseDouble(addPrice.getText());
                Inv = Integer.parseInt(addInv.getText());
                Min = Integer.parseInt(addMin.getText());
                Max = Integer.parseInt(addMax.getText());
                Product newProd = new Product(ID, Name, Price, Inv, Min, Max);
                for (Part part : Aparts) {
                    newProd.addAssociatedPart(part);
                } if (partValidator(newProd)) {
                    Inventory.deleteProduct(selectedProduct);
                    Inventory.addProduct(newProd);
                    MainScreenController.mainScreen(event);
                }
            } catch (Exception e) {
                typeValidator();
            }

        }
    }

        /**
         * This will test whether the Max field is larger than Min field.
         * Will also test if Inventory is between Min and Max
         * An alert will display corresponding to the issue that has occurred.
         *
         * @param part
         * @return
         */
    private boolean partValidator(Product part) {
        String Name = addName.getText();
        int Max = Integer.parseInt(addMax.getText());
        int Min = Integer.parseInt(addMin.getText());
        int Inv = Integer.parseInt(addInv.getText());

        if (Min > Max) {
            Error(4);
            return false;
        } else if (Inv < Min || Inv > Max) {
            Error(2);
            return false;
        }
        return true;
    }

    /**
     * This will test to see if Price, Inventory, Min or Max field has corresponding data type.
     * An alert will display corresponding to the issue that has occurred.
     */
    private void typeValidator() {
        try {
            double Price = Double.parseDouble(addPrice.getText());
        } catch (NumberFormatException e) {
            Error(3);
        }
        try {
            int Inv = Integer.parseInt(addInv.getText());
        } catch (NumberFormatException e) {
            Error(2);
        }
        try {
            int Min = Integer.parseInt(addMin.getText());
        } catch (NumberFormatException e) {
            Error(4);
        }
        try {
            int Max = Integer.parseInt(addMax.getText());
        } catch (NumberFormatException e) {
            Error(5);
        }
    }

        /**
         * The corresponding Alerts for each situation.
         *
         * @param Error
         */
    private void Error(int Error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (Error) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Name");
                alert.setContentText("No data in Name");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Inventory");
                alert.setContentText("Inv should be a number greater than Min and less than Max");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Price");
                alert.setContentText("Price is not a double");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Min");
                alert.setContentText("Min should be a number greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Max");
                alert.setContentText("Max should be a number greater than 0 and greater than Min");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Machine ID");
                alert.setContentText("Machine ID is not an integer");
                alert.showAndWait();
                break;
        }
    }

    //

        /**
         * Initiates both tableviews
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

        ApartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ApartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ApartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ApartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
