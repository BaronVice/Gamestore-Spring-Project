package gamestore.dao;

import gamestore.models.Game;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameDAO {
    private final SessionFactory sessionFactory;

    public List<Game> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Game", Game.class).getResultList();
    }

    public Game show(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Game.class, String.format("from Game where name=%s", name));
    }

    public void save(Game game){
        Session session = sessionFactory.getCurrentSession();
        session.save(game);
    }

    public void update(Game updatedGame, String previousName){
        Session session = sessionFactory.getCurrentSession();
        Game previousGame = session.get(Game.class, String.format("from Game where name=%s", previousName));
        // I guess there will be an exception if primary key is changed... Will find out later
        previousGame = updatedGame;
    }

    public void delete(String name){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(String.format("delete from Game where name=%s", name));
    }
}
