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
 * Represents a single Camera
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Camera extends LoanableItem implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	// private String title;

	/**
	 * Creates a Camera with the given id, title, and author name
	 * 
	 * @param title
	 *            Camera title
	 * @param author
	 *            author name
	 * @param id
	 *            Camera id
	 */
	public Camera(String brand, String id) {
		super(brand, id);
		// this.title = brand;
	}

	/**
	 * Marks the Camera as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the Camera could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			// dueDate.add(Calendar.HOUR, 3);
			dueDate.add(Calendar.HOUR, 3);
			return true;
		}
		return false;
	}

	/**
	 * Getter for author
	 * 
	 * @return author name
	 */
	// public String getBrand() {
	// return brand;
	// }

	/**
	 * String form of the Camera
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + " Camera brand " + super.getTitle() + " " + super.getId() + " borrowed by "
				+ borrowedBy;
	}

	public double computeFineItem() {
		double fineTotal = 0.0;
		int totalHrs = 0;
		int fee = 0;
		if (this.isOverDue()) {
			totalHrs += ((Calendar.getInstance().getTimeInMillis() - this.getDueDate().getTimeInMillis()) / 3600000);
			fee = totalHrs / 24;
			if (fee > 24) {
				fineTotal += 0.10;
				fee -= 24;
				if (fee > 0) {
					fineTotal += ((fee / 24) * 0.05);
				}
			}
		}
		return fineTotal;
	}

	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the Camera object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}