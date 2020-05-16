package views;

import java.io.IOException;
import java.util.LinkedList;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainBPView;
import models.*;

public class BPViewController 
{
	public BusinessPlan businessPlan;
	ViewTransitionaModelInterface viewTransitionModel = null;
	BorderPane pane;
	MyRemoteClient client;
	public Stage stage;
    TreeItem<Section> selectedTreeItem;
    int treeItemIndex = 0;
    //This is used when the user selects a section from the tree view
	
	@FXML
    private VBox commentContainer;
	@FXML
    private Button addCommentBtn;
    @FXML
    private Button cloneButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Button closeButton; 
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton; 
    @FXML
    private TreeView<Section> treeView; 
    @FXML
    private VBox Vbox;
    @FXML
    private Button previewBtn;
	
	@SuppressWarnings({ "unchecked" })
	public void setModel(BusinessPlan plan,MyRemoteClient client)
	{
		this.client = client;
		this.businessPlan = plan;
		setContent(businessPlan.root);
		TreeItem<Section> root = createTreeView(businessPlan.root);
		treeView.setRoot(root);
		removeButton.setDisable(true);
		addButton.setDisable(true);
    	this.addCommentBtn.setDisable(true);
	}
	public void setModel2(ViewTransitionaModelInterface viewTransitionModel)
	{
		this.viewTransitionModel = viewTransitionModel;
	}
	public void setPane(BorderPane pane)
	{
		this.pane = pane;
	}
    
    //For testing
    public TreeView<Section> getTreeView() {
    	return this.treeView;
    }
    
