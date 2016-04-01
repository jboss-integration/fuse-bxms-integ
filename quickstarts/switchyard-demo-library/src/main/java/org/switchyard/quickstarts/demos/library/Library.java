/*
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.switchyard.quickstarts.demos.library;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.switchyard.quickstarts.demos.library.types.Book;
import org.switchyard.quickstarts.demos.library.types.Loan;
import org.switchyard.quickstarts.demos.library.types.ObjectFactory;


/**
 * The Class Library.
 */
@SuppressWarnings("serial")
public final class Library implements Serializable {

    /** The Constant INSTANCE. */
    private static final Library INSTANCE = new Library();

    /** The Constant ZERO. */
    private static final Integer ZERO = Integer.valueOf(0);

    /** The isbns_to_books. */
    private final Map<String, Book> isbns_to_books = new TreeMap<String, Book>();

    /** The isbns_to_quantities. */
    private final Map<String, Integer> isbns_to_quantities = Collections.synchronizedMap(new TreeMap<String, Integer>());

    /** The librarian. */
    private final Object librarian = new Object();

    /**
     * Instantiates a new library.
     */
    private Library() {
        addBook("978-0-307-35193-7", "World War Z", "An Oral History of the Zombie War", 1);
        addBook("978-0-7360-9829-8", "Successful Sports Officiating", "American Sports Education Program.", 3);
        addBook("978-0-7434-8773-3", "The Time Machine", "H. G. Wells' story of a time traveler.", 8);
        addBook("978-1-101-15402-1", "The Island of Dr. Moreau",
                "H. G. Wells' story of what may be most relevant to modern ethical dimemmas.", 5);
        addBook("978-1-4000-5-80-2", "The Zombie Survival Guide", "Complete Protection from the Living Dead", 6);
        addBook("978-1-448-14153-1", "Doctor Who: Summer Falls", "Story of Amelia Williams.", 2);
        addBook("978-1-4516-7486-6",
                "Tesla, Man Out of Time",
                "Explores the brilliant and prescient mind of one of the twentieth century's greatest scientists and inventors, Nikola Tesla.",
                4);
        addBook("978-1-59474-449-5", "Pride and Prejudice and Zombies",
                "The Classic Regency Romance -- Now with Ultraviolent Zombie Mayhem!", 7);
    }

    /**
     * Adds the book.
     *
     * @param isbn the isbn
     * @param title the title
     * @param synopsis the synopsis
     * @param quantity the quantity
     */
    private void addBook(String isbn, String title, String synopsis, int quantity) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setSynopsis(synopsis);
        isbns_to_books.put(isbn, book);
        isbns_to_quantities.put(isbn, quantity);
    }

    /**
     * Gets the all books.
     *
     * @return the all books
     */
    public Collection<Book> getAllBooks() {
        return isbns_to_books.values();
    }

    /**
     * Gets the available books.
     *
     * @return the available books
     */
    public Collection<Book> getAvailableBooks() {
        synchronized (librarian) {
            Collection<Book> books = new LinkedList<Book>();
            for (Entry<String, Integer> entry : isbns_to_quantities.entrySet()) {
                if (entry.getValue() > 0) {
                    books.add(getBook(entry.getKey()));
                }
            }
            return books;
        }
    }

    /**
     * Gets the book.
     *
     * @param isbn the isbn
     * @return the book
     */
    public Book getBook(String isbn) {
        return isbns_to_books.get(isbn);
    }

    /**
     * Gets the quantity.
     *
     * @param isbn the isbn
     * @return the quantity
     */
    public Integer getQuantity(String isbn) {
        Integer quantity = null;
        if (isbn != null) {
            synchronized (librarian) {
                quantity = isbns_to_quantities.get(isbn);
            }
        }
        return quantity != null ? quantity : ZERO;
    }

    /**
     * Gets the quantity.
     *
     * @param book the book
     * @return the quantity
     */
    public Integer getQuantity(Book book) {
        return book != null ? getQuantity(book.getIsbn()) : ZERO;
    }

    /**
     * Attempt loan.
     *
     * @param isbn the isbn
     * @param loanId the loan id
     * @return the loan
     */
    public Loan attemptLoan(String isbn, String loanId) {
        Loan loan = new Loan();
        loan.setId(loanId);
        Book book = getBook(isbn);
        if (book != null) {
            synchronized (librarian) {
                int quantity = getQuantity(book);
                if (quantity > 0) {
                    quantity--;
                    isbns_to_quantities.put(isbn, quantity);
                    loan.setApproved(true);
                    loan.setNotes("Happy reading! Remaining copies: " + quantity);
                    loan.setBook(book);
                } else {
                    loan.setApproved(false);
                    loan.setNotes("Book has no copies available.");
                }
            }
        } else {
            loan.setApproved(false);
            loan.setNotes("No book matching isbn: " + isbn);
        }
        return loan;
    }

    /**
     * Return loan.
     *
     * @param loan the loan
     * @return true, if successful
     */
    public boolean returnLoan(Loan loan) {
        if (loan != null) {
            Book book = loan.getBook();
            if (book != null) {
                String isbn = book.getIsbn();
                if (isbn != null) {
                    synchronized (librarian) {
                        Integer quantity = isbns_to_quantities.get(isbn);
                        if (quantity != null) {
                            quantity = new Integer(quantity.intValue() + 1);
                            isbns_to_quantities.put(isbn, quantity);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        return toString(false);
    }

    /**
     * To string.
     *
     * @param detailed the detailed
     * @return the string
     */
    public String toString(boolean detailed) {
        StringWriter sw = new StringWriter();
        synchronized (librarian) {
            try {
                if (detailed) {
                    JAXBContext ctx = JAXBContext.newInstance("org.switchyard.quickstarts.demos.library.types");
                    Marshaller m = ctx.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                    ObjectFactory of = new ObjectFactory();
                    for (Book book : isbns_to_books.values()) {
                        int quantity = isbns_to_quantities.get(book.getIsbn());
                        sw.write("\nBook (quantity=" + quantity + ")\n");
                        m.marshal(of.createBook(book), sw);
                        sw.write('\n');
                    }
                } else {
                    for (Book book : isbns_to_books.values()) {
                        int quantity = isbns_to_quantities.get(book.getIsbn());
                        sw.write(book.getTitle() + " (" + quantity + ")\n");
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return sw.toString().trim();
    }

    /**
     * Library.
     *
     * @return the library
     */
    public static final Library library() {
        return INSTANCE;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        System.out.println(library().toString(false));
        System.out.println();
        System.out.println(library().toString(true));
    }

}
