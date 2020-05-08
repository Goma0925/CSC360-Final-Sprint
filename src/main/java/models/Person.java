package models;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person implements Serializable {
	

	private static final long serialVersionUID = -1952576292459976550L;
	public String username;
	public String password;
	public StringProperty department;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public StringProperty getDepartment() {
		return department;
	}

	public void setDepartment(StringProperty department) {
		this.department = department;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Boolean isAdmin;

	public Person() {
		
	}
	
	public Person(String username, String password, String department, Boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.department=new SimpleStringProperty(department);
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString() {
		return "Person [username=" + username + ", password=" + password + ", department=" + department + ", isAdmin="
				+ isAdmin + "]";
	}
	
}
