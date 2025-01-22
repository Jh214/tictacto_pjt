package toy.tictacto_pjt.entity.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.tictacto_pjt.entity.User_Info;

@Table(name = "Game")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameNo;
    private String gameTitle;
    @Enumerated(EnumType.STRING)
    private GameResult gameResult;

    @ManyToOne
    @JoinColumn(name = "player1_no") // Game 테이블에서 player1_no FK를 가짐
    private User_Info player1;

    @ManyToOne
    @JoinColumn(name = "player2_no") // Game 테이블에서 player2_no FK를 가짐
    private User_Info player2;
}
