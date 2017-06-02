package lv.ctco.TicTacToeCodeNew;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by raivis.taurinsh01 on 5/12/2017.
 */
public class DBConnection {
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public void connect(String connectionString){
        factory = Persistence.createEntityManagerFactory(connectionString);
        entityManager = factory.createEntityManager();
    }

    public void disconnect(String disconnectString){
        try {
            DriverManager.getConnection(disconnectString);
        }
        catch (SQLException se) {
            // SQL State XJO15 and SQLCode 50000 mean an OK shutdown.
            if (!(se.getErrorCode() == 50000) && ("XJ015".equals(se.getSQLState()))) {
                System.err.println(se);
            }
        }
        entityManager.close();
        factory.close();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
