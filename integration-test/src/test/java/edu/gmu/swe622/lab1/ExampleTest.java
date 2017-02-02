package edu.gmu.swe622.lab1;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.LinkedList;

import junit.framework.AssertionFailedError;

import org.junit.Test;

public class ExampleTest extends AbstractRMITest {
	// @Test
	// public void testCreateClient() throws Exception {
	// AddressBook client = getClient();
	// client.addPerson(new Person("Test", "test@test.com", null));
	// assertEquals(1, client.getAddressBook().size());
	// }

	int nTestThreadsFailures;

	@Test
	public void testThreads() throws Exception {
		LinkedList<Thread> testThreads = new LinkedList<Thread>();
		nTestThreadsFailures = 0;
		AddressBook client = getClient();
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						for (int j = 0; j < 10; j++) {
							// Use the thread ID as part of the ID, since each
							// thread gets a unique ID
							Person newPerson = new Person(Thread.currentThread().getId() + "-" + j, "Person " + j, "a@a.a", null);
							try {
								client.addPerson(newPerson);
								newPerson.notes = "some notes";
								client.editPerson(newPerson);
								Person updatePerson = null;
								for (Person p : client.getAddressBook())
									if (p.id.equals(newPerson.id))
										updatePerson = p;
								assertNotNull(updatePerson);
								assertEquals(newPerson.email, updatePerson.email);
								assertEquals(newPerson.name, updatePerson.name);
								assertEquals("some notes", updatePerson.notes);
							} catch (RemoteException e) {
								e.printStackTrace();
								fail();
							}
						}
					} catch (AssertionFailedError e) {
						nTestThreadsFailures++;
					}
				}
			});
			t.start();
			testThreads.add(t);
		}
		for (Thread t : testThreads)
			t.join();
		assertEquals(0, nTestThreadsFailures);
	}
}
