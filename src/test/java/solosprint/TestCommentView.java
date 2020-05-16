package solosprint;

import org.testfx.assertions.api.Assertions;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import main.MainBPView;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import testhelpers.*;
import views.BPViewController;

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
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
    		this.controller = loader.getController();
    		this.controller.setModel(this.planFactory.generateTestPlan(), this.client);
    		this.controller.setPane(pane);
    		this.controller.setStage(stage);
    		Scene sc = new Scene(pane);
    		stage.setScene(sc);
    		stage.show();
		} catch (IOException e) {
			System.out.println("ERROR!!!");
			e.printStackTrace();
		};
	};
	
	
	@Test
	void testCreateAndRemoveComments(FxRobot robot) throws Exception {
		String commentContents[] = {"Comment at 0", "Comment at 1", "Comment at 2", "Comment at 3"};
		
		LinkedList<String> remainingComments = new LinkedList<String>();
		
		//Select the first item in the tree.
		TreeView<Section> treeView = this.controller.getTreeView();
		treeView.getSelectionModel().select(0);
		robot.clickOn("#selectButton");
		
		//Test cancel button on the pop up
		robot.clickOn("#addCommentBtn");
		robot.clickOn("#addCommentCancelBtn");
		
		//Add comments
		for (int i=0; i<commentContents.length; i++) {
			//Enter comment and confirm.
			robot.clickOn("#addCommentBtn");
			this.inputFromKeyBoard(robot, commentContents[i]);
			robot.clickOn("#addCommentConfirmBtn");
			remainingComments.add(i, commentContents[i]);
		};
		
		//
		
		//Check the comment output in the comment container.
		for (int i=0; i<commentContents.length; i++) {
			this.checkCommentOutput(robot, client.getLoginPerson().getUsername(), commentContents[i], i);;
		}
		
		//Try removing commnets twice
		//1. Remove the third comment
		System.out.println("Removed comment at:"+ 2);
		this.removeComment(robot, 2);
		remainingComments.remove(2);
		System.out.println("Expected remaining set:"+ remainingComments.toString());
		for (int i=0; i<remainingComments.size(); i++) {
			this.checkCommentOutput(robot, client.getLoginPerson().getUsername(), remainingComments.get(i), i);;
		};
		
		//2. Remove the first comment out of 3.
		System.out.println("Removed comment at:"+ 0);
		this.removeComment(robot, 0);
		remainingComments.remove(0);
		System.out.println("Expected remaining set:"+ remainingComments.toString());
		for (int i=0; i<remainingComments.size(); i++) {
			this.checkCommentOutput(robot, client.getLoginPerson().getUsername(), remainingComments.get(i), i);;
		};
		
		//3. Remove the last comment out of 2.
		System.out.println("Removed comment at:"+ 1);
		this.removeComment(robot, 1);
		remainingComments.remove(1);
		System.out.println("Expected remaining set:"+ remainingComments.toString());
		for (int i=0; i<remainingComments.size(); i++) {
			this.checkCommentOutput(robot, client.getLoginPerson().getUsername(), remainingComments.get(i), i);;
		};
		
		//3. Remove the last comment.
		this.removeComment(robot, 0);
		remainingComments.remove(0);
		this.checkAllCommentsAreDeleted(robot);
	};

	private void checkCommentOutput(FxRobot robot, String username, String commentContent, int index) 
	{
		System.out.println("	Expect: '" + commentContent + "' at index " + index);
		System.out.println("	Got   : '" + ((Text) robot.lookup("#commentTextFlow-"+Integer.toString(index))
				.queryAs(TextFlow.class).getChildren().get(0)).getText() + "' at index " + index);
		//Check if the author label in the comment is correct.
		Assertions.assertThat(robot.lookup("#authorLabel-"+Integer.toString(index))
								.queryAs(Label.class)).hasText(username);
		
		//Check if the comment content in the comment is correct.
		Assertions.assertThat(((Text) robot.lookup("#commentTextFlow-"+Integer.toString(index))
				.queryAs(TextFlow.class).getChildren().get(0))).hasText(commentContent);
		
	}
	
	private void removeComment(FxRobot robot, int index) {
		Button removeButton = robot.lookup("#removeCommentBtn-"+Integer.toString(index))
											.queryAs(Button.class);
		robot.clickOn(removeButton);
	};
	
	private void checkAllCommentsAreDeleted(FxRobot robot) {
		VBox commentContainer = robot.lookup("#commentContainer").queryAs(VBox.class);
		//Check no elements exist in the commentContainer
		Assertions.assertThat(commentContainer.getChildren().size()==0);
	}
	
	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.getTestServer();
		this.client = factory.getTestClient();
	};

	private void inputFromKeyBoard(FxRobot robot, String input) {
		robot.write(input);
	}
	
	
}
