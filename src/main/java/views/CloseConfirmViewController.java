package views;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

import main.MainBPView;
import models.BusinessPlan;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.Section;


public class CloseConfirmViewController {

    @FXML
    private Button closeButton;

    @FXML
    private Button cancelButton;
    Stage stage;
	BusinessPlan model; 
	Section current;
	MyRemoteClient client;
	ConfirmationInterface model2;

	public void setModel(BusinessPlan plan, MyRemoteClient client)
	{
		this.client = client;
		model = plan; 
		
	}

	//set model2 for testing
	public void setModel2(ConfirmationInterface model)
	{
		model2 = model;
	}
	//When the close button is clicked, the user can choose to exit the application
	 @FXML
    void onClickClose(ActionEvent event) {
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
					stage.setScene(sc);
					cont.setStage(stage);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
		
		 else
		 {
			 model2.close();
		 }
    }
	 //if the user cancel the request, the page will back to the view page
    @FXML
    void onClickCancel(ActionEvent event) {

       	if(model2 == null)
    	{
       	System.out.println(cancelButton);
    	Stage stage2 = (Stage) cancelButton.getScene().getWindow();
    	stage2.close();
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			BPViewController cont = loader.getController();
			cont.setModel(model, client);
			cont.setPane(pane);
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	}
    	else
    	{
    		model2.cancel();
    		System.out.println("jjjj");
    	}
    }
	public void setStage(Stage stage) {
		this.stage = stage;
		
	}

}
