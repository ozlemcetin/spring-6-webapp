package guru.springframework.spring6webapp.services;

import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    //Abstract any persistence logic from the controllers by placing it into a service
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /*
     @Override is considered a best practice to help prevent you
     from accidentally implementing a method not defined by the interface.
     */
    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }
}
