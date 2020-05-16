package testhelpers;

import models.BusinessPlan;
import models.CNTRAssessment;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;

public class DummyNetworkClassFactory {
	String defaultUsername = "admin";
	String defaultPW = "adminPW";
	private MyRemoteImpl server;
	private MyRemoteClient client;
	private DummyBusinessPlanFactory bpFactory = new DummyBusinessPlanFactory();
	public String getAdminUsername() {
		return this.defaultUsername;
	}
	
	public String getAdminPW() {
		return this.defaultPW;
	}
	
	public MyRemoteClient getTestClient() {
		return this.client;
	}
	
	public MyRemoteImpl getTestServer() {
		return this.server;
	}
	
	private MyRemoteClient generateTestCliet(MyRemoteImpl server) {
		MyRemoteClient client;
		client = new MyRemoteClient(server);
		return client;
	}
	
	private MyRemoteImpl generateTestServer() {
		server = new MyRemoteImpl();

//		MyRemoteImpl server;
//		BusinessPlan plan = new CNTRAssessment();
//		Section current = plan.root;
//		current.setContent("root");
//		plan.addSection(current);
//		current.getChildren().get(1).setContent("goal2");;
//		current = current.getChildren().get(0);
//		current.setContent("goal");
//		current.addChild(new Section("Program Goals and Student Learning Objective"));
//		current.getChildren().get(0).setContent("objective1");
//		current.getChildren().get(1).setContent("objective2");
//		plan.setDepartment("CSC");
//		plan.setYear("2020");
		BusinessPlan planA = bpFactory.generateTestPlan();
		BusinessPlan planB = bpFactory.generateModifiedPlan();
		
		server.getStoredBP().add(planA);
		server.getStoredBP().add(planB);
		
		BusinessPlan plan2 = new CNTRAssessment();
		Section current2 = plan2.root;
		current2.setContent("root");
		plan2.addSection(current2);
		current2.getChildren().get(1).setContent("goal2");;
		current2 = current2.getChildren().get(0);
		current2.setContent("goal");
		current2.addChild(new Section("Program Goals and Student Learning Objective"));
		current2.getChildren().get(0).setContent("objective1");
		current2.getChildren().get(1).setContent("objective2");
		plan2.setDepartment("CSC");
		plan2.setYear("2021");
		plan2.isEditable = false;
		plan2.setEdit("No");
		server.getStoredBP().add(plan2);	
		server.addPerson(this.defaultUsername, this.defaultPW, "CSC", true);
		return server;
	}

	public DummyNetworkClassFactory() {
		this.server = this.generateTestServer();
		this.client = this.generateTestCliet(this.server);
		client.askForLogin(this.getAdminUsername(), this.getAdminPW());
	}
	
}
