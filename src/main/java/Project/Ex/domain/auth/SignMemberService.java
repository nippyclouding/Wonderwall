package Project.Ex.domain.auth;

import Project.Ex.domain.Member;
import Project.Ex.domain.MemberDTO;
import Project.Ex.domain.auth.sign_domain.MemberSignUpDto;
import Project.Ex.domain.auth.sign_domain.SignMember;
import Project.Ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SignMemberService {
    private final SignMemberRepository signMemberRepository;
    private final MemberRepository memberRepository;

    public SignMemberRepository getSignMemberRepository(){
        return signMemberRepository;
    }

    public SignMember findByLoginIdAndPassword(String loginId, String password){
        return signMemberRepository.findByLoginIdAndPassword(loginId, password);
    }
    public boolean isLoginIdDuplicate(String loginId) {
        return signMemberRepository.existsByLoginId(loginId);
    }
    @Transactional
    public SignMember signUp(MemberSignUpDto memberDTO) {
        // 한 번 더 중복 체크 (동시성 문제 방지)
        if (signMemberRepository.existsByLoginId(memberDTO.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 ID입니다.");
        }

        // SignMember 저장
        SignMember savedSignMember = signMemberRepository.save(memberDTO.toEntity());

        // Member 객체도 저장
        MemberDTO newMemberDTO = new MemberDTO(savedSignMember);
        Member member = new Member(newMemberDTO);
        memberRepository.save(member);

        return savedSignMember;
    }
}
