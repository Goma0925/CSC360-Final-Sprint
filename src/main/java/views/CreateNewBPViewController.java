package views;
import java.io.IOException;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.*;

public class CreateNewBPViewController {
	MyRemoteClient client;
	Stage stage;
    @FXML
    private Label departmentLabel;

    @FXML
    private ComboBox<String> ComboBox;

    @FXML
    private TextField yearText;
    
    @FXML
    private Button closeButton;
    ConfirmationInterface model2;
    
    public void setModel(MyRemoteClient client)
    {
    	if(model2 == null)
    	{
    	this.client = client;
    	departmentLabel.textProperty().setValue(client.getLoginPerson().getDepartment().getValue());
    	yearText.textProperty().setValue( Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
    	ComboBox.getItems().addAll( "VMOSA",
		        "CNTRAssessment",
		        "BYB Plan");
    	ComboBox.getSelectionModel().select(0);
    	}
    }
    //only used for test
	public void setLabel(String string) {
		departmentLabel.textProperty().setValue(string);
		
	}
    public void setModel2(ConfirmationInterface model)
    {
    	model2 = model;
    }
    //the window will be closed 
    @FXML
    void onClickCancel(ActionEvent event) {
    	if(model2 == null)
    	{
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/businessPlansByYear.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			SelectorControllor cont = loader.getController();
			cont.setModel(client);
			
			Scene sc = new Scene(pane);
			Stage stage = new Stage();
			stage.setScene(sc);
			cont.setStage(stage);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage2 = (Stage) closeButton.getScene().getWindow();
    	stage2.close();
    	}
    	else 
    	{
    		model2.cancel();
    	}
    }
    //Create new BP and get all the info from the view, then the window will be switch to the edit page. The new BP
    //will not be saved to Server if the user does not upload it.
    @FXML
    void onClickCreate(ActionEvent event) {
    	if(model2 == null) {
    		
    	//get department, year and type
    	String type = ComboBox.getSelectionModel().getSelectedItem();
    	String year = yearText.textProperty().getValue();
    	String department = client.getLoginPerson().getDepartment().getValue();
    	BusinessPlan plan;
    	if(type == "VMOSA")
    	{
    		plan = new VMOSA();
    		plan.setDepartment(department);
    		plan.setYear(year);
    	}
    	else if(type == "CNTRAssessment")
    	{
    		plan = new CNTRAssessment();
    		plan.setDepartment(department);
    		plan.setYear(year);
    	}
    	else 
    	{
    		plan = new BYBPlan();
    		plan.setDepartment(department);
    		plan.setYear(year);
    	}
  		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
    		BPViewController cont = loader.getController();
    		//cont.setModel(client.getCurrentBP());
    		cont.setModel(plan,client);
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
    	else
    	{
    		model2.confirmation();
    	}
    }
	public void setStage(Stage newStage)
	{
		stage = newStage;
	}


}