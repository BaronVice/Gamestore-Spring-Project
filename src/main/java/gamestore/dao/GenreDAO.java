package gamestore.dao;

import gamestore.models.Genre;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GenreDAO {
    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<Genre> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Genre", Genre.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Genre show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Genre.class, id);
    }

    @Transactional(readOnly = true)
    public Genre show(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                String.format("from Genre where name='%s'", name), Genre.class
        ).getResultList().stream().findAny().orElse(null);
    }

    @Transactional
    public void save(Genre genre){
        Session session = sessionFactory.getCurrentSession();
        session.save(genre);
    }

    @Transactional
    public void update(Genre updatedGenre, int id){
        Session session = sessionFactory.getCurrentSession();
        Genre previousGenre = session.get(Genre.class, id);

        previousGenre.setName(updatedGenre.getName());
        previousGenre.setDescription(updatedGenre.getDescription());
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Genre.class, id));
    }
}
