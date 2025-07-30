package Project.Ex.service.memberService;

import Project.Ex.domain.auth.SignMemberRepository;
import Project.Ex.domain.auth.sign_domain.SignMember;
import Project.Ex.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignMemberServiceTest {

    @Test
    public void test(){
        SignMemberRepository signMemberRepository;
        MemberService memberService;

        SignMember signMember = new SignMember("1", "dd", "dd");

    }

}