package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.common_response.SongResponse;
import com.example.e_project_4_api.dto.response.display_response.SongDisplay;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.*;
import com.example.e_project_4_api.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class SongService {
    @Autowired
    private SongRepository repo;
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private ArtistRepository artistRepo;
    @Autowired
    private FavouriteSongRepository favRepo;
    @Autowired
    private GenreSongRepository genSongRepo;
    @Autowired
    private PlaylistSongRepository playlistSongRepo;

    public List<SongResponse> getAllSongs() {
        return repo.findAll()
                .stream()
                .map(this::toSongResponse)
                .collect(Collectors.toList());
    }

    @Cacheable("songsDisplay")
    public List<SongDisplay> getAllSongsForDisplay() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByArtist", key = "#artistId")
    public List<SongDisplay> getAllSongsByArtistIdForDisplay(int artistId) {
        return repo.findAllByArtistId(artistId, false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByAlbum", key = "#albumId")
    public List<SongDisplay> getAllSongsByAlbumIdForDisplay(int albumId) {
        return repo.findAllByAlbumId(albumId, false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    public SongResponse findById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        return toSongResponse(op.get());
    }

    public SongDisplay findDisplayById(int id) {
        Optional<Songs> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        return toSongDisplay(op.get());
    }

    @Cacheable(value = "favSongs", key = "#id")
    public List<SongDisplay> getAllFavSongsByUserId(Integer id) {
        return favRepo.findFSByUserId(id, false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByGenre", key = "#id")
    public List<SongDisplay> getAllSongsByGenreId(Integer id) {
        return genSongRepo.findByGenreId(id, false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByPlaylist", key = "#id")
    public List<SongDisplay> getAllSongsByPlaylistId(Integer id) {
        return playlistSongRepo.findByPlaylistId(id, false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }
    @CacheEvict(value = {"songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
    public boolean deleteById(int id) {
        Optional<Songs> op = repo.findById(id);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        Songs existing = op.get();
        existing.setIsDeleted(true);
        repo.save(existing);
        return true;
    }

    @CacheEvict(value = {"songsDisplay", "songsByArtist", "songsByAlbum","songsByGenre" }, allEntries = true)
    public NewOrUpdateSong addNewSong(NewOrUpdateSong request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Songs> op = repo.findByTitle(request.getTitle());
        if (op.isPresent()) {
            errors.add(Map.of("titleError", "Already exist title"));
        }
        Optional<Albums> album = albumRepo.findByIdAndIsDeleted(request.getAlbumId(), false);
        Optional<Artists> artist = artistRepo.findByIdAndIsDeleted(request.getArtistId(), false);
        if (artist.isEmpty()) {
            errors.add(Map.of("artistError", "Can't find artist"));
        }
        if (album.isEmpty()) {
            errors.add(Map.of("albumError", "Can't find album"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Songs newSong = new Songs(request.getTitle(), request.getAudioPath(),
                0, request.getFeatureArtist(), request.getLyricFilePath(), false, false,
                new Date(), new Date(), album.get(), artist.get());
        repo.save(newSong);
        return request;
    }

    @CacheEvict(value = {"songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
    public NewOrUpdateSong updateSong(NewOrUpdateSong request) {
        List<Map<String, String>> errors = new ArrayList<>();

        Optional<Songs> op = repo.findById(request.getId());
        //check sự tồn tại
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + request.getId());
        }

        Optional<Songs> opTitle = repo.findByTitle(request.getTitle());
        if (opTitle.isPresent() && opTitle.get().getTitle() != op.get().getTitle()) {
            errors.add(Map.of("titleError", "Already exist title"));
        }

        Optional<Albums> album = albumRepo.findByIdAndIsDeleted(request.getAlbumId(), false);
        Optional<Artists> artist = artistRepo.findByIdAndIsDeleted(request.getArtistId(), false);
        if (artist.isEmpty()) {
            errors.add(Map.of("artistError", "Can't find artist"));
        }
        if (album.isEmpty()) {
            errors.add(Map.of("albumError", "Can't find album"));
        }
        Songs song = op.get();
        song.setTitle(request.getTitle());
        song.setAudioPath(request.getAudioPath());
        song.setListenAmount(request.getListenAmount());
        song.setFeatureArtist(request.getFeatureArtist());
        song.setLyricFilePath(request.getLyricFilePath());
        song.setIsPending(request.getIsPending());
        song.setModifiedAt(new Date());
        song.setAlbumId(album.get());
        song.setArtistId(artist.get());
        repo.save(song);
        return request;
    }

    @CacheEvict(value = {"songsDisplay", "songsByArtist", "songsByAlbum", "favSongs", "songsByGenre", "songsByPlaylist"}, allEntries = true)
    public void plusOneListenAmount(int songId) {
        Optional<Songs> op = repo.findByIdAndIsDeleted(songId, false);
        //check sự tồn tại
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + songId);
        }
        Songs song = op.get();
        song.setListenAmount(song.getListenAmount() + 1);
        repo.save(song);
    }

    public SongResponse toSongResponse(Songs song) {
        SongResponse res = new SongResponse();
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumId(song.getAlbumId().getId());
        res.setArtistId(song.getArtistId().getId());
        return res;
    }

    public SongDisplay toSongDisplay(Songs song) {
        SongDisplay res = new SongDisplay();
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        return res;
    }

    public SongDisplay toSongDisplay(FavouriteSongs fsSong) {
        Songs song = repo.findById(fsSong.getSongId().getId()).get();

        SongDisplay res = new SongDisplay();
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        return res;
    }

    public SongDisplay toSongDisplay(GenreSong genreSong) {
        Songs song = repo.findById(genreSong.getSongId().getId()).get();

        SongDisplay res = new SongDisplay();
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        return res;
    }

    public SongDisplay toSongDisplay(PlaylistSong playlistSong) {
        Songs song = repo.findById(playlistSong.getSongId().getId()).get();

        SongDisplay res = new SongDisplay();
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        return res;
    }
}
