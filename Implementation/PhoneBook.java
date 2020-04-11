package code;

import java.util.ArrayList;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;


/*
 * AUTHOR: Walid Baroudi
 * Computer Engineering student at Koc University
 * E-mail: wbaroudi18@ku.edu.tr
 * LinkedIn: linkedin.com/in/walid-baroudi
 * Github: github.com/waleedbaroudi
 */


/*
 * A phonebook class that is:
 * - efficiently (O(log n)) searchable by contact name
 * - efficiently (O(log n)) searchable by contact number
 * - searchable by e-mail (O(n))
 *
 * The phonebook assumes that names and numbers will be unique
 */

public class PhoneBook {

	BinarySearchTree<String, ContactInfo> contactsByName;
	BinarySearchTree<String, ContactInfo> contactsByNumber;

	public PhoneBook() {
		contactsByName = new BinarySearchTree<>();
		contactsByNumber = new BinarySearchTree<>();
		contactsByName.setComparator(new DefaultComparator<String>());
		contactsByNumber.setComparator(new DefaultComparator<String>());
	}

	// Returns the number of contacts in the phone book
	public int size() {
		return contactsByName.size();
	}

	// Returns true if the phone book is empty, false otherwise
	public boolean isEmpty() {
		return size() == 0;
	}

	// Adds a new contact or overwrites an existing contact with the given info
	// Args should be given in the order of e-mail and address
	// Note that it can also be empty.
	public void addContact(String name, String number, String... args) {
		int numArgs = args.length;
		String email = null;
		String address = null;

		if (numArgs > 0)
			if (args[0] != null)
				email = args[0];
		if (numArgs > 1)
			if (args[1] != null)
				address = args[1];
		if (numArgs > 2)
			System.out.println("Ignoring extra arguments");

		ContactInfo newContact = new ContactInfo(name, number);
		newContact.setAddress(address);
		newContact.setEmail(email);
		contactsByNumber.put(newContact.getNumber(), newContact);
		contactsByName.put(newContact.getName(), newContact);
	}

	// Return the contact info with the given name
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByName(String name) {
		return contactsByName.get(name);
	}

	// Return the contact info with the given phone number
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByPhone(String phoneNumber) {
		return contactsByNumber.get(phoneNumber);

	}

	// Return the contact info with the given e-mail
	// Return null if it does not exists
	// Can be O(n)
	public ContactInfo searchByEmail(String email) {
		List<ContactInfo> contacts = getContacts();
		for (ContactInfo con : contacts) {
			if (con.getEmail() == null)
				continue;
			if (con.getEmail().equalsIgnoreCase(email))
				return con;
		}
		return null;
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given name to delete, false
	// otherwise
	public boolean removeByName(String name) {
		ContactInfo removed = contactsByName.remove(name);
		if (removed == null)
			return false;
		contactsByNumber.remove(removed.getNumber());
		return true;
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given number to delete, false
	// otherwise
	public boolean removeByNumber(String phoneNumber) {
		ContactInfo removed = contactsByNumber.remove(phoneNumber);
		if (removed == null)
			return false;
		contactsByName.remove(removed.getName());
		return true;
	}

	// Returns the number associated with the name
	public String getNumber(String name) {
		return searchByName(name).getNumber();
	}

	// Returns the name associated with the number
	public String getName(String number) {
		return searchByPhone(number).getName();
	}

	// Update the email of the contact with the given name
	// Returns true if there is a contact with the given name to update, false
	// otherwise
	public boolean updateEmail(String name, String email) {
		ContactInfo found = searchByName(name);
		if (found == null)
			return false;
		found.setEmail(email);
		return true;
	}

	// Update the address of the contact with the given name
	// Returns true if there is a contact with the given name to update, false
	// otherwise
	public boolean updateAddress(String name, String address) {
		ContactInfo found = searchByName(name);
		if (found == null)
			return false;
		found.setAddress(address);
		return true;
	}

	// Returns a list containing the contacts in sorted order by name
	public List<ContactInfo> getContacts() {
		List<ContactInfo> cons = new ArrayList<ContactInfo>();
		List<BinaryTreeNode<String, ContactInfo>> treecons = contactsByName.getNodesInOrder();
		for (BinaryTreeNode<String, ContactInfo> con : treecons) {
			cons.add(con.getValue());
		}
		return cons;
	}

	// Prints the contacts in sorted order by name
	public void printContacts() {
		List<ContactInfo> cons = getContacts();
		for (ContactInfo con : cons)
			System.out.println(con);
	}
}
