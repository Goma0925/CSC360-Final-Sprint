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

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestSelectorView implements ConfirmationInterface 
{
	
	int createCalled = 0;
	int viewCalled = 0;
	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	
	@Start
	private void Start(Stage stage)
	{
		createCalled = 0;
		viewCalled = 0; 
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TestSelectorView.class.getResource("../views/businessPlansByYear.fxml"));
	
		BorderPane pane;
		try {
			pane = loader.load();
			SelectorControllor cont = loader.getController();
			cont.setModel2(this);
			cont.setModel(client);
			
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	void testButtons(FxRobot robot)
	{
	
		robot.clickOn("#confirmButton");

		
		Assertions.assertThat(createCalled).isEqualTo(1);
		Assertions.assertThat(viewCalled).isEqualTo(0);
		//////
		robot.clickOn("#cancelButton");
		Assertions.assertThat(createCalled).isEqualTo(1);
		Assertions.assertThat(viewCalled).isEqualTo(1);

		//////
		robot.clickOn("#confirmButton");
		robot.clickOn("#cancelButton");
		Assertions.assertThat(createCalled).isEqualTo(2);
		Assertions.assertThat(viewCalled).isEqualTo(2);
	}
	@Override
	public void confirmation() {
		createCalled++;
		
	}
	@Override
	public void close() {
		
	}
	@Override
	public void cancel() {
		viewCalled++;
		
	}
}
