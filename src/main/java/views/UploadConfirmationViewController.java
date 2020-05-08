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

public class UploadConfirmationViewController 
{
	
	BusinessPlan model;
	MyRemoteClient client;
	ConfirmationInterface model2;
	Stage stage;
    @FXML
    private Button closeButton;
	public void setModel(BusinessPlan plan, MyRemoteClient client)
	{
		this.client = client;
		model = plan;

	}
	public void setStage(Stage newstage)
	{
		stage = newstage;
	}
	public void setModel2(ConfirmationInterface model)
	{
		model2 = model;
	}
	//the upload request will be cancelled
    @FXML
    void onClickCancel(ActionEvent event) 
    {
    	if (model2 == null)
    	{
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
    	}
    }
    //current Bp is uploaded to the server
    @FXML
    void onClickConfirm(ActionEvent event) {
    	if(model2 == null)
    	{
    		client.setCurrentBP(model);
    		client.uploadBP();
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
    		model2.confirmation();
    	}
    }





}
