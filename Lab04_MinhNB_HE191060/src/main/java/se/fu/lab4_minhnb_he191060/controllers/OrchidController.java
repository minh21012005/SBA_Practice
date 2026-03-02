package se.fu.lab4_minhnb_he191060.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.fu.lab4_minhnb_he191060.entities.Orchid;
import se.fu.lab4_minhnb_he191060.services.IOrchidService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/orchids")
public class OrchidController {
    @Autowired
    private IOrchidService iOrchidService;

    @GetMapping("/")
    public ResponseEntity<List<Orchid>> fetchAll() {
        return ResponseEntity.ok(iOrchidService.getAllOrchids());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Orchid saveOrchid(@RequestBody Orchid orchid) {
        return iOrchidService.insertOrchid(orchid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orchid> updateOrchid(@PathVariable("id") int id, @RequestBody Orchid orchid) {
        return ResponseEntity.ok(iOrchidService.updateOrchid(id, orchid));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrchid(@PathVariable("id") int id) {
        iOrchidService.deleteOrchid(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Orchid>> getOrchidById(@PathVariable("id") int id) {
        return ResponseEntity.ok(iOrchidService.getOrchidById(id));
    }
}
