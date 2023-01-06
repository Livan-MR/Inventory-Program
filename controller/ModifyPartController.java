package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.InHouse;

import java.io.IOException;


    /** LOGICAL ERROR
     * Originally when saving, the updated part would be added to table but the original non modified part would
     * also be in the table. In order to solve issue the original selected part will be deleted then the updated
     * part will be added to table.
     *
     * @author Livan Martinez
     */
public class ModifyPartController {

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
     * Label to change from MachineID to CompanyName
     */
    @FXML private Label machineId;

    /**
     * When selecting In house radio button the label will display Machine ID
     *
     * @param event
     */
    @FXML void selectInHouse(ActionEvent event) {
        machineId.setText("Machine ID");
    }

    /**
     *When selecting Outsource radio button the label will display Company Name
     *
     * @param event
     */
    @FXML void selectOutsource(ActionEvent event) {
        machineId.setText("Company Name");
    }

    /**
     * Canceling or exiting this form redirects users to the Main form
     * @param event
     * @throws IOException
     */
    public void pressCancelButton(ActionEvent event) throws IOException {
        MainScreenController.mainScreen(event);
    }

    /**
     * Data that is passed from Main screen to Modify part form.
     * This will set the corresponding fields with the data from the selected part
     * If incoming data is In house model partData method used
     * If incoming data is Out source model partData2 method used
     * @param part
     */
    private InHouse selectedPart;
    private Outsourced selectedPart2;
    public void partData2(Part part) {
        outSource.setSelected(true);
        machineId.setText("Company Name");
        selectedPart2 = (Outsourced) part;
        addId.setText(String.valueOf(selectedPart2.getId()));
        addName.setText(selectedPart2.getName());
        addInv.setText(String.valueOf(selectedPart2.getStock()));
        addPrice.setText(String.valueOf(selectedPart2.getPrice()));
        addMax.setText(String.valueOf(selectedPart2.getMax()));
        addMin.setText(String.valueOf(selectedPart2.getMin()));
        addMachineId.setText(selectedPart2.getCompanyName());
        }

    public void partData(Part part) {
        inHouse.setSelected(true);
        selectedPart = (InHouse) part;
        addId.setText(String.valueOf(selectedPart.getId()));
        addName.setText(selectedPart.getName());
        addInv.setText(String.valueOf(selectedPart.getStock()));
        addPrice.setText(String.valueOf(selectedPart.getPrice()));
        addMax.setText(String.valueOf(selectedPart.getMax()));
        addMin.setText(String.valueOf(selectedPart.getMin()));
        addMachineId.setText(String.valueOf(selectedPart.getMachineId()));
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
     * When pressing save button a series of checks will be run. If any test fails the corresponding alert displays.
     * Test 1: Which radio button is selected In house or Outsource.
     * Test 2: If In house selected  check name field for data, if Outsource check Company name also for data.
     * Test 3: Is the corresponding type value in correct field for price, Inv, Min, Max, or Machine ID if In house part.
     * Test 4: Is the Min field less than Max.
     * If all tests pass then the modified part will be updated to table.
     *
     * @param event
     * @throws IOException
     */
    public void pressSaveButton(ActionEvent event) throws IOException {
        int ID;
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
                    ID = Integer.parseInt(addId.getText());
                    Price = Double.parseDouble(addPrice.getText());
                    Inv = Integer.parseInt(addInv.getText());
                    Min = Integer.parseInt(addMin.getText());
                    Max = Integer.parseInt(addMax.getText());
                    MachineID = Integer.parseInt(addMachineId.getText());
                    Part InPart = new InHouse(ID, Name, Price, Inv, Min, Max, MachineID);
                    if (partValidator(InPart)) {
                        Inventory.deletePart(selectedPart2);
                        Inventory.deletePart(selectedPart);
                        Inventory.addPart(InPart);
                        MainScreenController.mainScreen(event);
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
                    ID = Integer.parseInt(addId.getText());
                    Price = Double.parseDouble(addPrice.getText());
                    Inv = Integer.parseInt(addInv.getText());
                    Min = Integer.parseInt(addMin.getText());
                    Max = Integer.parseInt(addMax.getText());
                    Company = addMachineId.getText();
                    Part OutPart = new Outsourced(ID, Name, Price, Inv, Min, Max, Company);
                    if (partValidator(OutPart)) {
                        Inventory.deletePart(selectedPart2);
                        Inventory.deletePart(selectedPart);
                        Inventory.addPart(OutPart);
                        MainScreenController.mainScreen(event);
                    }
                } catch (Exception e) {
                    typeValidatorOut();
                }
            }
        }
    }

    /**
     * The corresponding Alerts for each situation.
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
                alert.setContentText("No data in Comapany Name");
                alert.showAndWait();
                break;
        }
    }
}
