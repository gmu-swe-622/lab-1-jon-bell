package edu.gmu.swe622.lab1;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExampleTest extends AbstractRMITest {
	@Test
	public void testCreateClient() throws Exception {
		AddressBook client = getClient();
		client.addPerson(new  Person("Test", "test@test.com", null));
		assertEquals(1, client.getAddressBook().size());
	}
}
