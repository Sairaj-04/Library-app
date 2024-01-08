package com.library.app.service;

import com.library.app.dao.BookRepository;
import com.library.app.dao.CheckoutRepository;
import com.library.app.dao.ReviewRepository;
import com.library.app.entity.Book;
import com.library.app.exception.BookException;
import com.library.app.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    public void addBook(AddBookRequest addBookRequest) throws BookException {
        Book existingBook = bookRepository.findByTitleAndAuthor(addBookRequest.getTitle(), addBookRequest.getAuthor());
        if(existingBook != null) {
            throw new BookException("This book is already present");
        }

        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());

        bookRepository.save(book);
    }

    public void increaseBookQuantity(Long bookId) throws BookException {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new BookException("Book not found");
        }

        book.get().setCopies(book.get().getCopies() + 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
    }

    public void decreaseBookQuantity(Long bookId) throws BookException {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new BookException("Book not found");
        }

        if(book.get().getCopies() <= 0 || book.get().getCopiesAvailable() <= 0) {
            throw new BookException("Quantity locked!");
        }

        book.get().setCopies(book.get().getCopies() - 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        bookRepository.save(book.get());
    }

    public void deleteBook(Long bookId) throws BookException {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new BookException("Book not found");
        }

        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
        bookRepository.delete(book.get());
    }
}
