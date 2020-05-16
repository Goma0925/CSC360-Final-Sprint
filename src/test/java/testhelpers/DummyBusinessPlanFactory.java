package testhelpers;

import models.BusinessPlan;
import models.CNTRAssessment;
import models.Section;

public class DummyBusinessPlanFactory {
	public BusinessPlan generateTestPlan() {
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
		plan.setYear("2019");
		return plan;
	}
	
	public BusinessPlan generateModifiedPlan() {
		BusinessPlan plan = new CNTRAssessment();
		Section current = plan.root;
		current.setContent("root");
		plan.addSection(current);
		current.getChildren().get(1).setContent("goal2");
		current.addChild(new Section("Program Goals and Student Learning Objective"));
		current = current.getChildren().get(0);
		current.setContent("goal");
		current = current.getParent().getChildren().get(1);
		current.addChild(new Section("Program Goals and Student Learning Objective"));
		current.getChildren().get(0).setContent("objective1");
		current.getChildren().get(1).setContent("objective2");
		plan.setDepartment("CSC");
		plan.setYear("2020");
		return plan;
	}
}

