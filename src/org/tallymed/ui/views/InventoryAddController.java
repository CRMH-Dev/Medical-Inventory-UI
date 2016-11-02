package org.tallymed.ui.views;

import java.io.IOException;
import java.net.URL;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.util.comparator.InvertibleComparator;
import org.springframework.web.client.RestTemplate;
import org.tallymed.service.clientserv.op.DealerOperation;
import org.tallymed.service.clientserv.type.OperationType;
import org.tallymed.ui.util.CommonUtil;
import org.tallymed.ui.views.InventoryController.User;
import org.tallymed.ui.views.forms.InventoryProduct;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class InventoryAddController implements Initializable {

	Map<String, InventoryProduct> inventoryProductMap = null;

	@FXML
	private AnchorPane inventoryPane;

	@FXML
	private JFXComboBox<String> dealer;

	@FXML
	private JFXTextField batchId;

	@FXML
	JFXTextField productName;

	@FXML
	JFXTextField productComposition;

	@FXML
	private JFXTextField mfgCompanyName;

	@FXML
	private JFXTextField mfgShortName;

	@FXML
	private JFXComboBox<String> unitType;

	@FXML
	private JFXTextField mrp;

	@FXML
	private JFXTextField purchasePrice;

	@FXML
	private JFXTextField quantity;

	@FXML
	private JFXTextField mfgDate;

	@FXML
	private JFXTextField expDate;

	@FXML
	private JFXTextField purchaseDate;

	@FXML
	private JFXTextField invoiceID;

	@FXML
	private JFXButton addDealerInvoice;

	@FXML
	private JFXTreeTableView<InventoryProduct> treeView;
	
	@FXML
	private JFXButton saveAllButton;
	
	@FXML
	private JFXButton editButton;
	
	@FXML
	private JFXButton deleteButton;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> dealers = FXCollections.observableArrayList();
		dealers.add("Add New");
		DealerOperation dealerOperation = findDealers();
		for(String dealerName : dealerOperation.getDealersName()){
			dealers.add(dealerName);
		}
		dealer.setItems(dealers);

		ObservableList<String> units = FXCollections.observableArrayList();
		units.add("Add New1");
		unitType.setItems(units);
		if (inventoryProductMap == null || inventoryProductMap.isEmpty()) {
			treeView.setVisible(false);
			saveAllButton.setVisible(false);
			editButton.setVisible(false);
			deleteButton.setVisible(false);
			
		} else {
			treeView.setVisible(true);
			saveAllButton.setVisible(true);
			editButton.setVisible(true);
			deleteButton.setVisible(true);
			refreshTableView();
		}
	}

	private DealerOperation findDealers() {
		DealerOperation dealerOperation = new DealerOperation();
		dealerOperation.setOperationType(OperationType.SEARCHALL);
		final String uri = "http://localhost:8080/dealerOperation";
		RestTemplate restTemplate = new RestTemplate();
		DealerOperation dealerOperations = restTemplate.postForObject(uri, dealerOperation, DealerOperation.class);
		return dealerOperations;
	}

	@SuppressWarnings("unchecked")
	private void refreshTableView() {
		JFXTreeTableColumn<InventoryProduct, String> batchName = new JFXTreeTableColumn<>("Batch ID");
		batchName.setPrefWidth(150);
		batchName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getBatchId();
					}
				});
		JFXTreeTableColumn<InventoryProduct, String> productName = new JFXTreeTableColumn<>("Product Name");
		productName.setPrefWidth(200);
		productName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getProductName();
					}
				});
		JFXTreeTableColumn<InventoryProduct, String> companyShortName = new JFXTreeTableColumn<>("Company Name");
		companyShortName.setPrefWidth(120);
		companyShortName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getMfgShortName();
					}
				});
		JFXTreeTableColumn<InventoryProduct, String> orderQuantityName = new JFXTreeTableColumn<>("Ordered Quantity");
		orderQuantityName.setPrefWidth(120);
		orderQuantityName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getQuantity();
					}
				});
		JFXTreeTableColumn<InventoryProduct, String> mfgName = new JFXTreeTableColumn<>("MFG Date");
		mfgName.setPrefWidth(150);
		mfgName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getMfgDate();
					}
				});
		JFXTreeTableColumn<InventoryProduct, String> expName = new JFXTreeTableColumn<>("Expiry Date");
		expName.setPrefWidth(150);
		expName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<InventoryProduct, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							TreeTableColumn.CellDataFeatures<InventoryProduct, String> param) {
						return param.getValue().getValue().getExpDate();
					}
				});
		if (inventoryProductMap != null && !inventoryProductMap.isEmpty()) {
			ObservableList<InventoryProduct> inventoryProductsObservable = FXCollections.observableArrayList();
			Set<String> batchKeys = inventoryProductMap.keySet();
			for(String key : batchKeys){
				inventoryProductsObservable.add(inventoryProductMap.get(key));
			}
			
			final TreeItem<InventoryProduct> root = new RecursiveTreeItem<InventoryProduct>(inventoryProductsObservable,
					RecursiveTreeObject::getChildren);
			treeView.getColumns().setAll(batchName, productName, companyShortName, orderQuantityName, mfgName, expName);
			treeView.setRoot(root);
			treeView.setShowRoot(false);
		}
	}

	@FXML
	private void handleDealerComboClick() {
		String message = validateDealerInvoice();
		if (message == "") {
			if (dealer.getValue().equalsIgnoreCase("Add New")) {
				try {
					Stage popupStage = new Stage(StageStyle.TRANSPARENT);
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(RootlayoutController.class.getResource("DealerAddPopup.fxml"));
					AnchorPane root = (AnchorPane) loader.load();
					popupStage.initOwner(dealer.getScene().getWindow());
					popupStage.initModality(Modality.WINDOW_MODAL);
					popupStage.setScene(new Scene(root, 330, 300));
					popupStage.show();
					popupStage.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			addDealerInvoice.setDisable(true);
			dealer.setDisable(true);
			invoiceID.setDisable(true);
			purchaseDate.setDisable(true);
			addDealerInvoice.setStyle("-fx-background-color:#777777;");
			inventoryProductMap = new HashMap<String, InventoryProduct>();
		} else {
			CommonUtil.showErrorPopup(null, message, "Please enter the Mandatory fields");
		}
	}

	@FXML
	private void handleAdd() {
		if (inventoryProductMap != null) {
			if (validProduct()) {
				InventoryProduct inventoryProduct = new InventoryProduct(dealer.getValue(), batchId.getText(),
						productName.getText(), productComposition.getText(), mfgCompanyName.getText(),
						mfgShortName.getText(), unitType.getValue(), mrp.getText(), purchasePrice.getText(),
						quantity.getText(), mfgDate.getText(), expDate.getText());
				inventoryProductMap.put(batchId.getText(), inventoryProduct);
				treeView.setVisible(true);
				saveAllButton.setVisible(true);
				editButton.setVisible(true);
				deleteButton.setVisible(true);
				refreshTableView();
			}
		} else {
			CommonUtil.showErrorPopup(null, "Please provide dealer invoice details!!", "An error has been occured!");
		}
	}

	private boolean validProduct() {
		return true;
	}

	private String validateDealerInvoice() {
		String message = "";
		if (dealer == null || dealer.getValue() == null) {
			message += "Dealer selection in Mandatory!!\n";
		}
		if (invoiceID == null || invoiceID.getText() == null || invoiceID.getText().equals("")) {
			message += "Dealer Invoice selection is Mandatory!!\n";
		}
		if (purchaseDate == null || purchaseDate.getText() == null || purchaseDate.getText().equals("")) {
			message += "Purchase date selection is Mandatory!!\n";
		}
		return message;
	}
	
}
