package com.example.demo.service.impl;

import com.example.demo.model.entity.Style;
import com.example.demo.model.entity.enums.StyleName;
import com.example.demo.repository.StyleRepository;
import com.example.demo.service.StyleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StyleServiceImpl implements StyleService {
    private final StyleRepository styleRepository;

    public StyleServiceImpl(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void initStyles() {
        if(styleRepository.count() == 0){
            Arrays.stream(StyleName.values())
                    .forEach(styleName -> {
                        Style style = new Style("description for " + styleName.name(), styleName);
                        styleRepository.save(style);
                    });
        }
    }

    @Override
    public Style findByName(StyleName styleName) {
        return styleRepository.findByStyleName(styleName).orElse(null);
    }
}
