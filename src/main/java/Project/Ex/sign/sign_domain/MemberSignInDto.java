package Project.Ex.sign.sign_domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignInDto {



    @NotBlank(message = "ID는 필수입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    public SignMember toEntity() {
        return new SignMember(loginId, password, null);
    }
}
