package Project.Ex.domain.auth;

import Project.Ex.domain.auth.sign_domain.SignMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignMemberRepository extends JpaRepository<SignMember, Long> {
    SignMember findByLoginIdAndPassword(String loginId, String password);
    boolean existsByLoginId(String loginId);
}
