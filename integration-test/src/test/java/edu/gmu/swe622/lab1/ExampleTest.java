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
								//Add the new person
								client.addPerson(newPerson);
								//Now try to change the notes field
								newPerson.notes = "some notes";
								client.editPerson(newPerson);
								//Now get the address book, try to find this person in it
								Person updatePerson = null;
								for (Person p : client.getAddressBook())
									if (p.id.equals(newPerson.id))
										updatePerson = p;
								
								//Now, make sure that the fields are set correctly on the edited person
								assertNotNull(updatePerson);
								assertEquals(newPerson.email, updatePerson.email);
								assertEquals(newPerson.name, updatePerson.name);
								assertEquals("some notes", updatePerson.notes);
							} catch (RemoteException e) {
								//Any RMI exception should fail the test
								e.printStackTrace();
								fail();
							}
						}
					} catch (AssertionFailedError e) {
						//Junit is really annoying when you have a test failure caused by another thread, 
						// (like this one) the exception prints out but it doesnt fail the test.
						//So, we'll count the # of failures we've seen in tests...
						nTestThreadsFailures++;
						//The astute reader might notice that *this* isn't thread-safe, we might race
						//on incrementing the # of test failures, but in this case, honestly we don't care
						//what the actual number of tests failed is as long as we record >0 if it's >0, so fine.
					}
				}
			});
			//Start each thread, then add it to the list of threads we created to check on
			t.start();
			testThreads.add(t);
		}
		//After we created all of the test threads, call join() on each to make sure that they finish
		for (Thread t : testThreads)
			t.join();
		
		//At this point, we know that all of the test threads were created and ran. Make sure there were
		//no failures in any of the test threads.
		assertEquals(0, nTestThreadsFailures);
	}
}
