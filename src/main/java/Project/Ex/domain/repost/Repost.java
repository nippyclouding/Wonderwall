package Project.Ex.domain.repost;

import Project.Ex.domain.profile.Profile;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity @Getter
public class Repost {
    @Id
    @GeneratedValue
    private Long repostingId;
    private String title;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileId")
    private Profile profile;
    private LocalDateTime date;
}
