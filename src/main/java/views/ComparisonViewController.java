package views;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import libs.BpTraverser;
import main.MainBPView;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.Section;

public class ComparisonViewController {
	private Stage primaryStage;
	private BusinessPlan targetBp2;
	private BusinessPlan targetBp1;
	private MyRemoteClient client;
	private BpTraverser traverser = new BpTraverser();

	@FXML
	private Button goBackBtn;
	@FXML
	private VBox sectionRows;
	@FXML
    private Label planNameLabel1;
    @FXML
    private Label planNameLabel2;

	@FXML
	void onClickGoBack(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/businessPlansByYear.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			System.out.println(pane);
			SelectorControllor cont = loader.getController();
			cont.setModel(this.client);
			cont.setStage(this.primaryStage);
			Scene sc = new Scene(pane);
			this.primaryStage.setScene(sc);
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setModels(BusinessPlan businessPlan, BusinessPlan businessPlan2) {
		this.targetBp1 = businessPlan;
		this.targetBp2 = businessPlan2;
		this.renderPlanDifferences();
	};
	
	public BusinessPlan getPlan1() {
		return this.targetBp1;
	}

	public BusinessPlan getPlan2() {
		return this.targetBp2;
	}
	
	private void renderPlanDifferences() {
		//Set titles for the grid pane's each column
		String planName1 = this.targetBp1.department.getValueSafe().concat(":"+this.targetBp1.year.getValueSafe());
		String planName2 = this.targetBp2.department.getValueSafe().concat(":"+this.targetBp2.year.getValueSafe());
		GridPane titleRow = this.createTitleRow(planName1, planName2);
		this.sectionRows.getChildren().add(titleRow);
		VBox.setMargin(titleRow, new Insets(0, 5, 0, 5));
		
		//Flatten business plans to ArrayLists
		this.traverser.flattenBusinessPlans(this.targetBp1, this.targetBp2);
		ArrayList<Section> bp1Sections = this.traverser.getFirstFlattenPlan();
		ArrayList<Section> bp2Sections = this.traverser.getSecondFlattenPlan();
		
		//Render each business plan section.
		for (int i = 0; i < bp1Sections.size(); i++) {
			Section bp1Section = bp1Sections.get(i);
			Section bp2Section = bp2Sections.get(i);
			String sectionName1;
			String sectionName2;
			String sectionContent1;
			String sectionContent2;
			//Get section names & contents to display for bp1
			if (bp1Section != null) {
				sectionName1 = bp1Section.getName();
				sectionContent1 = bp1Section.getContent().getValueSafe();
			}else {
				sectionName1 = "No Maching Secton Available";
				sectionContent1 = "No Maching Content Available";
			};
			//Get section names & contents to display for bp2
			if (bp2Section != null) {
				sectionName2 = bp2Section.getName();
				sectionContent2 = bp2Section.getContent().getValueSafe();
			}else {
				sectionName2 = "No Maching Secton Available";
				sectionContent2 = "No Maching Content Available";
			};
			
			//Check if the section contents are different
			boolean isDifferent = this.isDifferentContents(sectionContent1, sectionContent2);

			//Create a section content group for bp1
			Group sectionContentGroup1;
			sectionContentGroup1 = this.createSectionContentGroup(sectionName1, sectionContent1, isDifferent, "sectionContentGroup1-", i);

			//Create a section content group for bp2
			Group sectionContentGroup2;
			sectionContentGroup2 = this.createSectionContentGroup(sectionName2, sectionContent2, isDifferent, "sectionContentGroup2-", i);

			//Add the section contents & titles to each row
			GridPane sectionRow = this.createSectionContentRow(sectionContentGroup1, sectionContentGroup2);
			this.sectionRows.getChildren().add(sectionRow);
			
			//Set the margin of gridPane(sectionRow)
			VBox.setMargin(sectionRow, new Insets(0, 5, 0, 5));
		}
	}

	private GridPane createTitleRow(String planName1, String planName2) {
		//Create labels for business plan names.
		Label label1 = new Label(planName1);
		Label label2 = new Label(planName2);
		
		GridPane gridPane = new GridPane();
		// Adjust the grid width so that each column covers 50% of the overall width.
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		//Configure the border style & margins
		gridPane.setStyle("-fx-background-color: white;"
						+ "-fx-border-color: grey;");

		//Designate where to place the groups
		GridPane.setConstraints(label1, 0, 0);
		GridPane.setConstraints(label2, 1, 0);
		gridPane.getChildren().addAll(label1, label2);
		return gridPane;
	}

	private boolean isDifferentContents(String content1, String content2) {
		if (content1.equals(content2)) {
			return false;
		}else {
			return true;			
		}
	}

	private GridPane createSectionContentRow(Group sectionContentGroup1, Group sectionContentGroup2) {
		//Creates a grid pane container for two sectionContentGroups
	
		GridPane gridPane = new GridPane();
		// Adjust the grid width so that each column covers 50% of the overall width.
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
		//Configure the border style & margins
		gridPane.setStyle("-fx-background-color: white;"
						+ "-fx-border-color: grey;");

		//Designate where to place the groups
		GridPane.setConstraints(sectionContentGroup1, 0, 0);
		GridPane.setConstraints(sectionContentGroup1, 1, 0);
		gridPane.getChildren().addAll(sectionContentGroup1, sectionContentGroup2);
		return gridPane;
	}

	private Group createSectionContentGroup(String sectionName, String sectionContent, boolean isDifferent, String groupIdName, int secionIndex) {
		// Create parts for a group that displays the model's section content.
		Group group = new Group();
		group.setId(groupIdName+Integer.toString(secionIndex));
		VBox vbox = new VBox();
		Label sectionTitleLabel = new Label(sectionName);
		TextFlow textFlow = new TextFlow();
		Text content = new Text(sectionContent);
		content.setId("sectionContentText-"+Integer.toString(secionIndex));
		
		//Set styles for elements
		sectionTitleLabel.setFont(new Font("Arial", 18));;
		if (isDifferent) {
			sectionTitleLabel.setTextFill(Color.RED);
			content.setStyle("-fx-fill: red;" + 
					"-fx-font-size : 14px;");
		}

		// Compose elements in a group
		textFlow.getChildren().add(content);
		vbox.getChildren().add(sectionTitleLabel);
		vbox.getChildren().add(textFlow);
		group.getChildren().add(vbox);
		return group;
	}

	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}

	public void setClient(MyRemoteClient client) {
		this.client = client;
	}
}
