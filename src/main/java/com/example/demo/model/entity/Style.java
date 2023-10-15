package com.example.demo.model.entity;

import com.example.demo.model.entity.BaseEntity;
import com.example.demo.model.entity.enums.StyleName;
import jakarta.persistence.*;

@Entity
@Table(name = "styles")
public class Style extends BaseEntity {
    private String description;
    private StyleName styleName;

    public Style() {
    }

    public Style(String description, StyleName styleName) {
        this.description = description;
        this.styleName = styleName;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "style-name", nullable = false)
    @Enumerated(EnumType.STRING)
    public StyleName getStyleName() {
        return styleName;
    }

    public void setStyleName(StyleName styleName) {
        this.styleName = styleName;
    }
}

