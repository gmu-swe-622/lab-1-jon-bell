package edu.gmu.swe622.lab1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddressBookClient {

	public static void main(String[] args) {
		if(args.length != 1)
		{
			System.out.println("Usage: AddressBookClient <port>");
			return;
		}
		int port = Integer.valueOf(args[0]);
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", port);
			AddressBook addressBook = (AddressBook) registry.lookup("AddressBook");

			System.out.println(addressBook.getAddressBook());
			addressBook.addPerson(new Person("Prof Bell","bellj@gmu.edu",""));

		} catch (Exception e) {
			System.err.println("Client Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
