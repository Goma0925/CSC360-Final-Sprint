package views;

import org.testfx.assertions.api.Assertions;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestCloneBPView implements ConfirmationInterface
{

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
		loader.setLocation(BPViewController.class.getResource("../views/CloneBPView.fxml"));
		BorderPane pane;
		
			try {
				pane = loader.load();
				CloneBPViewController cont = loader.getController();
				cont.setModel(model, client);
				cont.setModel2(this);
				Scene sc = new Scene(pane);
				stage.setScene(sc);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	@Test 
	public void testTextField(FxRobot robot)
	{
		enterYear(robot, "2019");
	}
	
	private void enterYear(FxRobot robot, String year)
	{
		robot.clickOn("#cloneYear");
		robot.write(year);
	}
	@Test
	public void testButton(FxRobot robot)
	{
		Assertions.assertThat(confirmCalled).isEqualTo(0);
		Assertions.assertThat(cancelCalled).isEqualTo(0);
		robot.clickOn("#confirmButton");
		Assertions.assertThat(confirmCalled).isEqualTo(1);
		Assertions.assertThat(cancelCalled).isEqualTo(0);
		robot.clickOn("#confirmButton");
		robot.clickOn("#confirmButton");
		Assertions.assertThat(confirmCalled).isEqualTo(3);
		robot.clickOn("#cancelButton");
		robot.clickOn("#cancelButton");
		Assertions.assertThat(cancelCalled).isEqualTo(2);
		
	}

	@Override
	public void confirmation() 
	{
		confirmCalled++;
	}

	@Override
	public void cancel() 
	{
		cancelCalled++;
	}
	@Override
	public void close() 
	{
		
	}

}
