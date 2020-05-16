package views;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import libs.BpTraverser;
import models.BusinessPlan;
import models.Section;
import javafx.scene.text.*; 

public class PreviewController {
	private Scene previousScene;
	private Stage primaryStage; 
	private BpTraverser traverser = new BpTraverser();
	
    @FXML
    private Label planNameLabel;
    @FXML
    private VBox sectionContainer;
    @FXML
    private Button goBackBtn;
	private BusinessPlan businessPlan;
	
	
	@FXML
    void onClickGoback(ActionEvent event) {
    	this.primaryStage.setScene(this.previousScene);
    	this.primaryStage.show();
    }
    
    public void setStage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    
    public void setPreviousScene(Scene scene) {
    	this.previousScene = scene;
    }

	public void setBusinessPlan(BusinessPlan businessPlan) {
		this.businessPlan = businessPlan;
		this.renderBusinessPlan();
	}
    
    public void renderBusinessPlan() {
    	System.out.println("Check loaed plan:" + this.businessPlan.toString());
    	System.out.println("this.traverser:"+ this.traverser);
		this.sectionContainer.setPadding(new Insets(0, 40, 0, 40));
    	this.traverser.flattenBusinessPlan(this.businessPlan);
    	ArrayList<Section> sections = this.traverser.getFirstFlattenPlan();
    	
    	//Set the title of the plan
    	this.planNameLabel.setText(this.businessPlan.getDepartment() + " - " + this.businessPlan.getYear());
    	
		//Render each business plan section.
		for (int i = 0; i < sections.size(); i++) {
			String sectionName;
			String sectionContent;
			//Get section names & contents to display for bp1
			sectionName = sections.get(i).getName();
			sectionContent = sections.get(i).getContent().getValueSafe();

			//Create a section content group for bp1
			Group sectionContentGroup;
			sectionContentGroup = this.createSectionContentGroup(sectionName, sectionContent, "sectionContentGroup-", i);

			
			//Add the sectonContentGroup to the VBox
			this.sectionContainer.getChildren().add(sectionContentGroup);
		}    	
    }

	private Group createSectionContentGroup(String sectionName, String sectionContent, String groupIdName, int secionIndex) {
		// Create parts for a group that displays the model's section content.
		Group group = new Group();
		group.setId(groupIdName+Integer.toString(secionIndex));
		VBox vbox = new VBox();
		Label sectionTitleLabel = new Label(" - " + sectionName);
		TextFlow textFlow = new TextFlow();
		Text content = new Text(sectionContent);
		
		//Set Id
		sectionTitleLabel.setId("sectionTitleLabel-"+Integer.toString(secionIndex));
		content.setId("sectionContentText-"+Integer.toString(secionIndex));
		System.out.println("sectionTitleLabel:" + sectionTitleLabel.getId());
		System.out.println("content:" + content.getId());
				
		//Set styles for elements
		sectionTitleLabel.setFont(new Font("Arial", 18));;
		textFlow.setPadding(new Insets(0, 30, 10, 40));;
				
		// Compose elements in a group
		textFlow.getChildren().add(content);
		vbox.getChildren().add(sectionTitleLabel);
		vbox.getChildren().add(textFlow);
		group.getChildren().add(vbox);
		return group;
	}

    

}
