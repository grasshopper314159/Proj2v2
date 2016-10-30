package src;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
/**
 * Prints all LoanableItem objects formatted.
 * 
 * @author Brahma Dathan
 *
 */
public class PrintOverdue implements LoanableItemVisitor {
	private static PrintOverdue visitor;

	/**
	 * The constructor is for the singleton pattern
	 */
	private PrintOverdue() {
	}

	/**
	 * Returns the only instance of the class
	 * 
	 * @return the instance of the class
	 */
	public static PrintOverdue instance() {
		if (visitor == null) {
			visitor = new PrintOverdue();
		}
		return visitor;
	}

	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(LoanableItem item) {
		System.out.println("Print " + item + " formatted");
	}

	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(Book book) {
		System.out.println("Print " + book + " formatted");
		Calendar now = new GregorianCalendar();
		if (now.compareTo(book.getDueDate()) < 0) {
			System.out.println("This book is overdue");
		}
	}

	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(Periodical periodical) {
		Calendar now = new GregorianCalendar();
		System.out.println("Print " + periodical + " formatted");
		if (now.compareTo(periodical.getDueDate()) < 0) {
			System.out.println("This book is overdue");
		}
	}

	@Override
	public void visit(DVD dvd) {
		// TODO Auto-generated method stub
		Calendar now = new GregorianCalendar();
		System.out.println("Print " + dvd + " formatted");
		if (now.compareTo(dvd.getDueDate()) < 0) {
			System.out.println("This book is overdue");
		}

	}

	@Override
	public void visit(Laptop laptop) {
		// TODO Auto-generated method stub
		Calendar now = new GregorianCalendar();
		System.out.println("Print " + laptop + " formatted");
		if (now.compareTo(laptop.getDueDate()) < 0) {
			System.out.println("This book is overdue");
		}
	}

	@Override
	public void visit(Camera camera) {
		// TODO Auto-generated method stub
		Calendar now = new GregorianCalendar();
		System.out.println("Print " + camera + " formatted");
		if (now.compareTo(camera.getDueDate()) < 0) {
			System.out.println("This book is overdue");
		}

	}

}
