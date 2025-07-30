package Project.Ex.domain.auth;

import Project.Ex.domain.Member;
import Project.Ex.domain.MemberDTO;
import Project.Ex.repository.MemberRepository;
import Project.Ex.domain.auth.sign_domain.SignMember;
import Project.Ex.domain.auth.sign_domain.MemberDeleteDto;
import Project.Ex.domain.auth.sign_domain.MemberSignInDto;
import Project.Ex.domain.auth.sign_domain.MemberSignUpDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class SignMemberController {

    private final SignMemberService signMemberService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String root(){
        return "main";
    }

    //회원가입 매핑
    @GetMapping("/signUp")
    public String signUpForm(Model model){
        model.addAttribute("member", new MemberSignUpDto());
        return "sign/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid MemberSignUpDto memberDTO, BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", memberDTO);
            return "sign/signUp";
        }
        if (signMemberService.isLoginIdDuplicate(memberDTO.getLoginId())) {
            bindingResult.rejectValue("loginId", "duplicate.loginId", "이미 사용 중인 ID입니다.");
            model.addAttribute("member", memberDTO);
            return "sign/signUp";
        }
        try {
            log.info("new member signUp : {member}", memberDTO.getUsername());

            // 회원가입 처리를 서비스에 위임 (트랜잭션 처리)
            SignMember savedSignMember = signMemberService.signUp(memberDTO);

            redirectAttributes.addFlashAttribute("member", savedSignMember);
            return "redirect:signUpSuccess";
        }catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약조건 위반 (중복 ID 등)
            log.error("데이터 무결성 위반: {}", e.getMessage());
            bindingResult.rejectValue("loginId", "duplicate.loginId", "이미 사용 중인 ID입니다.");
            model.addAttribute("member", memberDTO);
            return "sign/signUp";
        }   catch (IllegalArgumentException e) {
            // 서비스에서 던진 중복 ID 예외
            log.error("중복 ID 에러: {}", e.getMessage());
            bindingResult.rejectValue("loginId", "duplicate.loginId", e.getMessage());
            // member 객체를 모델에 추가
            model.addAttribute("member", memberDTO);
            return "sign/signUp";

        } catch (Exception e) {
            log.error("회원가입 중 오류 발생", e.getMessage());
            bindingResult.reject("signup.error", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "sign/signUp";
        }
    }

    @GetMapping("/signUpSuccess")
    public String signUpSuccess(){
        return "sign/signUpSuccess";
    }



    //로그인 매핑
    @GetMapping("/signIn")
    public String signInForm(Model model){
        model.addAttribute("member", new MemberSignInDto()); // 🆕 이 줄 추가
        return "sign/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute MemberSignInDto member, HttpSession session){

        //form에서 넘겨받은 데이터로 DB의 회원 조회
        SignMember signMember = member.toEntity();
        SignMember loginSignMember = signMemberService.getSignMemberRepository().findByLoginIdAndPassword(signMember.getLoginId(), signMember.getPassword());

        //로그인 검증
        if(loginSignMember !=null){
            session.setAttribute("loginMember", loginSignMember);
            return "sign/signInSuccess";
        }
        else

            log.info("signIn member : {member}", member.toEntity().getUsername());

            return "sign/signInFail";
    }



    //로그아웃 매핑
    @GetMapping("/signOut")
    public String signOut(HttpSession httpSession){
        httpSession.removeAttribute("loginMember");
        //session.invalidate()도 가능
        return "redirect:/";
    }

    //회원탈퇴 매핑
    @GetMapping("deleteAccount")
    public String deleteAccountForm(HttpSession httpsession, Model model){

        //httpsession.getAttribute의 리턴타입 = object
        SignMember loginSignMember = (SignMember) httpsession.getAttribute("loginMember");

        //탈퇴하려는 회원의 유효성 확인(존재 여부 확인)
        if(loginSignMember == null){
            return "redirect:/signIn";
        }
        //삭제할 회원을 모델에 넣어 뷰로 전달
        model.addAttribute("memberDeleteDto", new MemberDeleteDto());
        return "sign/deleteAccountForm";
    }
    //회원 탈퇴 성공
    @PostMapping("/deleteAccount")
    public String deleteAccount(@Valid MemberDeleteDto memberDeleteDto,
                                BindingResult bindingResult,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes) {

        // 유효성 검증 실패 시(탈퇴 시 입력하려는 비밀번호가 잘못되었을 경우)
        if (bindingResult.hasErrors()) {
            return "sign/deleteAccountForm";
        }

        // 유효성 검증 성공 시 세션에서 로그인한 회원 정보 확인
        SignMember loginSignMember = (SignMember) httpSession.getAttribute("loginMember");
        if (loginSignMember == null) {
            redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/signIn";
        }

        // 비밀번호 확인
        if (!memberDeleteDto.isPasswordValid(loginSignMember.getPassword())) {
            bindingResult.rejectValue("password", "password.mismatch", "비밀번호가 일치하지 않습니다.");
            return "sign/deleteAccountForm";
        }

        try {
            // 회원 삭제 처리
            signMemberService.getSignMemberRepository().delete(loginSignMember);

            // 세션 무효화
            httpSession.invalidate();

            // 성공 메시지 추가
            log.info("deleteMember = {member}", memberDeleteDto.toEntity().getUsername());

            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
            return "sign/deleteSuccess";

        } catch (Exception e) {
            // 삭제 실패 시 에러 처리
            redirectAttributes.addFlashAttribute("error", "회원 탈퇴 중 오류가 발생했습니다.");
            return "sign/deleteAccountForm";
        }
    }
    @GetMapping("/deleteSuccess")
    public String deleteSuccess() {
        return "sign/deleteSuccess";
    }

    //메인 페이지 매핑
    //첫 페이지 수정 필요 시 해당 메서드 수정
    @GetMapping("main")
    public String postingMain(){
        return "firstMain";
    }
}
