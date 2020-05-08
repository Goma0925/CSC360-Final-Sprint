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
public class TestCreateNewBPView implements ConfirmationInterface {
	int confirmCalled = 0;
	int cancelCalled = 0;
	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	@Start
	private void Start(Stage stage)
	{
		confirmCalled = 0;
		cancelCalled = 0; 
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CreateNewBPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CreateNewBPViewController cont = loader.getController();
			cont.setModel2(this);
			cont.setModel(client);
			cont.setLabel("test");
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testTextArea(FxRobot robot)
	{
		enterText(robot, "1999");
		
	}
	private void enterText(FxRobot robot, String text) 
	{
		robot.clickOn("#yearText");
		robot.write(text);
	}
	@Test
	public void testCombo(FxRobot robot)
	{
		robot.clickOn("#typeCombo");
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
	public void checkLabel(FxRobot robot, String newlabel)
	{
		Assertions.assertThat(robot.lookup("#depLabel").queryAs(Label.class)).hasText(newlabel);
	}
	public void testLabel(FxRobot robot)
	{
		checkLabel(robot, "test");
	}
	@Override
	public void confirmation() {
		confirmCalled++;
		
	}
	@Override
	public void close() {
		
	}
	@Override
	public void cancel() {
		cancelCalled++;
		
	}
}
