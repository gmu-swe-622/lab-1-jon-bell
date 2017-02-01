package edu.gmu.swe622.lab1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public interface AddressBook extends Remote {
	public LinkedList<Person> getAddressBook() throws RemoteException;

	public void addPerson(Person p) throws RemoteException;
}
