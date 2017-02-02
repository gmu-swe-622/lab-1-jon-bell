package edu.gmu.swe622.lab1;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddressBookServer implements AddressBook {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: AddressBookServer <port>");
			return;
		}
		int port = Integer.valueOf(args[0]);
		try {
			createAndBind(port);
		} catch (Exception e) {
			System.err.println("AddressBook exception:");
			e.printStackTrace();
		}
	}

	public static Registry createAndBind(int port) throws Exception {
		String name = "AddressBook";
		AddressBook book = new AddressBookServer();
		AddressBook stub = (AddressBook) UnicastRemoteObject.exportObject(book, 0);
		Registry registry = LocateRegistry.createRegistry(port);
		registry.rebind(name, stub);
		System.out.println("AddressBook bound");
		return registry;
	}

	LinkedList<Person> people = new LinkedList<Person>();

	//We'll use a read-write lock to manage the synchronization on people
	ReentrantReadWriteLock peopleLock = new ReentrantReadWriteLock();

	@Override
	public void editPerson(Person p) {
		peopleLock.writeLock().lock();
		try {
			for(Person _p : people)
				if(p.id.equals(_p.id))
				{
					_p.email = p.email;
					_p.name = p.name;
					_p.notes = p.notes;
				}
		} finally {
			peopleLock.writeLock().unlock();
		}
	}

	@Override
	public LinkedList<Person> getAddressBook() {
		peopleLock.readLock().lock();
		try {
			return people;
		} finally {
			peopleLock.readLock().unlock();
		}
	}

	@Override
	public void addPerson(Person p) {
		peopleLock.writeLock().lock();
		try {
			//Search for someone who already has this ID, throw exception if exists
			for(Person _p : people)
				if(p.id.equals(_p.id))
					throw new IllegalArgumentException("Person with id " + p.id + " already exists!");
			people.add(p);
		} finally {
			peopleLock.writeLock().unlock();
		}
	}
}
