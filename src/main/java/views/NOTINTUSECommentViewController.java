package views;
//package views;
//
//import java.io.IOException;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import models.BusinessPlan;
//import models.Person;
//
//public class CommentViewController {
//	//This class is used by BPView Controller to add a comment feature.
//	private Stage stage;
//	private Pane rootPane;
//	private VBox commentContainer = null;
//	public CommentViewController(Stage stage, Pane pane) {
//		this.stage = stage;
//		this.rootPane = pane;
//    	System.out.println(this.rootPane.toString());
//	}
//	
//	public void displayComments() {
//    	System.out.println("Displaying comments...");
//    	System.out.println("root pane:\n"+this.rootPane.toString());
//	};
//	
//	public void onClickAddComment(BusinessPlan bp, Person author) {
//		System.out.println("onclick add comment");
//    	FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(BPViewController.class.getResource("../views/AddCommentPopUp.fxml"));
//		BorderPane pane;
//		try {
//			pane = loader.load();
//			AddCommentController cont = loader.getController();
//			Scene sc = new Scene(pane);
//			stage.setScene(sc);
//			stage.show();
//		//////////////////////
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	};	
//}
