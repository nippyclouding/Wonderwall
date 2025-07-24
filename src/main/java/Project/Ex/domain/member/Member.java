package Project.Ex.domain.member;

import Project.Ex.domain.comment.Comment;
import Project.Ex.domain.post.Post;
import Project.Ex.domain.profile.Profile;
import Project.Ex.domain.repost.Repost;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member {
    @Id @GeneratedValue @Column(name = "MEMBER_SERIAL_ID")
    private Long serialid;

    private String loginId;
    private String password;
    @OneToMany(mappedBy = "postingMember")
    private List<Post> postings = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> memberComments = new ArrayList<>();
    @OneToOne(mappedBy = "member")
    private Profile profile;
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
