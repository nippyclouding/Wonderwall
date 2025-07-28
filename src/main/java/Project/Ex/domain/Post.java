package Project.Ex.domain;

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

    public Post() {
    }

    public Post(String title, String text, Member postingMember) {
        this.title = title;
        this.text = text;
        this.postingMember = postingMember;
        this.date = LocalDateTime.now();
    }
}
