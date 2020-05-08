package models;

public class MyRemoteClient {

	private BusinessPlan currentBP=null;
	private Person loginPerson=null;
	private MyRemoteImpl server;
	
	public MyRemoteClient(MyRemoteImpl server) {
		this.setServer(server);
	}
	
	public BusinessPlan getCurrentBP() {
		return currentBP;
	}

	public void setCurrentBP(BusinessPlan currentBP) {
		this.currentBP = currentBP;
	}

	public Person getLoginPerson() {
		return loginPerson;
	
	}
	
    public static void main(String[] args) {

    }
    
    public void Hello() {
    	String response = getServer().sayHello();
		System.out.println("Response: " + response );
    } 
    
    public boolean askForLogin(String username, String password) {
		loginPerson=getServer().verifyLoginPerson(username,password);
		if(loginPerson!=null) {
			System.out.println("User: " + loginPerson.username+" logined.");
			return true;
		}
		else {
			System.out.println("Wrong username password combination. ");
			return false;
		}
	}
	
    public void logOut() {
    	loginPerson=null;
    	getServer().logOut();
		System.out.println("user logout from Client side.");
    }
    
    public void changeEditable(int year,boolean bol) {
    	if(loginPerson.isAdmin==true) {
    		getServer().changeEditable(year,bol);
    	}
    	else {
    		System.out.println("Sorry, You're not a admin. You CAN'T change a BusinessPlan's isEditable.");
    	}
    }
    
    public void addPerson(String username, String password, String department, Boolean isAdmin) {
    	if(loginPerson.isAdmin==true) {
    		getServer().addPerson(username,password,department,isAdmin);
    	}
    	else {
    		System.out.println("Sorry, You're not a admin. You CAN'T add a person");
    	}
    }
    
    public void askForBP(int year){
    	currentBP=getServer().findBP(year);
    	if(currentBP!=null) {
    		System.out.println("currentBP found.");
		}
    	else {
    		System.out.println("currentBP not found.");
    	}
    }
    
	public void newBP(String Type) {
		if(Type=="VMOSA") {
			BusinessPlan BP = new VMOSA();
			currentBP=BP;
			currentBP.department=loginPerson.department;
		}
		else if(Type=="BYBPlan")
		{
			BusinessPlan BP = new BYBPlan();
			currentBP=BP;
			currentBP.department=loginPerson.department;
		}
		else if(Type=="CNTRAssssment") {
			BusinessPlan BP = new CNTRAssessment();
			currentBP=BP;
			currentBP.department=loginPerson.department;
		}
		else {
			System.out.println("The Type of the BusinessPlan is not available to create.");
		}
	}
	public void cloneBP(String year, BusinessPlan current) 
	{
		BusinessPlan plan = null;
		try {
			plan = (BusinessPlan)current.cloneBP();
			plan.setYear(year);
			getServer().addBP(plan);
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	//upload BP after create a new BP or revised the old one
    public void uploadBP() {
    	if((Integer.parseInt(currentBP.year.getValue())) <  1819) {
    		System.out.println("Failed. Please use appropriate year attribute.");
    	}
    	else {
    		String Message=getServer().addBP(currentBP);	
			System.out.println("Response: " + Message);
    	}
    }

	public MyRemoteImpl getServer() {
		return server;
	}

	public void setServer(MyRemoteImpl server) {
		this.server = server;
	}

}
    
