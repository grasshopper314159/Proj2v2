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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class is the super class for all items that can be loaned out by the
 * library. It implements some of the functionality needed to issue, return,
 * remove, and renew a single item.
 * 
 * @author Brahma Dathan
 *
 */
public abstract class LoanableItem implements Matchable<String>, Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String id;
	protected Member borrowedBy;
	protected Calendar dueDate;
	private boolean isReserved = false;
	private List<Hold> holds = new LinkedList<Hold>();
	private int itemFine = 0;
	private Calendar tempCal;

	/**
	 * Takes in the title and id stores them.
	 * 
	 * @param title
	 *            the title of the item
	 * @param id
	 *            the id of the item
	 */
	public LoanableItem(String title, String id) {
		this.id = id;
		this.title = title;
	}

	public LoanableItem(String id) {
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * Issues the item to the member
	 * 
	 * @param member
	 *            The member to whom the item should be issued
	 * @return true iff the operations is successful
	 */
	public boolean issue(Member member) {
		if (borrowedBy != null) {
			return false;
		}
		dueDate = new GregorianCalendar().getInstance();
		borrowedBy = member;
		return true;
	}

	/**
	 * Returns the title of the item
	 * 
	 * @return the title of the item
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the id of the item
	 * 
	 * @return the id of the item
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the borrower of the item
	 * 
	 * @return the borrower of the item
	 */
	public Member getBorrower() {
		return borrowedBy;
	}

	/**
	 * Returns the due date of the item
	 * 
	 * @return the due date of the item
	 */
	public Calendar getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isOverDue() {

		Calendar now = new GregorianCalendar();

		if (dueDate != null) {
			if (now.compareTo(dueDate) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if and only if the supplied id is the same as the id of the
	 * item.
	 */
	@Override
	public boolean matches(String id) {
		return (this.id.equals(id));
	}

	/**
	 * Adds one more hold to the item
	 * 
	 * @param hold
	 *            the new hold on the item
	 */
	public void placeHold(Hold hold) {
		holds.add(hold);
	}

	/**
	 * Removes hold for a specific member
	 * 
	 * @param memberId
	 *            whose hold has to be removed
	 * @return true iff the hold could be removed
	 */
	public boolean removeHold(String memberId) {
		for (ListIterator<Hold> iterator = holds.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			String id = hold.getMember().getId();
			if (id.equals(memberId)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a valid hold
	 * 
	 * @return the next valid hold
	 */
	public Hold getNextHold() {
		for (ListIterator<Hold> iterator = holds.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			iterator.remove();
			if (hold.isValid()) {
				return hold;
			}
		}
		return null;
	}

	/**
	 * Checks whether there is a hold on this item
	 * 
	 * @return true iff there is a hold
	 */
	public boolean hasHold() {
		ListIterator<Hold> iterator = holds.listIterator();
		return (iterator.hasNext());
	}

	/**
	 * Returns an iterator for the holds
	 * 
	 * @return iterator for the holds on the book
	 */
	public Iterator<Hold> getHolds() {
		return holds.iterator();
	}

	/**
	 * Renews the item
	 * 
	 * @param member
	 *            who wants to renew the item
	 * @return true iff the item could be renewed
	 */
	public boolean renew(Member member) {
		if (hasHold()) {
			return false;
		}
		if ((member.getId()).equals(borrowedBy.getId())) {
			return (issue(member));
		}
		return false;
	}

	/**
	 * Marks the item as returned
	 * 
	 * @return the member who had borrowed the item
	 */
	public Member returnItem() {
		if (borrowedBy == null) {
			return null;
		} else {
			Member borrower = borrowedBy;
			borrowedBy = null;
			return borrower;
		}
	}

	public String getConvertedDueDate() {
		if (dueDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY hh:mm");
			return dateFormat.format(dueDate.getTime());
		}
		return dueDate.toString();
	}

	@Override
	public String toString() {
		return "LoanableItem [type= " + this.getClass().getSimpleName() + " title= " + title + ", id=" + id
				+ ", borrowedBy=" + borrowedBy + ", dueDate=" + dueDate + "]";
	}

	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the LoanableItem object
	 */
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}

	// public double computeFine() {
	// double totalFine = 0;
	// LoanableItem item = this;
	// totalFine += item.computeFineItem();
	// // }
	// // if (this instanceof Book) {
	// // Book bk = (Book) this;
	// // totalFine += bk.computeFineItem();
	// // }
	// // if (this instanceof Camera) {
	// // Camera cam = (Camera) this;
	// // totalFine += cam.computeFineItem();
	// // }
	// // if (this instanceof DVD) {
	// // DVD dvd = (DVD) this;
	// // totalFine += dvd.computeFineItem();
	// // }
	// // if (this instanceof Laptop) {
	// // Laptop lap = (Laptop) this;
	// // totalFine += lap.computeFineItem();
	// // }
	// return totalFine;
	//
	// }

	public double computeFineItem() {
		int totalHrs = 0;
		int fee = 0;
		if (this.isOverDue()) {
			if (this.isReserved()) {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - tempCal.getTimeInMillis()) / 3600000);
				itemFine += 1.0 * totalHrs;
				tempCal.add(Calendar.HOUR, totalHrs);
			} else {
				totalHrs += ((Calendar.getInstance().getTimeInMillis() - tempCal.getTimeInMillis()) / 3600000);
				fee = totalHrs / 24;
				if (fee > 24 && (itemFine >= 0.10)) {
					itemFine += ((fee / 24) * 0.05);
				} else {
					itemFine += 0.10;
				}
			}
			tempCal.add(Calendar.HOUR, totalHrs);
		}
		return itemFine;
	}

}
