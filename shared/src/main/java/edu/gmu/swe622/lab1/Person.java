package edu.gmu.swe622.lab1;

import java.io.Serializable;

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9204713593448563508L;
	public String name;
	public String email;
	public String id;
	
	public String notes;
	
	public Person()
	{
		
	}

	public Person(String id, String name, String email, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", email=" + email + ", notes=" + notes + "]";
	}
	
}
