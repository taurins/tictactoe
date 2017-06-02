package lv.ctco.TicTacToeCodeNew.dbEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLAYER")
public class PlayerEntity {
    @Id
    @Column(name = "IDPLAYER", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idplayer;
    public int getIdplayer() {
        return idplayer;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 24)
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = GameEntity.class, mappedBy = "player",cascade = CascadeType.ALL)
    private List<GameEntity> gameList = new ArrayList<>();
    public List<GameEntity> getGameList() {
        return gameList;
    }

    public void addToGameList(GameEntity game) {
        gameList.add(game);
        game.setPlayer(this);
    }

}
