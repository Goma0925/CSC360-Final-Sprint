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
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.MyRemoteClient;
import models.Section;
import models.ViewTransitionaModelInterface;

@ExtendWith(ApplicationExtension.class)
public class TestNonEditableView implements ViewTransitionaModelInterface 
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
		@Start
		private void start(Stage stage) throws InterruptedException
		{
			//controller to start the view
			
			closeCalled = 0;
			selectCalled = 0;
			plan = new CNTRAssessment();
			Section current = plan.root;
			current.setContent("root");
			plan.addSection(current);
			current.getChildren().get(1).setContent("goal2");;
			current = current.getChildren().get(0);
			System.out.println(current.getParent());
			current.setContent("goal");
			current.addChild(new Section("Program Goals and Student Learning Objective"));
			current.getChildren().get(0).setContent("objective1");
			current.getChildren().get(1).setContent("objective2");
			//System.out.println(plan.getAllContent().getValue());
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainBPView.class.getResource("../views/NonEditableView.fxml"));
			BorderPane pane;
			try {
				pane = loader.load();
				NonEditableViewController cont = loader.getController();
				//cont.setModel(client.getCurrentBP());
				cont.setModel(plan, client);
				cont.setModel2(this);
				System.out.println(cont.model2);
				cont.setPane(pane);
				sc = new Scene(pane);
				stage.setScene(sc);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Test
		void testButtons(FxRobot robot)
		{

			robot.clickOn("#closeButton");

			Assertions.assertThat(closeCalled).isEqualTo(1);
		
			Assertions.assertThat(selectCalled).isEqualTo(0);
		
			//////
			robot.clickOn("#selectButton");

			Assertions.assertThat(closeCalled).isEqualTo(1);
			Assertions.assertThat(selectCalled).isEqualTo(1);
	

		}
		
		
		@Override
		public void showCloneConfirmation() 
		{
				
		}

		@Override
		public void showUploadConfirmation() 
		{
			
		}

		@Override
		public void showCloseConfirmation() 
		{
			closeCalled++;
		}

		@Override
		public void showRemoveConfirmation() 
		{
			
		}

		public void selectButton() 
		{
			selectCalled++;
		}
		public void removeButton() 
		{
			
		}
		public void addButton() 
		{
			
		}
		@SuppressWarnings("rawtypes")
		@Test
		public void testTreeView(FxRobot robot)
		{
			TreeView tree = (TreeView) sc.lookup("#treeView");
			tree.getRoot();
			Assertions.assertThat(tree.getRoot().getValue()).isEqualTo(plan.root);
		}
		
		
		
	}






