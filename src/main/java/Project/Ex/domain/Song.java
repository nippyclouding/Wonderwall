package Project.Ex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;



@Entity @Getter
public class Song {
    @Id @GeneratedValue
    private Long songId;
    private String title;
    private String artist;
    private Integer runtime;
    @OneToOne(mappedBy = "profileSong")
    private Profile profile;
}
