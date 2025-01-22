package toy.tictacto_pjt.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import toy.tictacto_pjt.dto.ResponseDto;
import toy.tictacto_pjt.dto.user.LoginDto;
import toy.tictacto_pjt.dto.user.ModifyDto;
import toy.tictacto_pjt.dto.user.SignupDto;

public interface UserService {

    ResponseDto<?> signup(SignupDto signupDto);

    ResponseDto<?> login(LoginDto loginDto, HttpSession httpSession, BindingResult bindingResult);

    ResponseDto<?> logout(Long userNo, HttpSession httpSession);

    ResponseDto<?> info(Long userNo);

    ResponseDto<?> modify(Long userNo, ModifyDto modifyDto);

    ResponseDto<?> delete(Long userNo);
}
