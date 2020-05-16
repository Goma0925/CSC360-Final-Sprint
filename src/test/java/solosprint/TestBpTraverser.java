package solosprint;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import libs.BpTraverser;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import testhelpers.DummyBusinessPlanFactory;
import testhelpers.DummyNetworkClassFactory;

class TestBpTraverser {
	DummyBusinessPlanFactory planFactory = new DummyBusinessPlanFactory();
	MyRemoteClient client;
	MyRemoteImpl server;

	@Test
	void testFlattenBusinessPlan() {
		//This tests if the conversion of a single business plan to an ArrayList is properly done.
		System.out.println("-------Printing a flatten business plan----------");
		System.out.println("	*This test only prints out the results");
		this.initTestServerAndClient();
		
		BusinessPlan plan1 = this.server.getStoredBP().get(1);
		System.out.println("Year:" + plan1.getYear());
		System.out.println("BP:" + plan1.toString());		BpTraverser traverser = new BpTraverser();		
		traverser.flattenBusinessPlan(plan1);
		
		ArrayList<Section> firstFlattenPlan = traverser.getFirstFlattenPlan();
		System.out.println("");
		System.out.println("------Printing out each business plan section------");
		for (int j=0; j<firstFlattenPlan.size();j++) {
			System.out.println(firstFlattenPlan.get(j).toString());
			System.out.println("");
		}	
	};
	
	@Test
	void testFlattenBusinessPlans() {
		//This tests if the conversion of two business plans to an ArrayLists is properly done.
		System.out.println("-------Printing two flatten business plans----------");
		System.out.println("*Each chunk represents the corresponding sections of two business plans");
		System.out.println("	*This test only prints out the results");		this.initTestServerAndClient();
		
		BusinessPlan plan1 = this.server.getStoredBP().get(0);
		System.out.println("Year:" + plan1.getYear());
		System.out.println("BP:" + plan1.toString());
		BusinessPlan plan2 = this.server.getStoredBP().get(1);
		System.out.println("Year:" + plan2.getYear());
		System.out.println("BP:" + plan2.toString());
		BpTraverser traverser = new BpTraverser();		
		traverser.flattenBusinessPlans(plan1, plan2);
		
		ArrayList<Section> firstFlattenPlan = traverser.getFirstFlattenPlan();
		ArrayList<Section> secondFlattenPlan = traverser.getSecondFlattenPlan();
		System.out.println("");
		System.out.println("------Printing out each business plan section------");
		for (int j=0; j<firstFlattenPlan.size();j++) {
			if (firstFlattenPlan.get(j) == null) {
				System.out.println("NULL");
			}else {
				System.out.println(firstFlattenPlan.get(j).toString());
			}
			if (secondFlattenPlan.get(j) == null) {
				System.out.println("NULL");
			}else {
				System.out.println(secondFlattenPlan.get(j).toString());
			}
			System.out.println("");
		}
	}
	
	private void initTestServerAndClient() {
		DummyNetworkClassFactory factory = new DummyNetworkClassFactory();
		this.server = factory.getTestServer();
		this.client = factory.getTestClient();
	};

}
