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
import views.PreviewController;
import views.SelectorControllor;
import testhelpers.*;


public class BPPreviewManualTester extends Application
{
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;
	BusinessPlan testPlan;
	
	public static void main(String[] args)
	{
		launch(args);	
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		this.initTestServerAndClient();
		this.testPlan = this.planFactory.generateTestPlan();
		
		//Get editing Scene 
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		Scene editingScene = null;
		BorderPane editingPane;
		try {
			editingPane = loader.load();
    		BPViewController cont = loader.getController();
    		cont.setModel(this.testPlan, this.client);
    		cont.setPane(editingPane);
    		cont.setStage(stage);
    		editingScene = new Scene(editingPane);
		} catch (IOException e) {
			System.out.println("ERROR!!!");
			e.printStackTrace();
		};
		
	    //load the preview page
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(MainBPView.class.getResource("../views/PreviewView.fxml"));
		BorderPane previewPane;
		try {
			previewPane = loader2.load();
			System.out.println(previewPane);
			PreviewController cont = loader2.getController();
			cont.setStage(stage);
			cont.setPreviousScene(editingScene);
			cont.setBusinessPlan(this.testPlan);
			Scene sc = new Scene(previewPane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.getTestServer();
		this.client = factory.getTestClient();
	};
	
}
