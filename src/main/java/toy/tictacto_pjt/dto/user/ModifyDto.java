package toy.tictacto_pjt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDto {
    private String userPw;
    private String userNickName;
}
