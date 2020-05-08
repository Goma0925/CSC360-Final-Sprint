package main;



import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.*;
import views.ExitController;
import views.LoginController;


public class MainBPView extends Application
{
	static MyRemoteClient client;
	static MyRemoteImpl server;
	

	
	public void setClient(MyRemoteClient newClient)
	{
		client = newClient;
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{


		BusinessPlan plan = new CNTRAssessment();
		Section current = plan.root;
		current.setContent("root");
		plan.addSection(current);
		current.getChildren().get(1).setContent("goal2");;
		current = current.getChildren().get(0);
		current.setContent("goal");
		current.addChild(new Section("Program Goals and Student Learning Objective"));
		current.getChildren().get(0).setContent("objective1");
		current.getChildren().get(1).setContent("objective2");
		plan.setDepartment("CSC");
		plan.setYear("2020");
		//Registry registry = LocateRegistry.createRegistry(1099);
		server = new MyRemoteImpl();
		server.getStoredBP().add(plan);
		
		BusinessPlan plan2 = new CNTRAssessment();
		Section current2 = plan2.root;
		current2.setContent("root");
		plan2.addSection(current2);
		current2.getChildren().get(1).setContent("goal2");;
		current2 = current2.getChildren().get(0);
		current2.setContent("goal");
		current2.addChild(new Section("Program Goals and Student Learning Objective"));
		current2.getChildren().get(0).setContent("objective1");
		current2.getChildren().get(1).setContent("objective2");
		plan2.setDepartment("CSC");
		plan2.setYear("2021");
		plan2.isEditable = false;
		plan2.setEdit("No");
		server.getStoredBP().add(plan2);	
		client = new MyRemoteClient(server);
		server.addPerson("X", "1", "CSC", true);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/loginPage.fxml"));
		
		BorderPane view = loader.load();
		
	
		LoginController cont = loader.getController();
		// ---------Exit View Config------------
		stage.setOnCloseRequest(
				new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						//Stop exiting by event.consume()
		    			FXMLLoader exitViewLoader = new FXMLLoader();
		    			exitViewLoader.setLocation(MainBPView.class.getResource("../views/ExitView.fxml"));
		    			Pane exitView;
						try {
							exitView = exitViewLoader.load();
						} catch (IOException e) {
							exitView = null;
							e.printStackTrace();
						}
						ExitController exitController = exitViewLoader.getController();
						//Create a new stage for a popup window and set the exit view.
		    			Scene exitScene = new Scene(exitView);
		                final Stage dialog = new Stage();
		                dialog.initModality(Modality.APPLICATION_MODAL);
		                dialog.initOwner(stage);
		                dialog.setScene(exitScene);
						exitController.setExitViewStage(dialog);
		                dialog.show();
						event.consume();
					}
		         });
		// ---------Exit View Config------------
		cont.setModel(client);
		Scene s = new Scene(view);
		stage.setScene(s);
		stage.show();
		

	}
	
	public static void main(String[] args)
	{
		launch(args);	
	}
}
