package views;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.Section;
import models.ViewTransitionaModelInterface;
// the same as the editable one except this one does not have clone, upload, add new section and remove section
public class NonEditableViewController {

	BusinessPlan model;
	BorderPane pane;
	TreeItem<Section> selected;
	ViewTransitionaModelInterface model2;
	Stage stage;
	MyRemoteClient client;
	@SuppressWarnings({ "unchecked" })
	public void setModel(BusinessPlan plan, MyRemoteClient client)
	{

		model = plan;
		setContent(model.root);
		TreeItem<Section> root = createTreeView(model.root);
		treeView.setRoot(root);
		this.client = client;
	}
	
	//model2 is only for test
	public void setModel2(ViewTransitionaModelInterface model)
	{
		model2 = model;
	}
	
	public void setPane(BorderPane pane)
	{
		this.pane = pane;
	}
	//Create the content text of the Business Plan
	   private void setContent(Section current)
	    {
	    	if(current.children.isEmpty())
	    	{	
	    		Text area2= new Text();
	    		Bindings.bindBidirectional(area2.textProperty(),current.getContent());
	    		Vbox.getChildren().add(area2);

	    	}
	    	else
	    	{
	    		Text area= new Text();
	    		Vbox.getChildren().add(area);
	    		Bindings.bindBidirectional(area.textProperty(),current.getContent());
	    		for(int i = 0; i<current.children.size(); i++)
	    		{
	    			setContent(current.getChildren().get(i));
	    			
	    		}
	    	}
	    }
    //Create the tree view according to the sections
	//user recursion
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
    @FXML
    private Button closeButton;

    @FXML
    private VBox Vbox;

    @FXML
    private TreeView<Section> treeView;

    //the the close button is clicked the close confirm page will pop up
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
    //When a section is select from the tree view, the content of the section will be displayed on the content area
    @FXML
    void onClickSelect(ActionEvent event) 
    {
    	if(model2 == null)
    	{
    	try
    	{
    	selected = treeView.getSelectionModel().getSelectedItem();
    	
		Text area2= new Text();
		pane.setCenter(area2);
		Bindings.bindBidirectional(area2.textProperty(),selected.getValue().getContent());
    	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Please Select a Section!");
    	}
    	}
    	else
    	{
    	model2.selectButton();
    	}
		
    }
	public void setStage(Stage stage) {
		this.stage = stage;
		
	}
}
