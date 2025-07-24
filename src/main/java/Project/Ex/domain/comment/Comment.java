package Project.Ex.domain.comment;

import Project.Ex.domain.member.Member;
import Project.Ex.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity @Getter
public class Comment {
    @Id @GeneratedValue
    private Long commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SERIAL_ID")
    private Member member;
    private String text;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postingId")
    private Post post;
}
