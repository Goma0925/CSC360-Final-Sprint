package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BYBPlan extends BusinessPlan
{
	public String type;
	public StringProperty department;
	public StringProperty year;
	private static final long serialVersionUID = 1570409654792228146L;
	public String[] sectionNames = {"BYB Mission Statement", "BYB Objectives","BYB Plan"};
	public String[] getSectionNames() {
		return sectionNames;
	}

	public void setSectionNames(String[] sectionNames) {
		this.sectionNames = sectionNames;
	}

	// create an empty tree of BYB plan
	public BYBPlan()
	{
		//MS->Objective->Plan
		root = new Section("BYB Mission Statement");
		Section objective = new Section("BYB Objectives");
		Section plan = new Section("BYB Plan");
		root.addChild(objective);
		objective.addChild(plan);
		plan.setParent(objective);
		objective.setParent(root);
		setHeight(3);
		type = "BYB Plan";
	}
	
	public String getDepartment() {
		return department.getValue();
	}

	public void setDepartment(String dep) {
		department = new SimpleStringProperty(dep);
		
	}

	

	

	// BYB version of add a new Section to the business plan
	public void addSection(Section parent)
	{
		while (parent.name.getValue() != "BYB Plan")
		{
			if (parent.name.getValue() == "BYB Mission Statement")
			{
				Section child = new Section("BYB Objective");
				child.setParent(parent);
				parent.addChild(child);
				//parent = child;
			} else if (parent.name.getValue() == "BYB Objective")
			{
				Section child = new Section("BYB Plan");
				child.setParent(parent);
				parent.addChild(child);
				//parent = child; 
			} else
			{
				System.out.println("ERROR: INVALID SECTION! ! !");
				return;
			}

		}

	}



}
