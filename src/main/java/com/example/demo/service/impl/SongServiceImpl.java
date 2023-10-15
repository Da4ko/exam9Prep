package com.example.demo.service.impl;

import com.example.demo.model.entity.Song;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enums.StyleName;
import com.example.demo.model.service.SongServiceModel;
import com.example.demo.model.service.UserServiceModel;
import com.example.demo.model.view.SongViewModel;
import com.example.demo.repository.SongRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SongService;
import com.example.demo.service.StyleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SongServiceImpl implements SongService {
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;
    private final StyleService styleService;
    private final UserRepository userRepository;

    public SongServiceImpl(ModelMapper modelMapper, SongRepository songRepository, StyleService styleService,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
        this.styleService = styleService;
        this.userRepository = userRepository;
    }

    @Override
    public void add(SongServiceModel songServiceModel, UserServiceModel userServiceModel) {
            Song song = modelMapper.map(songServiceModel, Song.class);
            song.setStyle(styleService.findByName(songServiceModel.getStyle()));
            songRepository.save(song);
    }

    @Override
    public List<SongViewModel> findByStyleName(StyleName styleName) {
        List<SongViewModel> songsToDisplay = new ArrayList<>();
        List<Song> thisGenreSongs = songRepository.findAllByStyle_StyleName(styleName);
        for (Song song : thisGenreSongs) {
            SongViewModel songViewModel = new SongViewModel();
            songViewModel.setId(song.getId());
            songViewModel.setPerformer(song.getPerformer());
            songViewModel.setTitle(song.getTitle());
            songViewModel.setDuration(Integer.toString(song.getDuration()/60) + ":" + Integer.toString(song.getDuration()%60));
            songsToDisplay.add(songViewModel);
        }
        return songsToDisplay;
    }

    @Override
    public List<SongViewModel> findUserPlaylist(UserServiceModel userServiceModel) {
        List<SongViewModel> songsToDisplay = new ArrayList<>();
        User currentUser  = userRepository.findByUsernameAndPassword(userServiceModel.getUsername(), userServiceModel.getPassword()).orElse(null);
        Set<Song> thisPlaylist = currentUser.getPlaylist();

        for (Song song : thisPlaylist) {
            SongViewModel songViewModel = new SongViewModel();
            songViewModel.setId(song.getId());
            songViewModel.setPerformer(song.getPerformer());
            songViewModel.setTitle(song.getTitle());
            songViewModel.setDuration(Integer.toString(song.getDuration()/60) + ":" + Integer.toString(song.getDuration()%60));
            songsToDisplay.add(songViewModel);
        }
        return songsToDisplay;
    }

    @Override
    public void addToPlaylist(String id, UserServiceModel userServiceModel) {
        User user = userRepository.findByUsernameAndPassword(userServiceModel.getUsername(), userServiceModel.getPassword()).orElse(null);
        Set<Song> updatedPlaylist = user.getPlaylist();
        updatedPlaylist.add(songRepository.findById(id).orElse(null));
        user.setPlaylist(updatedPlaylist);
        userRepository.save(user);
    }

    @Override
    public String findUserPlaylistDuration(UserServiceModel userServiceModel) {
        User user = userRepository.findByUsernameAndPassword(userServiceModel.getUsername(), userServiceModel.getPassword()).orElse(null);
        Set<Song> userPlaylist = user.getPlaylist();
        int seconds = 0;
        for (Song song: userPlaylist) {
            seconds+=song.getDuration();
        }
        return Integer.toString(seconds/60) + ":" + Integer.toString(seconds%60);
    }


}
