package gamestore.dao;

import gamestore.models.Genre;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenreDAO {
    private final SessionFactory sessionFactory;

    public List<Genre> index(){
        Session session = sessionFactory.openSession();
        return session.createQuery("from Genre ", Genre.class).getResultList();
    }

    public Genre show(String name){
        Session session = sessionFactory.openSession();
        return session.get(Genre.class, name);
    }

    public void save(Genre genre){
        Session session = sessionFactory.openSession();
        session.save(genre);
    }

    public void update(Genre updatedGenre, String previousName){
        Session session = sessionFactory.openSession();
        Genre previousGenre = session.get(Genre.class, String.format("from Genre where name=%s", previousName));
        previousGenre = updatedGenre;
    }

    public void delete(String name){
        Session session = sessionFactory.openSession();
        session.createQuery(String.format("delete from Genre where name=%s", name));
    }
}
