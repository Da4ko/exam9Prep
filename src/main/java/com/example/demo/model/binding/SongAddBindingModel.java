package com.example.demo.model.binding;

import com.example.demo.model.entity.enums.StyleName;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SongAddBindingModel {
    private String performer;
    private String title;
    private LocalDate releaseDate;
    private Integer duration;
    private StyleName styleName;

    public SongAddBindingModel() {
    }
    @NotBlank(message = "Performer cannot be null")
    @Size(min = 3, max=20, message = "Performer must be between 3 and 20 characters")
    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
    @NotBlank(message = "Song cannot be null")
    @Size(min = 2, max=20, message = "Song must be between 2 and 20 characters")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @PastOrPresent(message = "Release date cannot be in the future")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    @Positive(message = "Duration cannot be negative")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    @NotNull(message = "You must select a style")
    public StyleName getStyleName() {
        return styleName;
    }

    public void setStyleName(StyleName styleName) {
        this.styleName = styleName;
    }
}
