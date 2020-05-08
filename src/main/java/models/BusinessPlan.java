package models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class BusinessPlan implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8935223403182446534L;
	public Section root;
	public StringProperty department;
	public StringProperty year;
	public boolean isEditable = true;
	StringProperty content = new SimpleStringProperty();
	private int height; 
	public StringProperty edit = new SimpleStringProperty("Yes");
	public String getEdit() {
		return edit.getValue();
	}

	public void setEdit(String edit) {
		this.edit.setValue(edit);
	}

	@Override
	public String toString() {
		return "BusinessPlan [root=" + root + ", department=" + department + ", year=" + year + ", isEditable="
				+ isEditable + "]";
	}

	public abstract void addSection(Section parent);// the only abstract method
	//if needed in the future, we may change the abstract method and let it throw Exceptions, but for now
	//we think print out is enough. And we also may simplify it. 

	public void deleteSection(Section child)
	{
		// check the node can be deleted or not
		// delete the whole branch
		// in order to keep a complete structure of the tree, we need to make sure that
		// except for the last section, all the other sections must have at least one
		// child.

		Section parent = child.getParent();
		System.out.println(parent);
		System.out.println(parent.getChildren());
		ArrayList<Section> children = parent.getChildren();
		
		int size = children.size();
		if (size != 1)
		{
			parent.deleteChild(child);
			
		} else
		{
			System.out.println("ERROR: THE SECTION CANNOT BE DELETED!!!");			
		}

	}
	public boolean isDeletable(Section child)
	{
		// check the node can be deleted or not
		// delete the whole branch
		// in order to keep a complete structure of the tree, we need to make sure that
		// except for the last section, all the other sections must have at least one
		// child.

		Section parent = child.getParent();
		if (parent == null)
		{
			return false;
		}
		ArrayList<Section> children = parent.getChildren();
		
		int size = children.size();
		if (size != 1)
		{
			return true;
			
		} else
		{
			return false;	
		}

	}

	public String getYear() {
		return year.getValue();
	}

	public void setYear(String year) {
		this.year = new SimpleStringProperty(year);
	}
	public String getDepartment() {
		return department.getValue();
	}

	public void setDepartment(String department) {
		this.department = new SimpleStringProperty(department);
	}
	// encode the business plan to XML file
	public void encodeToXML(String fileName)
	{
		final String SERIALIZED_FILE_NAME = fileName;

		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		} 
		catch (NullPointerException | FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File");
		}
		encoder.writeObject(this);
		encoder.close();
	}

	// decode the business plan from a XML file
	public BusinessPlan decodeFromXML(String fileName)
	{

		final String SERIALIZED_FILE_NAME = fileName;
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} 
		catch (NullPointerException | FileNotFoundException e)
		{
			System.out.println("ERROR: File not found");
		}
		BusinessPlan plan = (BusinessPlan) decoder.readObject();
		System.out.println(plan);
		return plan;
	}

	// getter of root for XML
	public Section getRoot()
	{
		return root;
	}

	public void setRoot(Section root)
	{
		this.root = root;
	}
	public Object cloneBP() throws CloneNotSupportedException
	{
		return super.clone(); 
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public abstract String[] getSectionNames();
	

}
