package src;

/**
 * 

 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 
 * This class implements the user interface for the Library project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 *
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Library library;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int ADD_ITEMS = 2;
	private static final int ISSUE_ITEMS = 3;
	private static final int TOGGLE_BOOK_RESERVED = 4;
	private static final int RETURN_ITEMS = 5;
	private static final int RENEW_BOOKS = 6;
	private static final int CHANGE_DUE_DATE = 7;
	private static final int REMOVE_MEMBERS = 8;
	private static final int REMOVE_BOOKS = 9;
	private static final int PLACE_HOLD = 10;
	private static final int REMOVE_HOLD = 11;
	private static final int PROCESS_HOLD = 12;
	private static final int GET_TRANSACTIONS = 13;
	private static final int SAVE = 14;
	private static final int RETRIEVE = 15;
	private static final int PRINT_FORMATTED = 16;
	private static final int PRINT_OVERDUE = 17;
	private static final int PAY_BALANCE = 18;
	private static final int HELP = 19;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			library = Library.instance();
		}
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt
	 *            - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 * 
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt
	 *            the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt
	 *            the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 * 
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 16 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(ADD_ITEMS + " to add items");
		System.out.println(ISSUE_ITEMS + " to issue items to a member");
		System.out.println(TOGGLE_BOOK_RESERVED + " to change a book's reserved status");
		System.out.println(RETURN_ITEMS + " to return items");
		System.out.println(RENEW_BOOKS + " to renew books ");
		System.out.println(CHANGE_DUE_DATE + " to change the due date of an item");
		System.out.println(REMOVE_MEMBERS + " to remove members");
		System.out.println(REMOVE_BOOKS + " to remove books");
		System.out.println(PLACE_HOLD + " to place a hold on a book");
		System.out.println(REMOVE_HOLD + " to remove a hold on a book");
		System.out.println(PROCESS_HOLD + " to process holds");
		System.out.println(GET_TRANSACTIONS + " to print transactions");
		System.out.println(SAVE + " to save data");
		System.out.println(RETRIEVE + " to retrieve");
		System.out.println(PRINT_FORMATTED + " to print items formatted");
		System.out.println(PRINT_OVERDUE + " to print items that are overdue");
		System.out.println(PAY_BALANCE + " to pay member's balance");
		System.out.println(HELP + " for help");
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * member.
	 * 
	 */
	public void addMember() {
		String name = getToken("Enter member name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		Member result;
		result = library.addMember(name, address, phone);
		if (result == null) {
			System.out.println("Could not add member");
		}
		System.out.println(result);
	}

	/**
	 * Method to be called for adding a loanable item. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * item.
	 * 
	 */
	public void addLoanableItems() {
		LoanableItem result;
		do {
			int type;
			do {
				type = getNumber("Enter\n" + Library.BOOK + " for book\n" + Library.PERIODICAL + " for periodical\n"
						+ Library.LAPTOP + " for laptop\n" + Library.CAMERA + " for camera\n" + Library.DVD
						+ " for DVD\n");
			} while (type != Library.BOOK && type != Library.PERIODICAL && type != Library.CAMERA
					&& type != Library.LAPTOP && type != Library.DVD);
			String title = "";
			String author = "";

			if (type == Library.BOOK) {
				title = getToken("Enter title");
				author = getToken("Enter author");
			} else if (type == Library.PERIODICAL) {
				title = getToken("Enter title");
			} else if (type == Library.DVD) {
				title = getToken("Enter title");
			} else if (type == Library.CAMERA) {
				title = getToken("Enter brand");
			} else if (type == Library.LAPTOP) {
				title = getToken("Enter brand");
			}
			String id = getToken("Enter id");

			result = library.addLoanableItem(type, title, author, id);
			if (result != null) {
				System.out.println(result);
			} else {
				System.out.println("Item could not be added");
			}
			if (!yesOrNo("Add more items?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for issuing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for issuing
	 * books.
	 * 
	 */
	public void issueLoanableItems() {
		LoanableItem result;
		Member thisMember;
		String memberID = getToken("Enter member id");
		thisMember = library.searchMembership(memberID);
		if (thisMember == null) {
			System.out.println("No such member");
			return;
		}
		if (thisMember.getBalance() >= 5.0) {
			System.out.println("Items cannot be checked out if your balance is greater than or euqal to $5.00.");
			System.out.println("Currently your balance is " + thisMember.getBalance());
			if (yesOrNo("Would you like to make a payment now to reduce your balance?")) {
				payBalance();
			} else {
				return;
			}
		}

		do {
			String bookID = getToken("Enter item id");
			result = library.issueLoanableItem(memberID, bookID);
			if (result != null) {
				System.out.println(result.getTitle() + "   " + result.getConvertedDueDate());
			} else {
				System.out.println("Item could not be issued");
			}
			if (!yesOrNo("Issue more items?")) {
				break;
			}
		} while (true);
	}

	public void changeReservedStatus() {
		int result;
		do {
			String bookID = getToken("Enter book id");

			result = library.changeReservedStatus(bookID);
			if (result == 7) {
				System.out.println("Reserved is set");
			} else {
				System.out.println("Status not changed");
			}
			if (!yesOrNo("Reserve more books?")) {
				break;
			}
		} while (true);

	}

	/**
	 * Method to be called for renewing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for renewing
	 * books.
	 * 
	 */
	public void renewLoanableItems() {
		LoanableItem result;
		String memberID = getToken("Enter member id");
		if (library.searchMembership(memberID) == null) {
			System.out.println("No such member");
			return;
		}
		Iterator<LoanableItem> issuedItems = library.getLoanableItems(memberID);
		while (issuedItems.hasNext()) {
			LoanableItem book = (issuedItems.next());
			if (yesOrNo(book.getTitle())) {
				result = library.renewItem(book.getId(), memberID);
				if (result != null) {
					System.out.println(result.getTitle() + "   due date: " + result.getDueDate());
				} else {
					System.out.println("Item is not renewable");
				}
			}
		}
	}

	public void changeDueDate() {
		do {
			String itemID = getToken("Enter item id");

			Calendar date = getDate("Please enter the new due date as mm/dd/yy");
			library.changeDueDate(itemID, date);

			if (!yesOrNo("Change More Due Dates?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for returning books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for returning
	 * books.
	 * 
	 */
	public void returnLoanableItems() {
		int result;
		do {
			String bookID = getToken("Enter item id");
			// Add overdue check here
			result = library.returnLoanableItem(bookID);
			switch (result) {
			case Library.ITEM_NOT_FOUND:
				System.out.println("No such Item in Library");
				break;
			case Library.ITEM_NOT_ISSUED:
				System.out.println("Item was not checked out");
				break;
			case Library.ITEM_HAS_HOLD:
				System.out.println("Book has a hold");
				break;
			case Library.OPERATION_FAILED:
				System.out.println("Item could not be returned");
				break;
			case Library.OPERATION_COMPLETED:
				System.out.println("Item has been returned");
				break;
			default:
				System.out.println("An error has occurred");
			}
			if (!yesOrNo("Return more items?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for removing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing
	 * books.
	 * 
	 */
	public void removeLoanableItems() {
		int result;
		do {
			String bookID = getToken("Enter item id");
			result = library.removeLoanableItem(bookID);
			switch (result) {
			case Library.ITEM_NOT_FOUND:
				System.out.println("No such item in Library");
				break;
			case Library.ITEM_ISSUED:
				System.out.println("Item is currently checked out");
				break;
			case Library.ITEM_HAS_HOLD:
				System.out.println("Item has a hold");
				break;
			case Library.OPERATION_FAILED:
				System.out.println("Item could not be removed");
				break;
			case Library.OPERATION_COMPLETED:
				System.out.println(" Item has been removed");
				break;
			default:
				System.out.println("An error has occurred");
			}
			if (!yesOrNo("Remove more books?")) {
				break;
			}
		} while (true);
	}

	public void removeMembers() {
		int result;
		String memberID = getToken("Enter memberID");
		result = library.removeMember(memberID);

	}

	/**
	 * Method to be called for placing a hold. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for placing a
	 * hold.
	 * 
	 */
	public void placeHold() {
		String memberID = getToken("Enter member id");
		String bookID = getToken("Enter book id");
		int duration = getNumber("Enter duration of hold");
		int result = library.placeHold(memberID, bookID, duration);
		switch (result) {
		case Library.ITEM_NOT_FOUND:
			System.out.println("No such Book in Library");
			break;
		case Library.ITEM_NOT_ISSUED:
			System.out.println(" Book is not checked out");
			break;
		case Library.NO_SUCH_MEMBER:
			System.out.println("Not a valid member ID");
			break;
		case Library.HOLD_PLACED:
			System.out.println("A hold has been placed");
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Method to be called for removing a holds. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing a
	 * hold.
	 * 
	 */
	public void removeHold() {
		String memberID = getToken("Enter member id");
		String bookID = getToken("Enter book id");
		int result = library.removeHold(memberID, bookID);
		switch (result) {
		case Library.ITEM_NOT_FOUND:
			System.out.println("No such Book in Library");
			break;
		case Library.NO_SUCH_MEMBER:
			System.out.println("Not a valid member ID");
			break;
		case Library.OPERATION_COMPLETED:
			System.out.println("The hold has been removed");
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Method to be called for processing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for processing
	 * books.
	 * 
	 */
	public void processHolds() {
		Member result;
		do {
			String bookID = getToken("Enter book id");
			result = library.processHold(bookID);
			if (result != null) {
				System.out.println(result);
			} else {
				System.out.println("No valid holds left");
			}
			if (!yesOrNo("Process more books?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for displaying
	 * transactions.
	 * 
	 */
	public void getTransactions() {
		Iterator<Transaction> result;
		String memberID = getToken("Enter member id");
		Calendar date = getDate("Please enter the date for which you want records as mm/dd/yy");
		result = library.getTransactions(memberID, date);
		if (result == null) {
			System.out.println("Invalid Member ID");
		} else {
			while (result.hasNext()) {
				Transaction transaction = result.next();
				System.out.println(transaction.getType() + "   " + transaction.getTitle() + "\n");
			}
			System.out.println("\n  There are no more transactions \n");
		}
	}

	/**
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
	 * 
	 */
	private void save() {
		if (Library.save()) {
			System.out.println(" The library has been successfully saved in the file LibraryData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Library method for retrieval.
	 * 
	 */
	private void retrieve() {
		try {
			Library tempLibrary = Library.retrieve();
			if (tempLibrary != null) {
				System.out.println(" The library has been successfully retrieved from the file LibraryData \n");
				library = tempLibrary;
			} else {
				System.out.println("File doesnt exist; creating new library");
				library = Library.instance();
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * 
	 * Prints the items in a unique format for each type of item.
	 */
	public void printFormatted() {
		library.processLoanableItems(PrintFormat.instance());
	}

	public void printOverdue() {
		library.processLoanableItems(PrintOverdue.instance());
	}

	public void payBalance() {
		Member result;
		String memberID = getToken("Enter member id");
		result = library.validateMember(memberID);
		if (result == null) {
			System.out.println("Invalid member id");
			getCommand();
		}
		double owe = result.calculateBalance();
		System.out.println("Current balance: " + owe);

		do {
			if (owe != 0.0) {
				String pay = getToken("Please enter payment amount: ");
				double payDouble = Double.parseDouble(pay);
				double remain = result.payBalance(payDouble);
				System.out.println("Remaining balance: " + remain);
				break;
			} else {
				System.out.println("No balance to pay.");
				break;
			}
		} while (true);
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalties.
	 * 
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_MEMBER:
				addMember();
				break;
			case ADD_ITEMS:
				addLoanableItems();
				break;
			case ISSUE_ITEMS:
				issueLoanableItems();
				break;
			case TOGGLE_BOOK_RESERVED:
				changeReservedStatus();
				break;
			case RETURN_ITEMS:
				returnLoanableItems();
				break;
			case REMOVE_MEMBERS:
				removeMembers();
				break;
			case REMOVE_BOOKS:
				removeLoanableItems();
				break;
			case RENEW_BOOKS:
				renewLoanableItems();
				break;
			case CHANGE_DUE_DATE:
				changeDueDate();
				break;
			case PLACE_HOLD:
				placeHold();
				break;
			case REMOVE_HOLD:
				removeHold();
				break;
			case PROCESS_HOLD:
				processHolds();
				break;
			case GET_TRANSACTIONS:
				getTransactions();
				break;
			case SAVE:
				save();
				break;
			case RETRIEVE:
				retrieve();
				break;
			case PRINT_FORMATTED:
				printFormatted();
				break;
			case PRINT_OVERDUE:
				printOverdue();
				break;
			case PAY_BALANCE:
				payBalance();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}