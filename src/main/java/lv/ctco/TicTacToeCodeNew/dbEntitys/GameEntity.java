package lv.ctco.TicTacToeCodeNew.dbEntitys;

import javax.persistence.*;

@Entity
@Table(name = "GAME")
public class GameEntity {
    @Id
    @Column(name = "IDGAME", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idgame;
    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idgame) {
        this.idgame = idgame;
    }

    @Basic
    @Column(name = "PLAYER_SIMBOL", nullable = true, length = 1)
    private String playerSimbol;
    public String getPlayerSimbol() {
        return playerSimbol;
    }

    public void setPlayerSimbol(String playerSimbol) {
        this.playerSimbol = playerSimbol;
    }

    @Basic
    @Column(name = "MOVES", nullable = true, length = 9)
    private String moves;
    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    @ManyToOne(targetEntity = PlayerEntity.class, fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "PLAYERID",referencedColumnName = "IDPLAYER")
    private PlayerEntity player;
    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }
}
