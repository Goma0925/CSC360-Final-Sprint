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
public class TestUploadView implements ConfirmationInterface
{

	int cancelCalled = 0;
	int confirmCalled = 0;
	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	@Start
	private void Start(Stage stage)
	{
		cancelCalled = 0;
		confirmCalled = 0;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/UploadConfirmationView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			UploadConfirmationViewController cont = loader.getController();
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

	
	@Test
	public void testButton(FxRobot robot)
	{
		Assertions.assertThat(cancelCalled).isEqualTo(0);
		Assertions.assertThat(confirmCalled).isEqualTo(0);
		
		robot.clickOn("#cancelButton");
		robot.clickOn("#confirmButton");
		Assertions.assertThat(cancelCalled).isEqualTo(1);
		Assertions.assertThat(confirmCalled).isEqualTo(1);
		
		robot.clickOn("#cancelButton");
		robot.clickOn("#confirmButton");
		robot.clickOn("#cancelButton");
		robot.clickOn("#confirmButton");
		Assertions.assertThat(cancelCalled).isEqualTo(3);
		Assertions.assertThat(confirmCalled).isEqualTo(3);
		
	}
	
	@Override
	public void confirmation() 
	{
		confirmCalled++;
	}

	@Override
	public void close() 
	{
		
	}

	@Override
	public void cancel() 
	{
		cancelCalled++;
	}


}
