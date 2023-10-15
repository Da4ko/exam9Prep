package com.example.demo.service;

import com.example.demo.model.entity.enums.StyleName;
import com.example.demo.model.service.SongServiceModel;
import com.example.demo.model.service.UserServiceModel;
import com.example.demo.model.view.SongViewModel;

import java.util.List;

public interface SongService {
    void add(SongServiceModel songServiceModel, UserServiceModel userServiceModel);

    List<SongViewModel> findByStyleName(StyleName styleName);

    List<SongViewModel> findUserPlaylist(UserServiceModel userServiceModel);

    void addToPlaylist(String id, UserServiceModel userServiceModel);

    String findUserPlaylistDuration(UserServiceModel userServiceModel);
}
