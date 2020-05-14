package views;

import org.testfx.assertions.api.Assertions;

import java.awt.Label;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import testhelpers.*;
import views.*;

@ExtendWith(ApplicationExtension.class)
class TestCommentView {
	/// Sprint 5 by Amon Otsuki
	///
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;
	BPViewController controller;
	
	@Start
	private void start(Stage stage) throws Exception
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
	
	@Test
	void testCreateComments(FxRobot robot) throws Exception {
		//Test cancel button on the pop up
//		robot.clickOn("#addCommentBtn");
//		robot.clickOn("#addCommentCancelBtn");
		
		String commentContents[] = {"Test comment1", "Test comment2"};
		for (int i=0; i<commentContents.length; i++) {
			//Enter comment and confirm.
			robot.clickOn("#addCommentBtn");
			this.inputFromKeyBoard(robot, commentContents[i]);
			robot.clickOn("#addCommentConfirmBtn");
		};

		//Check the comment output in the comment container.
//		for (int i=0; i<commentContents.length; i++) {
//			this.matchCommentOutput(robot, "#commentUsername-"+Integer.toString(i) , client.getLoginPerson().getUsername(), commentContents[i]);;
//		}
	};

	private void matchCommentOutput(FxRobot robot, String commentGroupId, 
											String username, String commentContent) 
	{
		// TODO Auto-generated method stub
		Assertions.assertThat(robot.lookup("#commentUsername-1").queryAs(Label.class).hasText());
	}
	
	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.generateTestServer();
		this.client = factory.generateTestCliet(this.server);
	};

	private void inputFromKeyBoard(FxRobot robot, String input) {
		robot.write(input);
	}
	
	@BeforeEach
	void initTestModels() {
		System.out.println("RESETTING MODELS");
	}

	
	
}
