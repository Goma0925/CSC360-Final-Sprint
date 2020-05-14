package manualtests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.MainBPView;
import models.*;
import views.BPViewController;
import views.ExitController;
import views.LoginController;
import testhelpers.*;


public class BPViewManualTester extends Application
{
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;
	
	public static void main(String[] args)
	{
		launch(args);	
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		this.initTestServerAndClient();
		
		FXMLLoader loader = new FXMLLoader();
		System.out.println(this.planFactory.generateTestPlan().toString());
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
    		BPViewController cont = loader.getController();
    		cont.setModel(this.planFactory.generateTestPlan(), this.client);
    		cont.setPane(pane);
    		cont.setStage(stage);
    		Scene sc = new Scene(pane);
    		stage.setScene(sc);
    		stage.show();
		} catch (IOException e) {
			System.out.println("ERROR!!!");
			e.printStackTrace();
		};
	};

	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.generateTestServer();
		this.client = factory.generateTestCliet(this.server);
	};
	
}
