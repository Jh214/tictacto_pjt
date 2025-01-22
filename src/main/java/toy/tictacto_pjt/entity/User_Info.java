package toy.tictacto_pjt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.tictacto_pjt.dto.user.ModifyDto;
import toy.tictacto_pjt.entity.game.Game;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userPw;
    @Column(unique = true, nullable = false)
    private String userNickName;

     @OneToMany(mappedBy = "player1")
     private List<Game> player1 = new ArrayList<>();

     @OneToMany(mappedBy = "player2")
     private List<Game> player2 = new ArrayList<>();

    public void update(ModifyDto modifyDto){
        this.userPw = modifyDto.getUserPw();
        this.userNickName = modifyDto.getUserNickName();
    }
}
