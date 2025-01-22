package toy.tictacto_pjt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.tictacto_pjt.entity.User_Info;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_Info, Long> {
    Optional<User_Info> findByUserId(String userId);
}
