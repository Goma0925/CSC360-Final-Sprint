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
class TestLogin implements ConfirmationInterface {
	int loginCalled = 0;

	BusinessPlan model = new VMOSA();
	MyRemoteClient client = null;
	@Start
	private void Start(Stage stage)
	{

		loginCalled = 0; 
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/loginPage.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			LoginController cont = loader.getController();
			cont.setModel2(this);
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void enterUsername(FxRobot robot) {
		robot.clickOn("#username");
		robot.write("1234");
	}
	@Test
	public void enterPass(FxRobot robot) {
		robot.clickOn("#password");
		robot.write("1234");
	}
	@Test
	public void testButtons(FxRobot robot) 
	{
		Assertions.assertThat(loginCalled).isEqualTo(0);
		robot.clickOn("#loginButton");

		
		Assertions.assertThat(loginCalled).isEqualTo(1);

		//////
		robot.clickOn("#loginButton");
		Assertions.assertThat(loginCalled).isEqualTo(2);


		//////
		robot.clickOn("#loginButton");
		robot.clickOn("#loginButton");
		Assertions.assertThat(loginCalled).isEqualTo(4);
	}
	@Test
	public void testCombo(FxRobot robot)
	{
		robot.clickOn("#serverMenu");
	}
	@Override
	public void confirmation() {
		loginCalled++;
		
	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

}
