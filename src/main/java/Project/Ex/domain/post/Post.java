package Project.Ex.domain.post;

import Project.Ex.domain.comment.Comment;
import Project.Ex.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Post {
    @Id @GeneratedValue
    private Long postingId;
    private String title;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SERIAL_ID")
    private Member postingMember;
    private LocalDateTime date;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
