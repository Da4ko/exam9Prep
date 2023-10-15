package com.example.demo.service;

import com.example.demo.model.entity.Style;
import com.example.demo.model.entity.enums.StyleName;

public interface StyleService {
    void initStyles();

    Style findByName(StyleName styleName);
}
