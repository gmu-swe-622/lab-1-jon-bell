#SWE 622 Spring 2017 Lab 1

In this lab, we'll play around with RMI, threads, and locking. You can work in groups of up to 2 people on this lab. It will be graded on a pass/fail basis - if you make some reasonable attempt at this, you pass, regardless of if you did everything right or not.

This template code contains a setup for an RMI client + server, with the server exposing two methods:
`edu.gmu.swe622.lab1.AddressBook.getAddressBook()` and `edu.gmu.swe622.lab1.AddressBook.addPerson(..)`.

The server maintains an address book, which is a list of `Person` objects. The client can query the server for the contents of the address book, or add new people to it. You can run it like this:

1. `mvn package` in this directory.
2. Run a server: `java -jar server/target/lab1-rpc-server-0.0.1-SNAPSHOT.jar 9000` (or replace 9000 with whatever port number you would like to listen on)
3. Run a client: `java -jar client/target/lab1-rpc-client-0.0.1-SNAPSHOT.jar 9000` (or replace 9000 with whatever port number you set the server to listen on)

Here are your tasks:
1. Make the server thread-safe. As it is, if multiple clients simultaneously access the server, for instance, calling the addPerson() method, the data may become corrupt.
2. Add an "editPerson" functionality. You should come up with a mechanism for tracking unique people (perhaps, with an ID), and create a new method for this. Make sure that this is thread-safe too!
3. Add some JUnit client tests: these tests should create many threads (say, 50), and in each thread, attempt to create, edit, and list the address book. These tests should be added in the lab-1-rpc-tests project (in the integration-test directory). 

When you are done, list the names of students who worked in your group here:
<your names>

Then, commit and push to github, and create a release.
