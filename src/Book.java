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

	/**
	 * String form of the book
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + " author " + author + " borrowed by " + borrowedBy;
	}

	public double computeFineItem(LoanableItem item) {
		double fineTotal = 0.0;
		int totalHrs = 0;
		int fee = 0;
		if (item.isOverDue() && (item instanceof Book)) {
			Book bk = (Book) item;
			if (bk.isReserved()) {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - bk.getDueDate().getTimeInMillis()) / 3600000);
				fineTotal += 1.0 * totalHrs;
			} else {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - item.getDueDate().getTimeInMillis())
						/ 3600000);
				fee = totalHrs / 24;
				if (fee > 24) {
					fineTotal += 0.10;
					fee -= 24;
					if (fee > 0) {
						fineTotal += ((fee / 24) * 0.05);
					}
				}
			}
		}
		return fineTotal;
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