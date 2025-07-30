package Project.Ex.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostForm {
    private String title;
    private String text;

    public PostForm() {
    }

    public PostForm(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
