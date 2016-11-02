package org.tallymed.ui.views;

import java.io.IOException;

import org.tallymed.ui.MainApplication;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootlayoutController {
	private static BorderPane rootLayout;
	
	
	/**
     * Initializes the root layout.
     */
    public void initRootLayout(Stage loginStage) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootlayoutController.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            Image img = new Image("file:resources/img/logo.png");
            Stage mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.initStyle(StageStyle.DECORATED); 
            mainStage.setTitle("..: tallyMED :: Home :..");
            mainStage.getIcons().add(img);
            mainStage.setMaximized(true);
            loginStage.close();
            mainStage.setOnHidden(event -> Platform.exit());
            mainStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showHomeLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootlayoutController.class.getResource("Inventory.fxml"));
            AnchorPane homeInitiate = (AnchorPane) loader.load();
            rootLayout.setCenter(homeInitiate);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@FXML
	private void handleClose(){
		Platform.exit();
		System.exit(0);
	}
	@FXML
	private void handleInventoryManageMenu(){
		
	}
	@FXML
	private void handleAddInventory(){
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootlayoutController.class.getResource("InventoryAdd.fxml"));
            AnchorPane homeInitiate = (AnchorPane) loader.load();
            rootLayout.setCenter(homeInitiate);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
