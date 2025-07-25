package Project.Ex.sign;

import Project.Ex.sign.sign_domain.SignMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SignMemberService {
    private final SignMemberRepository signMemberRepository;

    public SignMemberRepository getSignMemberRepository(){
        return signMemberRepository;
    }

    public SignMember findByLoginIdAndPassword(String loginId, String password){
        return signMemberRepository.findByLoginIdAndPassword(loginId, password);
    }
}
