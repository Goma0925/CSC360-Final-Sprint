package views;

import java.io.IOException;


import java.rmi.registry.Registry;




import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.ConfirmationInterface;
import models.MyRemoteClient;

public class LoginController {
	
	MyRemoteClient client;
	Registry registry;
	ConfirmationInterface model2;
	public void setModel(MyRemoteClient newClient)
	{
		client = newClient;
	}
	
	public void setModel2(ConfirmationInterface model) 
	{
		model2 = model;
	}
	@FXML
    private ChoiceBox<String> server;
	
    @FXML
    private TextField username;
    @FXML
    private Label serverLabel;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    @FXML
    void onClickLogin(ActionEvent event) {
    	//Create the primary stage
    	//authenticate
    	//model2 is only used for test
    	if(model2 == null) 
    	{
    	boolean loggedIn = client.askForLogin(username.textProperty().get(), password.textProperty().get());
		if(loggedIn)
		{	
	    Stage stage = (Stage) loginButton.getScene().getWindow();
	    //load the slection page of Business Plan
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/businessPlansByYear.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			System.out.println(pane);
			SelectorControllor cont = loader.getController();
			cont.setModel(client);
			cont.setStage(stage);
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}}

   	}
    	else
    	{
    		model2.confirmation();
    	}

    }
}

