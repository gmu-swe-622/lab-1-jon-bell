package edu.gmu.swe622.lab1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.After;
import org.junit.Before;

public class AbstractRMITest {

	Registry registry;

	protected AddressBook getClient() throws Exception
	{
		Registry registry = LocateRegistry.getRegistry("localhost", 9000);
		AddressBook addressBook = (AddressBook) registry.lookup("AddressBook");
		return addressBook;
	}
	@Before
	public void createServer() throws Exception {
		registry = AddressBookServer.createAndBind(9000);
	}

	@After
	public void destroyServer() throws Exception {
		registry.unbind("AddressBook");
	}
}
