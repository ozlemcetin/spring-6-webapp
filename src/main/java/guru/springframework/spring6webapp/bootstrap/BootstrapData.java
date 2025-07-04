package guru.springframework.spring6webapp.bootstrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Publisher savedPublisher = null;
        {
            Publisher publisher = new Publisher();
            publisher.setPublisherName("My Publisher");
            publisher.setAddress("123 Main");
            savedPublisher = publisherRepository.save(publisher);

            System.out.println("In Bootstrap");
            System.out.println("Publisher Count: " + publisherRepository.count());
        }

        Author savedEric = null;
        {
            Author author = new Author();
            author.setFirstName("Eric");
            author.setLastName("Evans");
            savedEric = authorRepository.save(author);
        }

        //"Saves a given entity.
        // Use the returned instance for further operations
        // as the save operation might have changed the entity instance completely."
        {
            Book book = new Book();
            book.setTitle("Domain Driven Design");
            book.setIsbn("123456");

            Book savedDdd = bookRepository.save(book);
            System.out.println("Gets id after saving: " + savedDdd.getId());

            //Creating associations -alter the object and then persist it
            savedDdd.setPublisher(savedPublisher);
            bookRepository.save(savedDdd);

            //Adding author to the book
            savedDdd.getAuthors().add(savedEric);
            bookRepository.save(savedDdd);

            //Adding book to the author
            savedEric.getBooks().add(savedDdd);
            authorRepository.save(savedEric);
        }

        Author savedRod = null;
        {
            Author author = new Author();
            author.setFirstName("Rod");
            author.setLastName("Johnson");
            savedRod = authorRepository.save(author);
        }

        {
            Book book = new Book();
            book.setTitle("J2EE Development without EJB");
            book.setIsbn("567891");

            Book savedJ2ee = bookRepository.save(book);
            System.out.println("Gets id after saving: " + savedJ2ee.getId());

            //Creating associations -alter the object and then persist it
            savedJ2ee.setPublisher(savedPublisher);
            bookRepository.save(savedJ2ee);

            //Adding author to the book
            savedJ2ee.getAuthors().add(savedRod);
            savedJ2ee.getAuthors().add(savedEric);
            bookRepository.save(savedJ2ee);

            //Adding book to the author
            savedRod.getBooks().add(savedJ2ee);
            savedEric.getBooks().add(savedJ2ee);
            authorRepository.save(savedRod);
        }

        System.out.println("Author count: " + authorRepository.count());
        System.out.println("Book count: " + bookRepository.count());

    }
}
