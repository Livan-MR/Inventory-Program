package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;


/** LOGICAL ERROR
 * Originally had one validator for both In house and Outsource options.
 * When using one validator if Outsource is selected the test will still fail since both Machine ID and company name
 * fields were treated as one field causing program to give alert when either a string or Integer is added to
 * company name field. In order to solve issue two validators were created one for In house and one for Outsource.
 *
 * @author Livan Martinez
 */
public class AddPartController {

    /**
     * Radio Button
     */
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outSource;
    @FXML private ToggleGroup sourceType;

    /**
     * TextFields
     */
    @FXML private TextField addId;
    @FXML private TextField addName;
    @FXML private TextField addInv;
    @FXML private TextField addPrice;
    @FXML private TextField addMax;
    @FXML private TextField addMin;
    @FXML private TextField addMachineId;

    /**
     * Buttons
     */
    @FXML private Button savePart;
    @FXML private Button cancelPart;

    /**
     * Label to change from MachineID to Company Name
     */
    @FXML private Label machineId;

    /**
     * When selecting In house radio button the label will display Machine ID
     */
    @FXML void selectInHouse(ActionEvent event) {
        machineId.setText("Machine ID");
    }

    /**
     * When selecting Outsource radio button the label will display Company Name
     */
    @FXML void selectOutsource(ActionEvent event) {
        machineId.setText("Company Name");
    }

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
     * This will test whether the Max field is larger than Min field.
     * Will also test if Inventory is between Min and Max.
     * An alert will display corresponding to the issue that has occurred.
     *
     * @param part
     * @return
     */
    private boolean partValidator(Part part) {
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
     * This will be used when Outsourced radio button is chosen since it will not test for Machine ID value.
     */
    private void typeValidatorOut() {
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
     * This will test to see if Price, Inventory, Min or Max field, and Machine ID has corresponding data type.
     * An alert will display corresponding to the issue that has occurred.
     * This will be used when In house radio button is selected since this will test Machine ID field as well.
     */
    private void typeValidatorIn() {
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
        try {
            int MachineID = Integer.parseInt(addMachineId.getText());
        } catch (NumberFormatException e) {
            Error(6);
        }

    }

    /**
     * Counts the amount of parts in the table. After counting parts will set new ID to number of parts + 1.
     * ID will never repeat since the genID will always be the size of the table plus 1.
     * Decided to change this section after not properly compiling during first attempt. This is more simple and efficient
     * than original attempt.
     * @return
     */
    public int setGenID() {
        int genID = 1;

        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            ++genID;
        }
        return genID;
    }

    /**
     * When pressing save button a series of checks will be run. If any test fails the corresponding alert displays.
     * Test 1: Which radio button is selected In house or Outsource.
     * Test 2: If In house selected check name field for data, if Outsource check Company name for data as well.
     * Test 3: Is the corresponding type value in correct field for price, Inv, Min, Max, or Machine ID if In house part.
     * Test 4: Is the Min field less than Max.
     * If all tests pass then new part will be added to table.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void pressSaveButton(ActionEvent actionEvent) throws IOException {
        String Name = addName.getText();
        double Price = 0;
        int Inv = 0;
        int Min = 0;
        int Max = 0;
        int MachineID = 0;
        String Company = addMachineId.getText();
        if (sourceType.getSelectedToggle().equals(inHouse)) {
            if (Name.isEmpty()) {
                Error(1);
            } else {
                try {
                    Price = Double.parseDouble(addPrice.getText());
                    Inv = Integer.parseInt(addInv.getText());
                    Min = Integer.parseInt(addMin.getText());
                    Max = Integer.parseInt(addMax.getText());
                    MachineID = Integer.parseInt(addMachineId.getText());
                    Part InPart = new InHouse(setGenID(), Name, Price, Inv, Min, Max, MachineID);
                    if (partValidator(InPart)) {
                        Inventory.addPart(InPart);
                        MainScreenController.mainScreen(actionEvent);
                    }
                } catch (Exception e) {
                    typeValidatorIn();
                }
            }
        }
        if (sourceType.getSelectedToggle().equals(outSource)) {
            if (Name.isEmpty()) {
                Error(1);
            } else if (Company.isEmpty()) {
                Error(7);
            } else {
                try {
                    Price = Double.parseDouble(addPrice.getText());
                    Inv = Integer.parseInt(addInv.getText());
                    Min = Integer.parseInt(addMin.getText());
                    Max = Integer.parseInt(addMax.getText());
                    Company = addMachineId.getText();
                    Part OutPart = new Outsourced(setGenID(), Name, Price, Inv, Min, Max, Company);
                    if (partValidator(OutPart)) {
                        Inventory.addPart(OutPart);
                        MainScreenController.mainScreen(actionEvent);
                    }
                } catch (Exception e) {
                    typeValidatorOut();
                }
            }
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
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Company Name");
                alert.setContentText("No data in Company name");
                alert.showAndWait();
                break;
        }
    }
}
