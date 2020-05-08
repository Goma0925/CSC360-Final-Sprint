package views;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import models.*;

public class BPViewController 
{
	BusinessPlan model;
	ViewTransitionaModelInterface model2 = null;
	BorderPane pane;
	MyRemoteClient client;
	Stage stage;
	@SuppressWarnings({ "unchecked" })
	public void setModel(BusinessPlan plan,MyRemoteClient client)
	{
		this.client = client;
		model = plan;
		setContent(model.root);
		TreeItem<Section> root = createTreeView(model.root);
		treeView.setRoot(root);
		removeButton.setDisable(true);
		addButton.setDisable(true);
	
	}
	public void setModel2(ViewTransitionaModelInterface model)
	{
		model2 = model;
	}
	public void setPane(BorderPane pane)
	{
		this.pane = pane;
	}
	
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
    
    //Before clicking on the add button users needs to select where they want to add the new section and 
    //after the button is clicked a new window will pop up and then ask the user to edit the content of the new section
    @FXML
    void onClickAdd(ActionEvent event) 
    {	
 
    	if(model2 == null)
    	{
    	///////set up window/////////
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/AddNewSectionView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			AddNewSectionViewController cont = loader.getController();
			cont.setModel(model,client);
			cont.setParent(selected.getValue());
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
    		model2.addButton();
    	}

    }
    TreeItem<Section> selected;
    //This is used when the user selects a section from the tree view
    @FXML
    void onClickSelect(ActionEvent event) 
    {
    	if(model2 == null)
    	{
    	try
    	{
    	selected = treeView.getSelectionModel().getSelectedItem();
    	if(model.isDeletable(selected.getValue()))
    	{
    		removeButton.setDisable(false);
    		
    	}
    	addButton.setDisable(false);
    	
		TextArea area2= new TextArea();
		pane.setCenter(area2);
		Bindings.bindBidirectional(area2.textProperty(),selected.getValue().getContent());
    	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Please Select a Section!");
    	}}
    	else
    	{
    	model2.selectButton();
    	addButton.setDisable(false);
    	removeButton.setDisable(false);
    	}
		
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
    	if(current.children.isEmpty())
    	{	
    		TreeItem temp = new TreeItem(current);
    		return temp;
    	}
    	else
    	{
    		TreeItem temp2 = new TreeItem(current);
    		for(int i = 0; i<current.children.size(); i++)
    		{
    			temp2.getChildren().add(createTreeView(current.getChildren().get(i)));
    		}
    		return temp2;
    	}
    	
    }
    
    //when this button is clicked, the current Business Plan will be cloned
    @FXML
    void onClickClone(ActionEvent event) 
    {
    	if(model2 == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CloneBPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CloneBPViewController cont = loader.getController();
			cont.setModel(model, client);
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
		 model2.showCloneConfirmation();
		 System.out.println("click");
    	}
    }
    //this is used to close the page
    @FXML
    void onClickClose(ActionEvent event) 
    {
    	if(model2 == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/CloseConfirmView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			CloseConfirmViewController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			cont.setModel(model,client);
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
    	model2.showCloseConfirmation();
    	}
    }
    //the remove button can be clicked when the user select a section and then the user can choose to remove that section
    @FXML
    void onClickRemove(ActionEvent event) 
    {
    	if(model2 == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/RemoveConfirmationView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			RemoveConfirmationViewController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			cont.setModel(model,client);
			cont.setParent(selected.getValue());
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
    		model2.removeButton();
    	}
    }

    //this is used when the user tries to upload a Business Plan
    @FXML
    void onClickUpload(ActionEvent event) 
    {
    	if(model2 == null)
    	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/UploadConfirmationView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			
			UploadConfirmationViewController cont = loader.getController();
			cont.setModel(model, client);
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
    	model2.showUploadConfirmation();
    	}
    }
	public void setStage(Stage stage) {
		this.stage = stage;
		
	}
   
}
