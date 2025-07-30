package Project.Ex.domain;

import Project.Ex.domain.auth.sign_domain.SignMember;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {
    private Long serialId;
    private String username;

    public MemberDTO(SignMember signMember) {
        serialId = signMember.getSerialId();
        username = signMember.getUsername();
    }
}
