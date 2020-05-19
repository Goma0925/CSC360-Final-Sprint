package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.Comment;
import models.MyRemoteClient;
import models.Section;

public class AddCommentController {
	private Stage popupStage;
	private Section selectedSection;
	private MyRemoteClient client;
	private BPViewController parentController;
	
	public void setStage(Stage popupStage) {
		this.popupStage = popupStage;
	};
	
	public void setParentController(BPViewController parentController) {
		this.parentController = parentController;
	}
	
	public void setModels(Section selectedSection, MyRemoteClient client) {
		this.selectedSection = selectedSection;
		this.client = client;
	}

	@FXML
    private Label SectionNameLable;

    @FXML
    private TextArea commentContent;

    @FXML
    private Button addCommentCancelBtn;

    @FXML
    private Button addCommentConfirmBtn;

    @FXML
    void onClickCancel(ActionEvent event) {

    }
    
    @FXML
    void onClikcCancel(ActionEvent event) {
    	this.popupStage.close();
    }

    @FXML
    void onClikcConfirm(ActionEvent event) {
    	String commentStr = commentContent.getText();
    	Comment comment = new Comment(client.getLoginPerson(), commentStr);
    	this.selectedSection.addComment(comment);
    	this.popupStage.close();
    	System.out.println("Added comment to a section:");
    	System.out.print("User:");
    	System.out.println(this.client.getLoginPerson());
    	System.out.println("	"+this.selectedSection.toString());
    	this.parentController.displayComments();
    	this.popupStage.close();
    };


}