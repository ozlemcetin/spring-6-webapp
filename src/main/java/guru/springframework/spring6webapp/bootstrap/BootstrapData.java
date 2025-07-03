package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PulisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PulisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PulisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //"Saves a given entity.
        // Use the returned instance for further operations
        // as the save operation might have changed the entity instance completely."
        {
            Book book = new Book();
            book.setTitle("Domain Driven Design");
            book.setIsbn("123456");

            Book savedDdd = bookRepository.save(book);
            System.out.println("Gets id after saving: " + savedDdd.getId());

            Author author = new Author();
            author.setFistName("Eric");
            author.setLastName("Evans");
            Author savedEric = authorRepository.save(author);

            //Creating associations -alter the object and then persist it
            savedEric.getBooks().add(savedDdd);
            authorRepository.save(savedEric);
        }

        {
            Book book = new Book();
            book.setTitle("J2EE Development without EJB");
            book.setIsbn("567891");

            Book savedJ2ee = bookRepository.save(book);
            System.out.println("Gets id after saving: " + savedJ2ee.getId());

            Author author = new Author();
            author.setFistName("Rod");
            author.setLastName("Johnson");
            Author savedRod = authorRepository.save(author);

            //Creating associations -alter the object and then persist it
            savedRod.getBooks().add(savedJ2ee);
            authorRepository.save(savedRod);
        }

        System.out.println("In Bootstrap");
        System.out.println("Author count: " + authorRepository.count());
        System.out.println("Book count: " + bookRepository.count());

        {
            Publisher publisher = new Publisher();
            publisher.setPublisherName("My Publisher");
            publisher.setAddress("123 Main");
            publisherRepository.save(publisher);

            System.out.println("Publisher Count: " + publisherRepository.count());
        }
    }
}
