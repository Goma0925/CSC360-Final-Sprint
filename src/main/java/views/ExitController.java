package views;



import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.stage.Stage;
import models.*;

public class ExitController {
	
	Stage exitViewStage;
	BusinessPlan model; 
	Section current;
	MyRemoteClient client;
	ConfirmationInterface model2;
	//set exit page
	public void setExitViewStage(Stage exitViewStage) {
		this.exitViewStage = exitViewStage;
		this.exitViewStage.setOnCloseRequest(e ->{
			this.exitViewStage.close();
		});
	}
	
	public void setModel(BusinessPlan plan, MyRemoteClient client)
	{
		this.client = client;
		model = plan; 
		
	}
	//only for test
	public void setModel2(ConfirmationInterface model)
	{
		model2 = model;
	}
	//close the exit page
	@FXML
    void onCancelQuit(ActionEvent event) {
		if(model2 == null)
		{
		// Close the exit window if the cancel button is pressed.
		this.exitViewStage.close();
		}
		else
		{
			model2.cancel();
		}
    }
	//close the app
    @FXML
    void onConfirmQuit(ActionEvent event) 
    {	
    	if(model2 == null)
    	{
    	//Close the exit window if the quit button is pressed.
		this.exitViewStage.close();

		//-------------------------------------------
    	Platform.exit();
        System.exit(0);
    	}
    	else
    	{
    		model2.confirmation();
    	}
    
    
    }
}
