package gamestore.dao;

import gamestore.models.Game;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GameDAO {
    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<Game> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Game", Game.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Game show(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Game.class, String.format("from Game where name=%s", name));
    }

    @Transactional
    public void save(Game game){
        Session session = sessionFactory.getCurrentSession();
        session.save(game);

        // I'm not sure, but will take a guess that @Cascade will handle this stuff
        
//        Good practice to put this game into genre as well
//        session.merge(game.getGenre());
//        game.getGenre().getGames().add(game);
    }

    @Transactional
    public void update(Game updatedGame, String previousName){
        Session session = sessionFactory.getCurrentSession();
        Game previousGame = session.get(Game.class, String.format("from Game where name=%s", previousName));
        // Remove from old genre and add to new in DB if genre has changed


        // I guess there will be an exception if primary key is changed... Will find out later
        previousGame = updatedGame;
    }

    @Transactional
    public void delete(String name){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(String.format("delete from Game where name=%s", name));
    }
}
