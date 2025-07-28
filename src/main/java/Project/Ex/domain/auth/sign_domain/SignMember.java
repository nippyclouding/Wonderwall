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

    @Column(unique = true) @NotBlank
    private String loginId;

    @Column @NotBlank
    private String password;

    @Column @NotBlank
    private String username;

    @OneToOne(mappedBy = "signMember", cascade = CascadeType.REMOVE)
    private Member member;

    public SignMember(String loginId, String password, String username) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
    }

}