    //Before clicking on the add button users needs to select where they want to add the new section and 
    //after the button is clicked a new window will pop up and then ask the user to edit the content of the new section
    @FXML
    void onClickAdd(ActionEvent event) 
    {	
 
    	if(viewTransitionModel == null)
    	{
    	///////set up window/////////
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/AddNewSectionView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			AddNewSectionViewController cont = loader.getController();
			cont.setModel(businessPlan,client);
			cont.setParent(this.selectedTreeItem.getValue());
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();
		//////////////////////
		} catch (IOException e) {
			e.printStackTrace();
		}
    	}
    	else
    	{
    		viewTransitionModel.addButton();
    	}

    }

    @FXML
    void onClickSelect(ActionEvent event) 
    {
    	if(viewTransitionModel == null)
    	{
	    	try
	    	{
		    	this.selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
		    	if(this.businessPlan.isDeletable(this.selectedTreeItem.getValue()))
		    	{
		    		removeButton.setDisable(false);
		    		
		    	}
		    	addButton.setDisable(false);
		    	//Text edit area
				TextArea area2= new TextArea();
				area2.setId("editingArea");
				pane.setCenter(area2);
				Bindings.bindBidirectional(area2.textProperty(), this.selectedTreeItem.getValue().getContent());
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("Please Select a Section!");
	    	}
    	}
    	else
    	{
	    	viewTransitionModel.selectButton();
	    	addButton.setDisable(false);
	    	removeButton.setDisable(false);
    	};
    	
    	//Display comments
    	this.displayComments();
    	//Enable addCommentButton
    	this.addCommentBtn.setDisable(false);
    }
    //this is used to display the content of the Business Plan using recursion
    private void setContent(Section current)
    {
    	if(current.children.isEmpty())
    	{	
    		TextArea area2= new TextArea();
    		Bindings.bindBidirectional(area2.textProperty(),current.getContent());
    		Vbox.getChildren().add(area2);

    	}
    	else
    	{
    		TextArea area= new TextArea();
    		Vbox.getChildren().add(area);
    		Bindings.bindBidirectional(area.textProperty(),current.getContent());
    		for(int i = 0; i<current.children.size(); i++)
    		{
    			setContent(current.getChildren().get(i));
    			
    		}
    	}
    }
    
    //this is used to create the tree view according to the sections using recursion
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private TreeItem createTreeView(Section current)
    {
    	
    	//System.out.println(current);
    	TreeItem treeItem;
    	if(current.children.isEmpty())
    	{	
    		treeItem = new TreeItem(current);
    	}
    	else
    	{
    		treeItem = new TreeItem(current);
    		for(int i = 0; i<current.children.size(); i++)
    		{
    			treeItem.getChildren().add(createTreeView(current.getChildren().get(i)));
    		}
    	}
		return treeItem;
    }
    
    protected void displayComments() {
    	//Sprint 5 by Amon
    	this.commentContainer.getChildren().clear();
    	Section selectedSection = this.getSelectedSection();
    	LinkedList<Comment> comments= selectedSection.getComments();
    	
    	//Display comments from the last one (=oldest comment).
    	//So that the oldest comment comes at the top
    	int commentIndex = 0; //Index to assign the id to elements
    	for (int i=0; i<comments.size(); i++) {
    		String authorName = comments.get(i).getAuthor().getUsername();
        	String commentContent = comments.get(i).getContent();
    		Group commentGroup = this.createCommentGroup(authorName, commentContent, commentIndex);
        	this.commentContainer.getChildren().add(commentGroup);
        	commentIndex ++;
    	}
    };
    
    //This function creates a group element that contains comment and its author to display.
    public Group createCommentGroup(String authorName, String commentContent, int commentIndex) {
    	Group group = new Group();
    	Rectangle background = new Rectangle();
    	VBox vbox = new VBox();
    	Button commentRemoveButton = new Button("Remove");
    	Label authorLabel = new Label(authorName);
    	TextFlow textFlow = new TextFlow();
    	Text content = new Text(commentContent);
    	
    	//Set CSS IDs to each item
    	commentRemoveButton.setId("removeCommentBtn-"+Integer.toString(commentIndex));
    	authorLabel.setId("authorLabel-"+Integer.toString(commentIndex));
    	textFlow.setId("commentTextFlow-"+Integer.toString(commentIndex));
		
		//Set remove event to the commentRemoveButton
		Section currentSection = this.getSelectedSection();
		commentRemoveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//Remove the comment from the section's list (model)
				currentSection.removeComment(commentIndex);
				//Re-render
				displayComments();
			};
		});
    	
    	//Compose the comment group
    	textFlow.getChildren().add(content);
    	vbox.getChildren().add(commentRemoveButton);
    	vbox.getChildren().add(authorLabel);
    	vbox.getChildren().add(textFlow);
    	group.getChildren().add(background);
    	group.getChildren().add(vbox);
    	return group;
    }
    
    //Get the selected Section of the business plan.
    private Section getSelectedSection() {
    	this.selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
		try {
	    	Section selectedSection = this.selectedTreeItem.getValue();
	    	return selectedSection;
		}catch(Exception e){
			return null;
		}
    }
    
    @FXML
    void onClickAddComment(ActionEvent event) {
		//Get the selected section.
		this.displayCommentInputWindow(this.getSelectedSection());
    }
    
    //Show a popup window to take user comment for a selected section.
    private void displayCommentInputWindow(Section selectedSection) {
    	// selectedSection: The section to insert a new comment to.
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/AddCommentPopUp.fxml"));
		try {
			Pane pane = loader.load();
			Scene commentInputScene = new Scene(pane);
            Stage popup = new Stage();
			AddCommentController cont = loader.getController();
			cont.setStage(popup);
			cont.setParentController(this);
			cont.setModels(selectedSection, client);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(this.stage);
            popup.setScene(commentInputScene);
            popup.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    };
    
    @FXML
    private void onClickPreview() {
	    //load the preview page
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/PreviewView.fxml"));
		BorderPane previewPane = this.pane;
		try {
			previewPane = loader.load();
			System.out.println(previewPane);
			PreviewController cont = loader.getController();
			cont.setStage(this.stage);
			cont.setPreviousScene(this.stage.getScene());
			cont.setBusinessPlan(this.businessPlan);
			Scene sc = new Scene(previewPane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //when this button is clicked, the current Business Plan will be cloned
    @FXML
    void onClickClone(ActionEvent event) 
    {
    	if(viewTransitionModel == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CloneBPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CloneBPViewController cont = loader.getController();
			cont.setModel(this.businessPlan, client);
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	}
    	else
    	{
		 viewTransitionModel.showCloneConfirmation();
		 System.out.println("click");
    	}
    }
    //this is used to close the page
    @FXML
    void onClickClose(ActionEvent event) 
    {
    	if(viewTransitionModel == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CloseConfirmView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CloseConfirmViewController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			cont.setModel(this.businessPlan,client);
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
    	}
    	else
    	{
    	viewTransitionModel.showCloseConfirmation();
    	}
    }
    //the remove button can be clicked when the user select a section and then the user can choose to remove that section
    @FXML
    void onClickRemove(ActionEvent event) 
    {
    	if(viewTransitionModel == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/RemoveConfirmationView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			RemoveConfirmationViewController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			cont.setModel(this.businessPlan,client);
			cont.setParent(this.selectedTreeItem.getValue());
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();

		} catch (IOException e) 
		{
			e.printStackTrace();

    	}
    	}

    	else
    	{
    		viewTransitionModel.removeButton();
    	}
    }

    //this is used when the user tries to upload a Business Plan
    @FXML
    void onClickUpload(ActionEvent event) 
    {
    	if(viewTransitionModel == null)
    	{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BPViewController.class.getResource("../views/UploadConfirmationView.fxml"));
			BorderPane pane;
			try {
				pane = loader.load();
				
				UploadConfirmationViewController cont = loader.getController();
				cont.setModel(this.businessPlan, client);
				Scene sc = new Scene(pane);
				cont.setStage(stage);
				stage.setScene(sc);
				stage.show();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
    	}
    	else
    	{
    		viewTransitionModel.showUploadConfirmation();
    	}
    }
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
