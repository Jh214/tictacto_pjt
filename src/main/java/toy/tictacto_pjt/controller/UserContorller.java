package toy.tictacto_pjt.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.tictacto_pjt.dto.ResponseDto;
import toy.tictacto_pjt.dto.user.LoginDto;
import toy.tictacto_pjt.dto.user.ModifyDto;
import toy.tictacto_pjt.dto.user.SignupDto;
import toy.tictacto_pjt.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api_ttt/user")
public class UserContorller {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody SignupDto signupDto){
        ResponseDto<?> result = userService.signup(signupDto);

        return result;
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginDto loginDto,
                                HttpSession session, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseDto.setFailed("로그인 실패 - " + errorMessage);
        }

        return userService.login(loginDto, session, bindingResult);
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(@RequestParam(name = "userNo") Long userNo, HttpSession session){
        return userService.logout(userNo, session);
    }

    @GetMapping("/info")
    public ResponseDto<?> info(@RequestParam(name = "userNo") Long userNo){
        ResponseDto<?> result = userService.info(userNo);

        return result;
    }

    @PatchMapping("/modify")
    public ResponseDto<?> modify(@RequestParam(name = "userNo")Long userNo, @RequestBody ModifyDto modifyDto){
        ResponseDto<?> result = userService.modify(userNo, modifyDto);

        return result;
    }

    @DeleteMapping("/delete")
    public ResponseDto<?> delete(@RequestParam(name = "userNo")Long userNo){
        return userService.delete(userNo);
    }
}
