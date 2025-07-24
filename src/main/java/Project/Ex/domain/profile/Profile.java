package Project.Ex.domain.profile;

import Project.Ex.domain.member.Member;
import Project.Ex.domain.repost.Repost;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Profile {
    @Id @GeneratedValue
    private Long profileId;
    private String username;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    private Song profileSong;
    @OneToMany(mappedBy = "profile")
    private List<Repost> reposts = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SERIAL_ID")
    private Member member;
}
