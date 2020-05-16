package libs;

import java.util.ArrayList;
import java.util.Stack;

import models.BusinessPlan;
import models.Section;

public class BpTraverser {
	private ArrayList<Section> firstFlattenPlan = null;
	private ArrayList<Section> secondFlattenPlan = null;
	
	public void flattenBusinessPlans(BusinessPlan bp1, BusinessPlan bp2) {
		//Simultaneously traverse two trees in business plans and put the sections in two lists.
		//Creates a two item array of ArrayList, each of which contains sections of each business plan
		//In the ArrayList, if one section does not exit in one businessplan, but does exit in the other,
		//a null will be added to the ArrayList of the business plan that does not have the section.
		this.firstFlattenPlan = new ArrayList<Section>();
		this.secondFlattenPlan = new ArrayList<Section>();
		Stack<Section> stack1 = new Stack<Section>();
		Stack<Section> stack2 = new Stack<Section>();
		Section current1 = bp1.getRoot();
		Section current2;
		stack1.push(bp1.getRoot());
		stack2.push(bp2.getRoot());
		
		//Traverser two trees of the business plans simultaneously. Depth first search	
		while (stack1.size() != 0 && stack2.size() != 0) {
			current1 = stack1.pop();
			current2 = stack2.pop();
			this.firstFlattenPlan.add(current1);
			this.secondFlattenPlan.add(current2);
			//Check how many children each node has. if the node is null, put 0 in the number of children.
			int bp1ChildNum;
			try {
				bp1ChildNum = current1.getChildren().size();
			}catch(Exception e) {
				bp1ChildNum = 0;
			}
			int bp2ChildNum;
			try {
				bp2ChildNum = current2.getChildren().size();
			}catch(Exception e) {
				bp2ChildNum = 0;
			}
			
			//Take the biggest number between the length of the children.			
			int childNum = this.getBiggest(bp1ChildNum, bp2ChildNum);
			//Start from the last child to get the desired output in the ArrayList
			for (int i=childNum-1; i>-1; i--) {
				//Put the section in BP1 to the stack
				try {
					stack1.push(current1.getChildren().get(i));
				}catch(Exception e) {
					stack1.push(null);
				}
				//Put the section in BP2 to the stack
				try {
					stack2.push(current2.getChildren().get(i));
				}catch(Exception e) {
					stack2.push(null);
				}
			}
		}
	};
	
	private int getBiggest(int a, int b) {
		//returns a bigger of two ints. 
		if (a>b) {return a;}
		else {return b;}
	};
	
	public void flattenBusinessPlan(BusinessPlan plan) {
		//The results ArrayList are stored in both firstFlattenPlan & secondFlattenPlan
		//This is to share the API with the flattenBusinessPlans(BusinessPlan, BusinessPlan) method.
		this.firstFlattenPlan = new ArrayList<Section>();
		this.secondFlattenPlan = new ArrayList<Section>();
		Stack<Section> stack = new Stack<Section>();
		stack.push(plan.getRoot());
		Section current;
		//Traverser the business plan. Depth first search	
		while (stack.size() != 0) {
			current = stack.pop();
			this.firstFlattenPlan.add(current);
			this.secondFlattenPlan.add(current);
			int childNum = current.getChildren().size();
			for (int i=childNum-1; i>-1; i--) {
				//Put the section in BP1 to the stack
				stack.push(current.getChildren().get(i));
			}
		}
	}
	
	public ArrayList<Section> getFirstFlattenPlan(){
		return this.firstFlattenPlan;
	}
	
	public ArrayList<Section> getSecondFlattenPlan(){
		return this.secondFlattenPlan;
	}
	
}
