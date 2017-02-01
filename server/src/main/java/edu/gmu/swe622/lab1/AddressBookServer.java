package edu.gmu.swe622.lab1;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

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

	@Override
	public LinkedList<Person> getAddressBook() {
		return people;
	}

	@Override
	public void addPerson(Person p) {
		people.add(p);
	}
}
