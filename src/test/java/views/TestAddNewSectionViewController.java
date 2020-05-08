package views;

import java.io.IOException;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestAddNewSectionViewController implements ConfirmationInterface
{
	int confirmCalled = 0;
	int cancelCalled = 0;
	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	//set up
	@Start
	private void Start(Stage stage)
	{
		confirmCalled = 0;
		cancelCalled = 0; 
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/AddNewSectionView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			AddNewSectionViewController cont = loader.getController();
			cont.setModel(model,client);
			cont.setModel2(this);
			cont.setName2("newSection");
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void confirmation() {
		confirmCalled++;
		
	}
	@Test
	void testButtons(FxRobot robot)
	{
	
		robot.clickOn("#confirmButton");

		
		Assertions.assertThat(confirmCalled).isEqualTo(1);
		Assertions.assertThat(cancelCalled).isEqualTo(0);
		//////
		robot.clickOn("#cancelButton");
		Assertions.assertThat(confirmCalled).isEqualTo(1);
		Assertions.assertThat(cancelCalled).isEqualTo(1);

		//////
		robot.clickOn("#confirmButton");
		robot.clickOn("#cancelButton");
		Assertions.assertThat(confirmCalled).isEqualTo(2);
		Assertions.assertThat(cancelCalled).isEqualTo(2);
	}
	@Test
	public void testTextArea(FxRobot robot)
	{
		enterText(robot, "Hello World");
		
	}
	private void enterText(FxRobot robot, String text) 
	{
		robot.clickOn("#newContentText");
		robot.write(text);
	}
	
	public void checkLabel(FxRobot robot, String newlabel)
	{
		Assertions.assertThat(robot.lookup("#sectionNameLabel").queryAs(Label.class)).hasText(newlabel);
	}
	@Test
	public void testLabel(FxRobot robot)
	{
		checkLabel(robot, "newSection");
	}
	@Override
	public void close() {
		
	}
	@Override
	public void cancel() {
		cancelCalled++;
		
	}

	

}
