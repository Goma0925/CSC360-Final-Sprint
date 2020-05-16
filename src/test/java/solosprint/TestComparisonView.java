package solosprint;

import java.io.IOException;
import java.util.ArrayList;
import org.testfx.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import libs.BpTraverser;
import main.MainBPView;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import testhelpers.*;
import views.ComparisonViewController;
import views.SelectorControllor;

@ExtendWith(ApplicationExtension.class)
class TestComparisonView {
	/// Sprint 5 by Amon Otsuki
	///
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;
	ComparisonViewController comparisonController;
	SelectorControllor selectorController;
	Stage stage;
	private FxRobot robot;
	
	@Start
	private void openSelectorView(Stage stage) throws Exception
	{
		this.initTestServerAndClient();
		this.stage = stage;
	    //load the selection page of Business Plan
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/businessPlansByYear.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			SelectorControllor cont = loader.getController();
			cont.setModel(this.client);
			cont.setStage(stage);
			this.selectorController = cont;
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	@Test
	void testMain(FxRobot robot) throws Exception {
		this.robot = robot;
		this.testTransitionFromSelectorView(this.robot);
	}
	
	void testTransitionFromSelectorView(FxRobot robot) throws Exception {
		//Switch mode
		robot.clickOn("#modeSwitchBtn");
		
		//Select two plans (the first one and the second one).
		TableView<BusinessPlan> tableView = robot.lookup("#tableView").queryAs(TableView.class);
		tableView.getSelectionModel().select(0);
		tableView.getSelectionModel().select(1);
		
		//Open comparison view
		robot.clickOn("#viewBtn");

		//Check if the sections that are different are highlighted
		this.testContentDifferences();
	};
	
	void testContentDifferences() throws Exception {
		//Get the business plans on display from the controller 
		this.comparisonController = this.selectorController.comparisonController;
		BusinessPlan planToCompare1 = this.comparisonController.getPlan1();
		BusinessPlan planToCompare2 =  this.comparisonController.getPlan1();
		
		//Flatten business plans to ArrayLists
		BpTraverser traverser = new BpTraverser();
		traverser.flattenBusinessPlans(planToCompare1, planToCompare2);
		ArrayList<Section> bp1Sections = traverser.getFirstFlattenPlan();
		ArrayList<Section> bp2Sections = traverser.getSecondFlattenPlan();
		
		for (int i=0; i<bp1Sections.size();i++) {
			Group sectionContentGroup1 = robot.lookup("#sectionContentGroup1-"+i).queryAs(Group.class);
			Group sectionContentGroup2 = robot.lookup("#sectionContentGroup2-"+i).queryAs(Group.class);
			this.isDifferentProperlyHighlighted(bp1Sections.get(i), bp2Sections.get(i), sectionContentGroup1, sectionContentGroup2, i);
		}
	};
	
	private void isDifferentProperlyHighlighted(Section bp1Section, Section bp2Section, Group sectionContentGroup1, Group sectionContentGroup2, int sectionIndex) {
		//Section bp1Section: Model of the Section of the business plan1
		//Section bp2Section: Model of the Section of the business plan1
		//Group sectionContentGroup1: View element that displays the bp1Section
		//Group sectionContentGroup2: View element that displays the bp2Section
		
		//Get the section names and contents
		String sectionContent1;
		String sectionContent2;
		if (bp1Section != null) {
			sectionContent1 = bp1Section.getContent().getValueSafe();
		}else {
			sectionContent1 = "No Maching Content Available";
		};
		//Get section names & contents to display for bp2
		if (bp2Section != null) {
			sectionContent2 = bp2Section.getContent().getValueSafe();
		}else {
			sectionContent2 = "No Maching Content Available";
		};
		
		//Check if the difference between the business plan sections is highlighted, 
		//if the section contents are different.
		Text content = this.robot.lookup("#sectionContentText-" + Integer.toString(sectionIndex)).queryAs(Text.class);
		String contentStyle = content.getStyle();
		System.out.println("Checking each sectionContentGroup's contentText...");
		System.out.println("	content Text's Style: '" + contentStyle);
		System.out.println("	Style contains the highlighter: " + content.getStyle().contains("-fx-fill: red;"));
		if (!sectionContent1.equals(sectionContent2)) {
			//If the section contents are different,
			//content texts should be highlighted.
			Assertions.assertThat(contentStyle).contains("-fx-fill: red;");
		}
	}
	
	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.getTestServer();
		this.client = factory.getTestClient();
	};
	
}
