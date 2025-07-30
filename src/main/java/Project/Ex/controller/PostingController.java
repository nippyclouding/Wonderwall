package Project.Ex.controller;

import Project.Ex.domain.PostForm;
import Project.Ex.domain.auth.sign_domain.SignMember;
import Project.Ex.repository.PostingRepository;
import Project.Ex.service.PostingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {

    private final PostingRepository postingRepository;
    private final PostingService postingService;

    @GetMapping
    public String writeNewPost(HttpSession session, Model model) {
        // GET 요청으로 로그인 체크
        SignMember loginMember = (SignMember) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/signIn";
        }
        // 빈 폼 객체 전달
        model.addAttribute("postingForm", new PostForm());
        return "posting/writeNewPost";
    }

    @PostMapping
    public String createPost(@ModelAttribute PostForm postingForm,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        try {
            // 다시 한번 로그인 체크 (for 강력한 보안)
            SignMember loginSignMember = (SignMember) session.getAttribute("loginMember");
            if (loginSignMember == null) {
                return "redirect:/signIn";
            }

            // 간단한 유효성 검사
            String errorMessage = validatePostingForm(postingForm);
            if (errorMessage != null) {
                model.addAttribute("postingForm", postingForm);
                model.addAttribute("errorMessage", errorMessage);
                return "posting/writeNewPost";
            }

            // 게시글 저장
            Long postId = postingService.savePost(postingForm, loginSignMember.getSerialId());

            // 성공 메시지
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 작성되었습니다.");
            return "redirect:/";

        } catch (RuntimeException e) {
            // 간단한 예외 처리
            log.error("게시글 작성 오류: " + e.getMessage());
            model.addAttribute("postingForm", postingForm);
            model.addAttribute("errorMessage", e.getMessage());
            return "posting/writeNewPost";

        } catch (Exception e) {
            // 예상치 못한 오류
            log.error("시스템 오류: " + e.getMessage());
            model.addAttribute("postingForm", postingForm);
            model.addAttribute("errorMessage", "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return "posting/writeNewPost";
        }
    }

    // 간단한 유효성 검사 메서드
    private String validatePostingForm(PostForm postingForm) {
        if (postingForm.getTitle() == null || postingForm.getTitle().trim().isEmpty()) {
            return "제목을 입력해주세요.";
        }

        if (postingForm.getTitle().length() > 100) {
            return "제목은 100자 이내로 입력해주세요.";
        }

        if (postingForm.getText() == null || postingForm.getText().trim().isEmpty()) {
            return "내용을 입력해주세요.";
        }

        if (postingForm.getText().length() > 5000) {
            return "내용은 5000자 이내로 입력해주세요.";
        }

        return null; // 오류 없음
    }
}
