package Project.Ex.domain.auth.sign_domain;

import Project.Ex.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class SignMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial_id")
    private Long serialId;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "ID는 필수입니다.")
    private String loginId;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "이름은 필수입니다.")
    private String username;

    @OneToOne(mappedBy = "signMember", cascade = CascadeType.REMOVE)
    private Member member;

    public SignMember(String loginId, String password, String username) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
    }

}
