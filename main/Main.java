package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;


    /** FUTURE ENHANCEMENT
     * A practical enhancement could be a better search bar. The searchbar works great for a few items. An issue however
     * can come if there are thousands of items. A way to solve this can be adding features to the searchbar such as am
     * autofill or a spell check feature such as Google's "Did you mean feature". This can make it so items with similar
     * or difficult to spell names can be searched for when looking through over thousands of items.
     *
     * @author Livan Martinez
     *
     * Javadoc folder located in src/javadoc
     * */
public class Main extends Application {

    /**
     * Loads Main Screen upon program startup
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /***
     * Loads test data for product table and parts table
     * Launches program
     * @param args
     */
    public static void main(String[] args) {

        Inventory.addProduct(new Product(1,"Giant Bike",299.99,5,1,20));
        Inventory.addProduct(new Product(2,"Tricycle",99.99,3,1,20));

        Inventory.addPart(new InHouse(1,"Brakes",15.00,10,1,20,1));
        Inventory.addPart(new InHouse(2,"Wheel",11.00,16,1,20,2));
        Inventory.addPart(new InHouse(3,"Seat",15.00,10,1,20,3));

        launch();
    }
}
