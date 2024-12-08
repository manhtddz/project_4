//package com.example.e_project_4_api.controllers;
//
//import com.example.e_project_4_api.dto.request.NewOrUpdateAlbum;
//import com.example.e_project_4_api.dto.request.NewOrUpdateSong;
//import com.example.e_project_4_api.dto.response.AlbumResponse;
//import com.example.e_project_4_api.dto.response.SongResponse;
////import com.example.e_project_4_api.service.AlbumService;
//import com.example.e_project_4_api.service.SongService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class SongController {
//    @Autowired
//    private SongService service;
//
//    @GetMapping("/public/songs")
//    public List<SongResponse> findAll() {
//        return service.getAllAlbums();
//    }
//
//    @GetMapping("/public/songs/{id}")
//    public SongResponse findDetails(@PathVariable("id") int id) {
//        return service.findById(id);
//    }
//
//    @DeleteMapping("/public/songs/{id}")
//    public Map<String, String> delete(@PathVariable("id")int id) {
//        service.deleteById(id);
//        return Map.of(
//                "message", "Delete song " + id
//        );
//    }
//
//    @PostMapping("/public/songs")
//    public Map<String, Object> add(@RequestBody @Valid NewOrUpdateSong request) {
//        NewOrUpdateSong res = service.addNewSong(request);
//        return Map.of(
//                "message", "Add success",
//                "data", res
//        );
//    }
//
//    @PutMapping("/public/songs")
//    public Map<String, Object> update(@RequestBody @Valid NewOrUpdateSong request) {
//        NewOrUpdateSong res = service.updateSong(request);
//        return Map.of(
//                "message", "Update success",
//                "data", res
//        );
//    }
//}
