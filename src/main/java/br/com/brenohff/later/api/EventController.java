package br.com.brenohff.later.api;

import br.com.brenohff.later.model.LTEvent;
import br.com.brenohff.later.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/events")
@CrossOrigin
public class EventController {

    @Autowired
    EventService service;

    @PostMapping(value = "/saveEvent")
    public ResponseEntity<Void> saveEvent(@RequestPart("event") String event, @RequestPart MultipartFile file) {
        service.saveEvent(event, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/getAll")
    public List<LTEvent> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping(value = "/getPublic")
    public ResponseEntity<List<LTEvent>> getPublic() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPublic());
    }

    @GetMapping(value = "/getEventById")
    public LTEvent getEventById(@RequestParam(value = "event_id") Long event_id) {
        return service.getEventById(event_id);
    }

    @GetMapping(value = "/getEventsByUser")
    public List<LTEvent> getEventsByUser(@RequestParam(value = "user_id") String user_id) {
        return service.getEventsByUser(user_id);
    }

    @GetMapping(value = "/delete")
    public void delete(@RequestParam(value = "event_id") Long event_id) {
        service.delete(event_id);
    }

}