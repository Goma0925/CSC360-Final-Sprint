package models;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;



// Concrete server class
public class MyRemoteImpl implements MyRemote {

	private Person loginPerson=null;
	private ArrayList <BusinessPlan> storedBP = new ArrayList<BusinessPlan>();
	private ArrayList <Person> storedUser=new ArrayList<Person>();

  
    public MyRemoteImpl() {
    	
    }

	public Person getLoginPerson() {
		return loginPerson;
	}

	public void setLoginPerson(Person loginPerson) {
		this.loginPerson = loginPerson;
	}

	public ArrayList<Person> getStoredUser() {
		return storedUser;
	}

	public void setStoredUser(ArrayList<Person> storedUser) {
		this.storedUser = storedUser;
	}
	
	public ArrayList<BusinessPlan> getStoredBP() {
		return storedBP;
	}
	
	public void setStoredBP(ArrayList<BusinessPlan> storedBP) {
		this.storedBP = storedBP;
	}

	public String sayHello() {
        return "Hello User!";
    }
    
	public Person verifyLoginPerson(String username, String password) {
    	for(int i=0; i<storedUser.size();i++){
    		if ((storedUser.get(i).username.equals(username))&&(storedUser.get(i).password.equals(password))){
    			loginPerson=storedUser.get(i);
    			System.out.println("user found.");
    			return loginPerson;
    		}
    	}
    	System.out.println("user not found.");
    	return null;
    }
    
    //helper class for checking the person exists or not in the test file
    public Person findPerson(String username, String password, String deparment, Boolean bol) {
    	for(int i=0; i<storedUser.size();i++){
    		if ((storedUser.get(i).username.equals(username))&&(storedUser.get(i).password.equals(password))){
    			Person personfound=storedUser.get(i);
    			System.out.println("user found.");
    			return personfound;
    		}
    	}
    	System.out.println("user not found.");
    	return null;
    }
    
    public void addPerson(String username, String password, String department, Boolean isAdmin) {
    	Person newperson=new Person(username,password,department,isAdmin);
    	storedUser.add(newperson);
    	System.out.println("New User:"+username+" added.");
    }
    
    public void changeEditable(int year, boolean bool) {
    	BusinessPlan bpcur=null;
    	if(loginPerson==null) {
    		System.out.println("PLEASE LOGIN FIRST.");
    	}
    	else {
    		for (int i=0; i<storedBP.size();i++){
        		if((storedBP.get(i).department.equals(loginPerson.department))&&(Integer.parseInt(storedBP.get(i).year.getValue())==year)){
        			bpcur=storedBP.get(i);
        		}
    		}
    		if(bpcur!=null) {
    			bpcur.isEditable=bool;
    			if(bool == true)
    			{
    				bpcur.edit.setValue("Yes");
    			}
    			else
    			{
    				bpcur.edit.setValue("No");
    			}
    			System.out.println("BusinessPlan isEditable changed to: "+bool);
    		}
    		else {
    			System.out.println("BusinessPlan not found.");
    		}
    	}
    	
    }
    
    public void logOut() {
    	loginPerson=null;
    	System.out.println("user logout from Server.");
    }
      
    //called by client askForBP function
    public BusinessPlan findBP(int year) {
    	if(loginPerson==null) {
    		System.out.println("PLEASE LOGIN FIRST.");
    		return null;
    	}
    	for (int i=0; i<storedBP.size();i++){
    		if((storedBP.get(i).department.equals(loginPerson.department))&&(Integer.parseInt(storedBP.get(i).year.getValue())==year)){
    			System.out.println("BusinessPlan found.");
    			return storedBP.get(i);
    		}
    	}
    	System.out.println("BusinessPlan not found.");
    	return null;
    }
    
    //called by client uploadBP function
    public String addBP(BusinessPlan BP) {
    	String Message="";
    	if(loginPerson==null) {
    		Message="PLEASE LOGIN FIRST.";
    		System.out.println(Message);
    		return Message;
    	}
    	for (int i=0; i<storedBP.size();i++){
    		BusinessPlan current=storedBP.get(i);

    		if((current.department.equals(BP.department))&&(current.year==BP.year)){
    			System.out.println("Business Plan already exists.");
    			if(current.isEditable==false) {
    				Message="This BusinessPlan is not Editable";
        			System.out.println(Message);
        			return Message;
    			}
    			storedBP.remove(current);
    			storedBP.add(BP);
    			Message="Replaced Old Version BP with New One.";
    			System.out.println(Message);
    			return Message;
    		}
    	}
    	storedBP.add(BP);
    	System.out.println("Business does not exist.");
    	Message="Added new BP to Server";
    	System.out.println(Message);
    	return Message;
    }
    
    //save all data to the disk every two minutes 
  	//timeInterval should be set to 1000*120 when call the function
  	public void startEncode(long timeInterval) {
          Runnable runnable = new Runnable() {
          	public void run() {
          		while (true) {
          			// ------- code for task to run	      
          			XMLEncodeAllData();
          			System.out.println("Server has automatically Encode all Data.");
          			// ------- ends here	      
          			try {
          				Thread.sleep(timeInterval);
          				} 
          			catch (InterruptedException e) {
          				e.printStackTrace();
          				}	      
          			}	    
          		}	  
          	};	  	  
          	Thread thread = new Thread(runnable);
          	thread.start();
  	}
  	

	//Encoder
	public void XMLEncodeAllData() {
		String BusinessPlan_File="Server_BusinessPlan.xml";
		String User_File="Server_User.xml";
		XMLEncodeBP(BusinessPlan_File);
		XMLEncodeUser(User_File);
	}
	
    //helperEncodeFunction
	public void XMLEncodeBP(String filename) {
		XMLEncoder encoder=null;
		try{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
			}
		catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File"+filename);
			}
			encoder.writeObject(this.storedBP);
			encoder.close();
	}
	
	public void XMLEncodeUser(String filename) {
		XMLEncoder encoder=null;
		try{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
			}
		catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File"+filename);
			}
			encoder.writeObject(this.storedUser);
			encoder.close();
	}
	
	//Decoder
	//Call two decoder functions to read all data from the disk
	//When the server starts, we can call this two function first
	//Since we set the filename of encoder's and decoder's files are the same, 
	//The server will always store and read from the same files.
	@SuppressWarnings("unchecked")
  public ArrayList <BusinessPlan> XMLDecodeBP() {
		String BusinessPlan_File="Server_BusinessPlan.xml";
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(BusinessPlan_File)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File "+BusinessPlan_File+" not found");
		}
		return (ArrayList<BusinessPlan>)decoder.readObject();
	}
	
	@SuppressWarnings("unchecked")
  public ArrayList <Person> XMLDecodeUser() {
		String User_File="Server_User.xml";
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(User_File)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File "+User_File+" not found");
		}
		return (ArrayList <Person>)decoder.readObject();
	}
	
    public static void main(String args[]) {
        try {
        	MyRemoteImpl obj = new MyRemoteImpl();
        	MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(obj, 0);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("MyRemote", stub);

            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}