package toy.tictacto_pjt.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import toy.tictacto_pjt.dto.ResponseDto;
import toy.tictacto_pjt.dto.user.InfoResponseDto;
import toy.tictacto_pjt.dto.user.LoginDto;
import toy.tictacto_pjt.dto.user.ModifyDto;
import toy.tictacto_pjt.dto.user.SignupDto;
import toy.tictacto_pjt.entity.User_Info;
import toy.tictacto_pjt.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseDto<?> signup(SignupDto signupDto) {
        String userId = signupDto.getUserId();
        String userPw = signupDto.getUserPw();
        String userConPw = signupDto.getUserConfirmPw();
        String userNickName = signupDto.getUserNickName();

        Optional<User_Info> optionalUser = userRepository.findByUserId(signupDto.getUserId());

        if(optionalUser.isPresent()) {
            return ResponseDto.setFailed("이미 존재하는 회원입니다.");
        }

        if (!userPw.equals(userConPw)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(userPw);

        // UserEntity 생성
        User_Info user = User_Info.builder()
                .userId(userId)
                .userPw(hashedPassword) // 해시된 비밀번호
                .userNickName(userNickName)
                .build();

        try {
            // db에 사용자 저장
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        return ResponseDto.setSuccess("회원 생성에 성공했습니다.");
    }

    @Override
    public ResponseDto<?> login(LoginDto loginDto, HttpSession httpSession, BindingResult bindingResult) {
        // 컨트롤러에서 Validation 검증 통과
        // 그래도 여기서도 null-safe 처리를 해주는 게 좋습니다.
        String userId = loginDto.getUserId();
        String userPw = loginDto.getUserPw();

        Optional<User_Info> optionalUser = userRepository.findByUserId(userId);

        // 로그인 성공
        if (optionalUser.isPresent()
                && bCryptPasswordEncoder.matches(userPw, optionalUser.get().getUserPw())) {

            String userNickName = optionalUser.get().getUserNickName();
            httpSession.setAttribute("userNickName", userNickName);
            httpSession.setAttribute("userNo", loginDto.getUserNo());

            return ResponseDto.setSuccessData("로그인 성공!", userNickName);
        } else { // 로그인 실패
            // 주로 컨트롤러에서 처리할 때 의미가 크다고 함
            bindingResult.reject("LOGIN_FAILED", "아이디 또는 비밀번호가 올바르지 않습니다.");

            return ResponseDto.setFailed("아이디 또는 비밀번호를 확인하여 주세요.");
        }
    }

    @Override
    public ResponseDto<?> logout(Long userNo, HttpSession httpSession) {
        Optional<User_Info> optionalUser = userRepository.findById(userNo);

        if(optionalUser.isEmpty()){
            return ResponseDto.setFailed("해당 회원을 찾을 수 없습니다.");
        }

        // 세션 제거
        httpSession.removeAttribute("userNickName");
        httpSession.removeAttribute("userNo");

        return ResponseDto.setSuccess("로그아웃이 완료되었습니다");
    }

    @Override
    public ResponseDto<?> info(Long userNo){
        Optional<User_Info> optionalUser = userRepository.findById(userNo);

        if(optionalUser.isEmpty()) {
            return ResponseDto.setFailed("해당 회원을 찾을 수 없습니다.");
        }

        List<InfoResponseDto> userView = optionalUser.stream()
                .map(user -> new InfoResponseDto(
                        user.getUserId(),
                        user.getUserNickName()
                ))
                .collect(Collectors.toList());

        return ResponseDto.setSuccessData("회원 정보를 성공적으로 불러왔습니다.", userView);
    }

    @Override
    public ResponseDto<?> modify(Long userNo, ModifyDto modifyDto) {
        Optional<User_Info> optionalUser = userRepository.findById(userNo);

        if(optionalUser.isEmpty()) {
            return ResponseDto.setFailed("해당 회원을 찾을 수 없습니다.");
        }
        User_Info user = optionalUser.get();

        user.update(modifyDto);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        return ResponseDto.setSuccess("회원 정보가 성공적으로 수정되었습니다.");
    }

    @Override
    public ResponseDto<?> delete(Long userNo) {
        Optional<User_Info> optionalUser = userRepository.findById(userNo);

        if(optionalUser.isEmpty()) {
            return ResponseDto.setFailed("해당 회원을 찾을 수 없습니다.");
        }

        User_Info user = optionalUser.get();

        try {
//            유저 삭제
            userRepository.delete(user);
            return ResponseDto.setSuccess("회원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다: " + e.getMessage());
        }
    }
}
