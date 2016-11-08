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
import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a single book
 * 
 * Nate Branch Test Commit
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Book extends LoanableItem implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	private String author;
	private boolean isReserved = false;
	private int bookFine = 0;

	/**
	 * @return the isReserved
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * @param isReserved
	 *            the isReserved to set
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * Creates a book with the given id, title, and author name
	 * 
	 * @param title
	 *            book title
	 * @param author
	 *            author name
	 * @param id
	 *            book id
	 */
	public Book(String title, String author, String id) {
		super(title, id);
		this.author = author;
	}

	/**
	 * Marks the book as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the book could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			if (isReserved != true) {
				dueDate.add(Calendar.MONTH, 1);
			} else {
				dueDate.add(Calendar.HOUR, 2);
			}
			return true;
		}
		return false;
	}

	/**
	 * Getter for author
	 * 
	 * @return author name
	 */
	public String getAuthor() {
		return author;
	}

	public int getBookFine() {
		return bookFine;
	}

	public void setBookFine(int bookFine) {
		this.bookFine = bookFine;
	}

	/**
	 * String form of the book
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + " author " + author + " borrowed by " + borrowedBy;
	}

	public double computeFineItem() {
		int totalHrs = 0;
		int fee = 0;
		if (this.isOverDue() && (this instanceof Book)) {
			if (this.isReserved()) {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - this.getDueDate().getTimeInMillis())
						/ 3600000);
				bookFine += 1.0 * totalHrs;
			} else {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - this.getDueDate().getTimeInMillis())
						/ 3600000);
				fee = totalHrs / 24;
				if (fee > 24) {
					bookFine += 0.10;
					fee -= 24;
					if (fee > 0) {
						bookFine += ((fee / 24) * 0.05);
					}
				}
			}
		}
		return bookFine;
	}

	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the Book object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}