package testhelpers;

import models.BusinessPlan;
import models.CNTRAssessment;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;

public class DummyNetworkClassFactory {
	public MyRemoteClient generateTestCliet(MyRemoteImpl server) {
		MyRemoteClient client;
		client = new MyRemoteClient(server);
		return client;
	}
	
	public MyRemoteImpl generateTestServer() {
		MyRemoteImpl server;
		BusinessPlan plan = new CNTRAssessment();
		Section current = plan.root;
		current.setContent("root");
		plan.addSection(current);
		current.getChildren().get(1).setContent("goal2");;
		current = current.getChildren().get(0);
		current.setContent("goal");
		current.addChild(new Section("Program Goals and Student Learning Objective"));
		current.getChildren().get(0).setContent("objective1");
		current.getChildren().get(1).setContent("objective2");
		plan.setDepartment("CSC");
		plan.setYear("2020");
		//Registry registry = LocateRegistry.createRegistry(1099);
		server = new MyRemoteImpl();
		server.getStoredBP().add(plan);
		
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
		server.addPerson("X", "1", "CSC", true);
		return server;
	}
	
}
