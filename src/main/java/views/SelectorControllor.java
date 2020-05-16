package views;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import main.MainBPView;
import models.BusinessPlan;
import models.ConfirmationInterface;
import models.MyRemoteClient;

public class SelectorControllor {
	// model 2 is only used for test
	BusinessPlan plan;
	MyRemoteClient client;
	Stage stage;
	ConfirmationInterface model2;
	private String mode = "EDIT";
	// All the table columns
	@FXML
	private TableView<BusinessPlan> tableView;
	@FXML
	private TableColumn<BusinessPlan, String> year;
	@FXML
	private TableColumn<BusinessPlan, String> department;
	@FXML
	private TableColumn<BusinessPlan, String> Editability;
	@FXML
	private TableColumn<BusinessPlan, String> Type;
	@FXML
	private Button modeSwitchBtn;
	@FXML
	private Label modeLabel;
	@FXML
	private Label instructionLabel;
	public ComparisonViewController comparisonController; // For testing

	// this is called when the user click the create new business plan button and
	// then a new window will pop up
	@FXML
	void createNewBP(ActionEvent event) {
		if (model2 == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainBPView.class.getResource("../views/CreateNewBPView.fxml"));
			BorderPane pane;
			try {
				pane = loader.load();
				CreateNewBPViewController cont = loader.getController();
				cont.setModel(client);
				cont.setStage(stage);
				Scene sc = new Scene(pane);
				stage.setScene(sc);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // for testing
			model2.confirmation();
		}

	}

	public void setStage(Stage newStage) { // pass stage between different views
		stage = newStage;
	}

	public void setModel(MyRemoteClient client) {
		if (model2 == null)// avoid null pointer exception
		{
			this.client = client;
			createTable();
		}

	}

	// called when the user selects a BP and then tries to view it.
	@FXML
	void onClickView(ActionEvent event) {
		if (this.mode.equals("EDIT")) {
			this.switchToEditView();
		} else {
			this.switchToComparisonView();
		}
	}

	private void switchToEditView() {
		if (model2 == null) {
			try {
				// get selected BP
				BusinessPlan current = tableView.getSelectionModel().getSelectedItem();
				// pop up editable page
				if (current.isEditable) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
					BorderPane pane;
					try {
						pane = loader.load();
						BPViewController cont = loader.getController();
						// cont.setModel(client.getCurrentBP());
						cont.setModel(current, client);
						cont.setPane(pane);
						cont.setStage(stage);
						Scene sc = new Scene(pane);
						stage.setScene(sc);
						stage.show();
					} catch (IOException e) {

						e.printStackTrace();
					}

				}
				// pop up noneditable page
				else {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainBPView.class.getResource("../views/NonEditableView.fxml"));
					BorderPane pane;
					try {
						pane = loader.load();
						NonEditableViewController cont = loader.getController();
						// cont.setModel(client.getCurrentBP());
						cont.setModel(current, client);
						cont.setPane(pane);
						cont.setStage(stage);
						Scene sc = new Scene(pane);
						stage.setScene(sc);
						stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}
			} catch (Exception e) {
				System.out.println("Please Select a Business Plan");
			}
		} else {
			model2.cancel();
		}

	};

	private void switchToComparisonView() {
		ObservableList<BusinessPlan> selectedPlans = tableView.getSelectionModel().getSelectedItems();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/ComparisonView.fxml"));
		try {
			Pane pane = loader.load();
			ComparisonViewController cont = loader.getController();
			// cont.setModel(client.getCurrentBP());
			cont.setModels(selectedPlans.get(0), selectedPlans.get(1));
			cont.setClient(this.client);
			cont.setStage(this.stage);
			this.comparisonController = cont;
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickCompare(ActionEvent event) {
		String comparisonModeName = "Comparison Mode";
		String editingModeName = "Editing Mode";
		if (this.mode.equals("EDIT")) {
			// Switch to Comparison mode
			System.out.println("SWITCHING TO Comparison");
			this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			this.modeSwitchBtn.setText(editingModeName);
			this.modeLabel.setText(comparisonModeName);
			this.instructionLabel.setText("Select two business plans to compare (Press command + click)");
			this.mode = "COMPARE";
		} else {
			// Switch to Editing mode
			this.tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			this.modeSwitchBtn.setText(comparisonModeName);
			this.modeLabel.setText(editingModeName);
			this.instructionLabel.setText("Select a business plan to edit");
			this.mode = "EDIT";
		}
	}

	private void onClickTable() {
		//Make sure the maximum 2 plans are selected.
		ObservableList<BusinessPlan> selectedPlans = this.tableView.getSelectionModel().getSelectedItems();
		if (selectedPlans.size() >= 3) {
			//If you can't select anymore, clear everything.
			this.tableView.getSelectionModel().clearSelection();
		}		
	}

	// create table of BPs
	public void createTable() {
		year.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("year"));
		department.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("department"));
		Editability.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("edit"));
		Type.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("type"));
		ArrayList<BusinessPlan> plans = client.getServer().getStoredBP();
		ObservableList<BusinessPlan> newPlans = FXCollections.observableArrayList();
		for (int i = 0; i < plans.size(); i++) {
			newPlans.add(plans.get(i));
		}
		this.tableView.setItems(newPlans);

		this.tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onClickTable();
			}
		});
		this.tableView.setId("tableView");
	}

	// set model2 for test
	public void setModel2(ConfirmationInterface model2) {
		this.model2 = model2;

	};

}
