package com.example.e_project_4_api.service;

import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
import com.example.e_project_4_api.dto.response.common_response.SongResponse;
import com.example.e_project_4_api.dto.response.display_for_admin.SongDisplayForAdmin;
import com.example.e_project_4_api.dto.response.display_response.SongDisplay;
import com.example.e_project_4_api.dto.response.mix_response.SongWithLikeAndViewInMonth;
import com.example.e_project_4_api.ex.NotFoundException;
import com.example.e_project_4_api.ex.ValidationException;
import com.example.e_project_4_api.models.*;
import com.example.e_project_4_api.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    private LikeAndViewRepository likeAndViewRepository;

    public List<SongResponse> getAllSongs() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toSongResponse)
                .collect(Collectors.toList());
    }

    public int getNumberOfSong() {
        return repo.getNumberOfAllNotDeleted(false);
    }

    @Cacheable("songsDisplay")
    public List<SongDisplay> getAllSongsForDisplay() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable("songsDisplayForAdmin")
    public List<SongDisplayForAdmin> getAllSongsForAdmin() {
        return repo.findAllNotDeleted(false)
                .stream()
                .map(this::toSongDisplayAdmin)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByArtist", key = "#artistId")
    public List<SongDisplay> getAllSongsByArtistIdForDisplay(int artistId) {
        return repo.findAllByArtistId(artistId, false, true)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "songsByAlbum", key = "#albumId")
    public List<SongDisplay> getAllSongsByAlbumIdForDisplay(int albumId) {
        return repo.findAllByAlbumId(albumId, false, true)
                .stream()
                .map(this::toSongDisplay)
                .collect(Collectors.toList());
    }

    public SongResponse findById(int id) {
        Optional<Songs> op = repo.findByIdAndIsDeleted(id, false);
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

    public SongDisplayForAdmin findDisplayForAdminById(int id) {
        Optional<Songs> op = repo.findByIdAndIsDeleted(id, false);
        if (op.isEmpty()) {
            throw new NotFoundException("Can't find any song with id: " + id);
        }
        return toSongDisplayAdmin(op.get());
    }

    public SongWithLikeAndViewInMonth getMostListenedSongInMonth() {
        LocalDate cuDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 1);
        LikeAndViewInMonth mostListenedSong = likeAndViewRepository.findSongWithMaxListenAmount(cuDate.getMonthValue(), pageable)
                .stream().findFirst().orElseThrow(() -> new NotFoundException("Can't most listened song"));
        return toSongWithLikeAndViewAmount(mostListenedSong);
    }

    public SongWithLikeAndViewInMonth getMostFavouriteSongInMonth() {
        LocalDate cuDate = LocalDate.now();

        Pageable pageable = PageRequest.of(0, 1);
        LikeAndViewInMonth mostListenedSong = likeAndViewRepository.findSongWithMaxLikeAmount(cuDate.getMonthValue(), pageable)
                .stream().findFirst().orElseThrow(() -> new NotFoundException("Can't most listened song"));
        return toSongWithLikeAndViewAmount(mostListenedSong);
    }

    public List<SongWithLikeAndViewInMonth> getMost5ListenedSongInMonth() {
        LocalDate cuDate = LocalDate.now();

        Pageable pageable = PageRequest.of(0, 5);
        List<LikeAndViewInMonth> mostListenedSongs = likeAndViewRepository.findSongsWithMaxListenAmount(cuDate.getMonthValue(), pageable);
        return mostListenedSongs.stream()
                .map(this::toSongWithLikeAndViewAmount)
                .collect(Collectors.toList());
    }

    private SongWithLikeAndViewInMonth toSongWithLikeAndViewAmount(LikeAndViewInMonth mostListenedSong) {
        SongWithLikeAndViewInMonth res = new SongWithLikeAndViewInMonth();
        res.setSongId(mostListenedSong.getSongId().getId());
        res.setSongName(mostListenedSong.getSongId().getTitle());
        res.setArtistName(mostListenedSong.getSongId().getArtistId().getArtistName());
        res.setAlbumName(mostListenedSong.getSongId().getAlbumId().getTitle());
        res.setLikeInMonth(mostListenedSong.getLikeAmount());
        res.setListenInMonth(mostListenedSong.getListenAmount());
        return res;
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
                .filter(song -> song.getSongId().getIsPending())
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

    @CacheEvict(value = {"playlistsDisplayForAdmin", "artistsDisplayForAdmin",
            "albumsDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs",
            "songsByGenre", "songsByPlaylist"}, allEntries = true)
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

    @CacheEvict(value = {"playlistsDisplayForAdmin", "artistsDisplayForAdmin",
            "albumsDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs",
            "songsByGenre", "songsByPlaylist"}, allEntries = true)
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

    @CacheEvict(value = {"artistsDisplayForAdmin",
            "albumsDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs",
            "songsByGenre", "songsByPlaylist"}, allEntries = true)
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
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
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

    @CacheEvict(value = {"artistsDisplayForAdmin",
            "albumsDisplayForAdmin", "songsDisplayForAdmin", "songsDisplay", "songsByArtist", "songsByAlbum", "favSongs",
            "songsByGenre", "songsByPlaylist"}, allEntries = true)
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
        List<Integer> genreIds = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getId())
                .toList();
        res.setGenreIds(genreIds);
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
        int favCount = favRepo.findFSBySongId(song.getId(), false).size();
        res.setTotalFavourite(favCount);
        List<String> genreNames = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getTitle())
                .toList();
        res.setGenreNames(genreNames);
        return res;
    }

    public SongDisplayForAdmin toSongDisplayAdmin(Songs song) {
        SongDisplayForAdmin res = new SongDisplayForAdmin();
        int favCount = favRepo.findFSBySongId(song.getId(), false).size();

        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        res.setTotalFavourite(favCount);
        List<String> genreNames = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getTitle())
                .toList();
        res.setGenreNames(genreNames);
        return res;
    }

    public SongDisplay toSongDisplay(FavouriteSongs fsSong) {
        Songs song = repo.findById(fsSong.getSongId().getId()).get();
        SongDisplay res = new SongDisplay();
        int favCount = favRepo.findFSBySongId(song.getId(), false).size();
        res.setTotalFavourite(favCount);
        BeanUtils.copyProperties(song, res);
        res.setIsDeleted(song.getIsDeleted());
        res.setIsPending(song.getIsPending());
        res.setAlbumTitle(song.getAlbumId().getTitle());
        res.setAlbumImage(song.getAlbumId().getImage());
        res.setArtistName(song.getArtistId().getArtistName());
        List<String> genreNames = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getTitle())
                .toList();
        res.setGenreNames(genreNames);
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
        int favCount = favRepo.findFSBySongId(song.getId(), false).size();
        res.setTotalFavourite(favCount);
        List<String> genreNames = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getTitle())
                .toList();
        res.setGenreNames(genreNames);
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
        int favCount = favRepo.findFSBySongId(song.getId(), false).size();
        res.setTotalFavourite(favCount);
        List<String> genreNames = genSongRepo.findBySongId(song.getId(), false)
                .stream()
                .map(it -> it.getGenreId().getTitle())
                .toList();
        res.setGenreNames(genreNames);
        return res;
    }
}
