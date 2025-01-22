package toy.tictacto_pjt.entity.game;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameResult {
    WIN("승리"),
    LOSE("패배");

    private final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}
