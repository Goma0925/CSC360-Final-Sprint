package models;

public class CNTRAssessment extends BusinessPlan
{
	public String type;
	private static final long serialVersionUID = -235542752097413334L;
	public String[] sectionNames = {"Centre College Institutional Mission Statement", "Program Mission Statement" ,"Program Goals and Student Learning Objective"};
	public String[] getSectionNames() 
	{
		return sectionNames;
	}

	public void setSectionNames(String[] sectionNames) {
		this.sectionNames = sectionNames;
	}

	// create an empty tree for CNTR Assessment
	public CNTRAssessment()
	{
		root = new Section("Centre College Institutional Mission Statement");
		Section objective = new Section("Program Goals and Student Learning Objective");
		Section progMission = new Section("Program Mission Statement");
		progMission.addChild(objective);
		root.addChild(progMission);
		progMission.setParent(root);
		objective.setParent(progMission);
		setHeight(3);
		type = "CNTRAssessment";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// add new section to the Assessment
	public void addSection(Section parent)
	{
		while (parent.name.getValue() != "Program Goals and Student Learning Objective")
		{
			if (parent.name.getValue() == "Centre College Institutional Mission Statement")
			{
				Section child = new Section("Program Mission Statement");
				child.setParent(parent);
				parent.addChild(child);
				parent = child;
			} else if (parent.name.getValue() == "Program Mission Statement")
			{
				Section child = new Section("Program Goals and Student Learning Objective");
				child.setParent(parent);
				parent.addChild(child);
				parent = child;
			} else
			{
				System.out.println("ERROR: INVALID SECTION! ! !");
				return;
			}

		}

	}




	

}
