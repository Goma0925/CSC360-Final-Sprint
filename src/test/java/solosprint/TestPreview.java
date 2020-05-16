package solosprint;

import org.testfx.assertions.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import libs.BpTraverser;
import main.MainBPView;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import testhelpers.*;
import views.BPViewController;
import views.PreviewController;

@ExtendWith(ApplicationExtension.class)
class TestPreview {
	/// Sprint 5 by Amon Otsuki
	///
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;
	BPViewController controller;
	BusinessPlan testPlan;
	BPViewController viewController;
	private FxRobot robot;
	
	
	@Start
	private void start(Stage stage) throws Exception
	{
		this.initTestServerAndClient();
		this.testPlan = this.planFactory.generateTestPlan();
		
		//Get editing Scene 
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		Scene editingScene = null;
		BorderPane editingPane;
		try {
			editingPane = loader.load();
    		BPViewController cont = loader.getController();
    		this.viewController = cont;
    		cont.setModel(this.testPlan, this.client);
    		cont.setPane(editingPane);
    		cont.setStage(stage);
    		editingScene = new Scene(editingPane);
		} catch (IOException e) {
			System.out.println("ERROR!!!");
			e.printStackTrace();
		};
		
	    //load the preview page
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(MainBPView.class.getResource("../views/PreviewView.fxml"));
		BorderPane previewPane;
		try {
			previewPane = loader2.load();
			System.out.println(previewPane);
			PreviewController cont = loader2.getController();
			cont.setStage(stage);
			cont.setPreviousScene(editingScene);
			cont.setBusinessPlan(this.testPlan);
			Scene sc = new Scene(previewPane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		};
		System.out.println("Ready");
	};
	
	
	@Test
	void testPreview(FxRobot robot) throws Exception {
		this.robot = robot;
		//Check if the test plan is rendered properly
		//First, get the flatten plan.
		BpTraverser traverser = new BpTraverser();
		traverser.flattenBusinessPlan(this.testPlan);
		ArrayList<Section> sections = traverser.getFirstFlattenPlan();
		this.testPreviewContent(sections);

		
		//Check if an edit is reflected in the preview.
		
		//First, edit a section in the edit view.
		this.robot.clickOn("#goBackBtn");
		TreeView<Section> treeView = this.viewController.getTreeView();
		treeView.getSelectionModel().select(0);
		System.out.println(this.robot.lookup("#editingArea").queryAs(VBox.class).getChildren());
		robot.clickOn("#selectButton");
				
		TextArea textArea = (TextArea) this.robot.lookup("#editingArea").queryAs(TextArea.class);
		robot.clickOn(textArea);
		robot.write(this.getBestJoke());
		BusinessPlan editedPlan = this.viewController.businessPlan;
		
		//Flatten the business plan and check the preview again.
		this.robot.clickOn("#previewBtn");
		traverser.flattenBusinessPlan(editedPlan);
		ArrayList<Section> sections2 = traverser.getFirstFlattenPlan();
		this.testPreviewContent(sections2);
	};
	
	private void testPreviewContent(ArrayList<Section> sections) {
		for (int i=0; i<sections.size(); i++) {
			//Get view's rendered data.
			Label sectionTitleLabel = robot.lookup("#sectionTitleLabel-" + i).queryAs(Label.class);
			Text contentTextFlow = robot.lookup("#sectionContentText-"+i).queryAs(Text.class);
			
			//Get the model's data
			String sectionTitle = sections.get(i).getName();
			String content = sections.get(i).getContent().getValueSafe();
			
			//Check if the model's data matches with the view
			Assertions.assertThat(sectionTitleLabel).hasText(" - " + sectionTitle);
			Assertions.assertThat(contentTextFlow).hasText(content);
		};
	}
	
	private String getBestJoke() {
		return  "\nComputer Science Joke:"+
				"\nMy dog ate my computer science project\n" + 
				"\"your dog ate your coding assignment?\"\n" + 
				"\n" + 
				"\n" + 
				"It took him a couple bytes'"+
				"\n................HAHAHAHAHA";
	}

	
	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.getTestServer();
		this.client = factory.getTestClient();
	};

	
	
}
