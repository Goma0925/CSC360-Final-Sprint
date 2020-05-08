package views;

//import org.assertj.core.api.Assertions;
import org.testfx.assertions.api.Assertions;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.ViewTransitionaModelInterface;

@ExtendWith(ApplicationExtension.class)
public class TestBPView implements ViewTransitionaModelInterface
{
	
	int cloneCalled = 0;
	int uploadCalled = 0;
	int closeCalled = 0;
	int removeCalled = 0;
	int selectCalled = 0;
	int addCalled = 0;
	MyRemoteClient client;
	Scene sc;
	BusinessPlan plan;
	BorderPane pane;
	@Start
	private void start(Stage stage) throws InterruptedException
	{
		//controller to start the view
		
		cloneCalled = 0;
		uploadCalled = 0;
		closeCalled = 0;
		removeCalled = 0;
		selectCalled = 0;
		addCalled = 0;
		//System.out.println(plan.getAllContent().getValue());
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/ExitView.fxml"));
		try {
			pane = loader.load();
			BPViewController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			System.out.println(cont.model2);
			sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//FxRobot robot = new FxRobot();
		//Thread.sleep(2000);
		//testButtons(robot);
		//testTextArea(robot);
		//System.out.println(cont.model2);
	}
	
	@Test
	void testButtons(FxRobot robot)
	{
	    robot.clickOn("#cloneButton");
		Assertions.assertThat(cloneCalled).isEqualTo(1);
		Assertions.assertThat(uploadCalled).isEqualTo(0);
		Assertions.assertThat(closeCalled).isEqualTo(0);
		Assertions.assertThat(selectCalled).isEqualTo(0);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#cloneButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(0);
		Assertions.assertThat(closeCalled).isEqualTo(0);
		Assertions.assertThat(selectCalled).isEqualTo(0);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#uploadButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(1);
		Assertions.assertThat(closeCalled).isEqualTo(0);
		Assertions.assertThat(selectCalled).isEqualTo(0);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#closeButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(1);
		Assertions.assertThat(closeCalled).isEqualTo(1);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(selectCalled).isEqualTo(0);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#selectButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(1);
		Assertions.assertThat(closeCalled).isEqualTo(1);
		Assertions.assertThat(selectCalled).isEqualTo(1);
		Assertions.assertThat(removeCalled).isEqualTo(0);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#removeButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(1);
		Assertions.assertThat(closeCalled).isEqualTo(1);
		Assertions.assertThat(selectCalled).isEqualTo(1);
		Assertions.assertThat(removeCalled).isEqualTo(1);
		Assertions.assertThat(addCalled).isEqualTo(0);
		//////
		robot.clickOn("#addButton");
		Assertions.assertThat(cloneCalled).isEqualTo(2);
		Assertions.assertThat(uploadCalled).isEqualTo(1);
		Assertions.assertThat(closeCalled).isEqualTo(1);
		Assertions.assertThat(selectCalled).isEqualTo(1);
		Assertions.assertThat(removeCalled).isEqualTo(1);
		Assertions.assertThat(addCalled).isEqualTo(1);
		//////
		robot.clickOn("#cloneButton");
		robot.clickOn("#uploadButton");
		robot.clickOn("#closeButton");
		robot.clickOn("#selectButton");
		robot.clickOn("#removeButton");
		robot.clickOn("#addButton");
		Assertions.assertThat(cloneCalled).isEqualTo(3);
		Assertions.assertThat(uploadCalled).isEqualTo(2);
		Assertions.assertThat(closeCalled).isEqualTo(2);
		Assertions.assertThat(selectCalled).isEqualTo(2);
		Assertions.assertThat(selectCalled).isEqualTo(2);
		Assertions.assertThat(selectCalled).isEqualTo(2);
	}
	
	/*@Test
	public void testTextArea(FxRobot robot)
	{
		enterText(robot, "Hello World");
		
	}*/
	
	/*private void enterText(FxRobot robot, String text) 
	{
		robot.clickOn("#idCentre College Institutional Mission Statement");
		robot.write(text);
	}*/
	
	/*private void checkText(FxRobot robot, String text)
	{
		Assertions.assertThat(robot.lookup("#contentText"))
	}*/
	/*@Test
	public void testTreeView(FxRobot robot, String test, int index)
	{
		//step1 
		robot.clickOn("#treeView");
	}*/
	
	@Override
	public void showCloneConfirmation() 
	{
		cloneCalled++;	
		System.out.println("click");
	}

	@Override
	public void showUploadConfirmation() 
	{
		uploadCalled++;	
	}

	@Override
	public void showCloseConfirmation() 
	{
		closeCalled++;
	}

	@Override
	public void showRemoveConfirmation() 
	{
		removeCalled++;
	}

	public void selectButton() 
	{
		selectCalled++;
	}
	public void removeButton() 
	{
		removeCalled++;
	}
	public void addButton() 
	{
		addCalled++;
	}
	@SuppressWarnings("rawtypes")
	@Test
	public void testTreeView(FxRobot robot)
	{
	
		TreeView tree = (TreeView) sc.lookup("#treeView");
		tree.getRoot();
		Assertions.assertThat(tree.getRoot().getValue()).isEqualTo(plan.root);
		VBox v = (VBox) sc.lookup("#vbox");
		TextArea text = (TextArea) v.getChildren().get(0);
		robot.clickOn(text);
		robot.write("test");
		
		
	}
	

	
	
}
