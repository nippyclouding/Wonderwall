package Project.Ex.sign;

import Project.Ex.sign.sign_domain.SignMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<SignMember, Long> {
    SignMember findByLoginIdAndPassword(String loginId, String password);
}
