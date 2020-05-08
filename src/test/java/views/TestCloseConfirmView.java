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
public class TestCloseConfirmView implements ConfirmationInterface
{

	int closeCalled = 0;
	int cancelCalled = 0;
	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	@Start
	private void Start(Stage stage)
	{
		closeCalled = 0;
		cancelCalled = 0;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CloseConfirmView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CloseConfirmViewController cont = loader.getController();
			cont.setModel(model, client);
			cont.setModel2(this);
			Scene sc = new Scene(pane);
			stage = new Stage();
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void confirmation() 
	{
		
	}
	@Test
	public void testButton(FxRobot robot)
	{
		Assertions.assertThat(closeCalled).isEqualTo(0);
		Assertions.assertThat(cancelCalled).isEqualTo(0);
		robot.clickOn("#closeButton");
		robot.clickOn("#cancelButton");
		Assertions.assertThat(closeCalled).isEqualTo(1);
		Assertions.assertThat(cancelCalled).isEqualTo(1);
		robot.clickOn("#closeButton");
		robot.clickOn("#closeButton");
		robot.clickOn("#cancelButton");
		robot.clickOn("#cancelButton");
		Assertions.assertThat(closeCalled).isEqualTo(3);
		Assertions.assertThat(cancelCalled).isEqualTo(3);
	}
	@Override
	public void close() 
	{
		closeCalled++;

	}

	@Override
	public void cancel() 
	{
		cancelCalled++;

		
	}



}
