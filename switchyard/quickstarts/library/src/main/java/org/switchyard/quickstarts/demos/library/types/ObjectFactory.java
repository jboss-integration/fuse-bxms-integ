//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.04 at 12:38:20 PM EDT 
//


package org.switchyard.quickstarts.demos.library.types;
import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.switchyard.quickstarts.demos.library.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@SuppressWarnings("serial")
@XmlRegistry
public class ObjectFactory implements Serializable {

    private final static QName _Book_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "book");
    private final static QName _Loan_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "loan");
    private final static QName _LoanRequest_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "loanRequest");
    private final static QName _LoanResponse_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "loanResponse");
    private final static QName _ReturnRequest_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "returnRequest");
    private final static QName _ReturnResponse_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "returnResponse");
    private final static QName _Suggestion_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "suggestion");
    private final static QName _SuggestionRequest_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "suggestionRequest");
    private final static QName _SuggestionResponse_QNAME = new QName("urn:switchyard-quickstart-demo:library:1.0", "suggestionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.switchyard.quickstarts.demos.library.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link Loan }
     * 
     */
    public Loan createLoan() {
        return new Loan();
    }

    /**
     * Create an instance of {@link LoanRequest }
     * 
     */
    public LoanRequest createLoanRequest() {
        return new LoanRequest();
    }

    /**
     * Create an instance of {@link LoanResponse }
     * 
     */
    public LoanResponse createLoanResponse() {
        return new LoanResponse();
    }

    /**
     * Create an instance of {@link ReturnRequest }
     * 
     */
    public ReturnRequest createReturnRequest() {
        return new ReturnRequest();
    }

    /**
     * Create an instance of {@link ReturnResponse }
     * 
     */
    public ReturnResponse createReturnResponse() {
        return new ReturnResponse();
    }

    /**
     * Create an instance of {@link Suggestion }
     * 
     */
    public Suggestion createSuggestion() {
        return new Suggestion();
    }

    /**
     * Create an instance of {@link SuggestionRequest }
     * 
     */
    public SuggestionRequest createSuggestionRequest() {
        return new SuggestionRequest();
    }

    /**
     * Create an instance of {@link SuggestionResponse }
     * 
     */
    public SuggestionResponse createSuggestionResponse() {
        return new SuggestionResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Book }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "book")
    public JAXBElement<Book> createBook(Book value) {
        return new JAXBElement<Book>(_Book_QNAME, Book.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Loan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "loan")
    public JAXBElement<Loan> createLoan(Loan value) {
        return new JAXBElement<Loan>(_Loan_QNAME, Loan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "loanRequest")
    public JAXBElement<LoanRequest> createLoanRequest(LoanRequest value) {
        return new JAXBElement<LoanRequest>(_LoanRequest_QNAME, LoanRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "loanResponse")
    public JAXBElement<LoanResponse> createLoanResponse(LoanResponse value) {
        return new JAXBElement<LoanResponse>(_LoanResponse_QNAME, LoanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "returnRequest")
    public JAXBElement<ReturnRequest> createReturnRequest(ReturnRequest value) {
        return new JAXBElement<ReturnRequest>(_ReturnRequest_QNAME, ReturnRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "returnResponse")
    public JAXBElement<ReturnResponse> createReturnResponse(ReturnResponse value) {
        return new JAXBElement<ReturnResponse>(_ReturnResponse_QNAME, ReturnResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Suggestion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "suggestion")
    public JAXBElement<Suggestion> createSuggestion(Suggestion value) {
        return new JAXBElement<Suggestion>(_Suggestion_QNAME, Suggestion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuggestionRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "suggestionRequest")
    public JAXBElement<SuggestionRequest> createSuggestionRequest(SuggestionRequest value) {
        return new JAXBElement<SuggestionRequest>(_SuggestionRequest_QNAME, SuggestionRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuggestionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:switchyard-quickstart-demo:library:1.0", name = "suggestionResponse")
    public JAXBElement<SuggestionResponse> createSuggestionResponse(SuggestionResponse value) {
        return new JAXBElement<SuggestionResponse>(_SuggestionResponse_QNAME, SuggestionResponse.class, null, value);
    }

}
