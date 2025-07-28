package Project.Ex.domain.auth.sign_domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpDto {

    @NotBlank(message = "ID는 필수입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String username;

    // DTO를 Entity로 변환
    public SignMember toEntity() {
        return new SignMember(loginId, password, username);
    }

}
