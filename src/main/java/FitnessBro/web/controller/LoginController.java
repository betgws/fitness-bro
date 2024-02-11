package FitnessBro.web.controller;

import FitnessBro.service.OAuth2Service.GoogleService;
import FitnessBro.service.OAuth2Service.KakaoService;
import FitnessBro.service.LoginService.LoginService;
import FitnessBro.service.MemberService.MemberCommandService;
import FitnessBro.service.OAuth2Service.KakaoService;
import FitnessBro.service.OAuth2Service.NaverService;
import FitnessBro.web.dto.Login.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final GoogleService googleService;
    private final MemberCommandService memberCommandService;
    private final LoginService loginService;

    @PostMapping("/select")
    @Operation(summary = "회원가입 동네형, 유저 선택", description ="회원가입 동네형 유저 선택" )
    public ResponseEntity<String> Select(@RequestHeader(value = "token") String token, @RequestBody @Valid LoginRequestDTO request){
        // 위에 RequestHeader에서 token 가져옴
        // token으로 이메일 가져옴
        String userEmail = loginService.decodeJwt(token);
        // 이메일 이용해서 유저 아이디 가져올 수 있음
        Long userId = loginService.getIdByEmail(userEmail);


        memberCommandService.classifyUsers(userEmail, request.getRole());

        return ResponseEntity.ok().body("select 성공");
    }



    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<String> KakaoLogin(@RequestParam("code") String code) {

        // member entity member_id가 String이 아니라서 개판으로 짜임


        ResponseEntity<String> stringResponseEntity = kakaoService.getKakaoAccessToken(code);

        String token = stringResponseEntity.getBody();
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(token);

        String userToken = memberCommandService.joinSocialMember(String.valueOf(userInfo.get("email")), String.valueOf(userInfo.get("id")));

        return ResponseEntity.ok().body(userToken);
    }

    @GetMapping("/oauth2/code/naver")
    public ResponseEntity<String> NaverLogin(@RequestParam("code") String code, @RequestParam("state") String state) {

        ResponseEntity<String> stringResponseEntity = naverService.getNaverAccessToken(code, state);

        String token = stringResponseEntity.getBody();

        HashMap<String, Object> userInfo = naverService.getUserInfo(token);


        String userToken = memberCommandService.joinSocialMember(String.valueOf(userInfo.get("email")), String.valueOf(userInfo.get("id")));

        return ResponseEntity.ok().body(userToken);

    }

    @GetMapping("/oauth2/code/google")
    public ResponseEntity<String> GoogleLogin(@RequestParam("code") String code) {
        System.out.println("code = " + code);
        //requestAccessToken이랑 getNaverAccessToken같은 역할
        ResponseEntity<String> accessTokenResponse = googleService.requestAccessToken(code);
        System.out.println("accessTokenResponse = " + accessTokenResponse);
        String accessTokenResponseBody = accessTokenResponse.getBody();

        //HashMap<String, Object> userInfo = googleService.getUserInfo(accessToken);
        //log.info(String.valueOf(userInfo));
        HashMap<String,String> userInfo = googleService.getUserInfo(accessTokenResponseBody);
        String userToken = memberCommandService.joinSocialMember(userInfo.get("email"), userInfo.get("id"));

        return ResponseEntity.ok().body(userToken);
    }
}
