package Project.Ex.domain.member;

import Project.Ex.domain.comment.Comment;
import Project.Ex.domain.post.Post;
import Project.Ex.domain.profile.Profile;
import Project.Ex.domain.repost.Repost;
import Project.Ex.sign.sign_domain.SignMember;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member {
    @Id @Column(name = "MEMBER_SERIAL_ID")
    private Long serialid;
    private String userName;
    @OneToMany(mappedBy = "postingMember")
    private List<Post> postings = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> memberComments = new ArrayList<>();
    @OneToOne(mappedBy = "member")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serial_id")
    private SignMember signMember;


    public Member(MemberDTO memberDTO) {
        userName = memberDTO.getUsername();
        serialid = memberDTO.getSerialId();
    }

    protected Member() {
    }
}
