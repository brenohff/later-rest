package br.com.brenohff.later.service;

import br.com.brenohff.later.enums.EventStatus;
import br.com.brenohff.later.model.LTCategory;
import br.com.brenohff.later.model.LTCategoryEvent;
import br.com.brenohff.later.model.LTEvent;
import br.com.brenohff.later.repository.CategoryEventRepository;
import br.com.brenohff.later.repository.EventRepository;
import br.com.brenohff.later.repository.UserRepository;
import br.com.brenohff.later.service.exceptions.ObjectNotFound;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryEventRepository categoryEventRepository;

    @Autowired
    S3Service s3Service;

    public void saveEvent(String e, MultipartFile file) {
        LTEvent event = new Gson().fromJson(e, LTEvent.class);

        event.setDt_post(new Date());
        event.setStatus(EventStatus.AGUARDANDO);
        event.setImage(s3Service.uploadFile(file).toString());
        eventRepository.save(event);

        for (LTCategory category : event.getCategories()) {
            categoryEventRepository.save(new LTCategoryEvent(category.getId(), event.getId()));
        }
    }

    public List<LTEvent> getEventsActivesAndPublic() {
        return eventRepository.getEventsActivesAndPublic();
    }

    public List<LTEvent> getPendingEvents() {
        return eventRepository.getPendingEvents();
    }

    public List<LTEvent> getEventsByCategory(Long category_id) {
        return eventRepository.getEventsByCategory(category_id);
    }

    public List<LTEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    public LTEvent getEventById(Long event_id) {
        LTEvent event = eventRepository.findOne(event_id);

        if (event != null) {
            return event;
        } else {
            throw new ObjectNotFound("Evento não encontrado");
        }
    }

    public List<LTEvent> getEventsByUser(String user_id) {
        return eventRepository.getEventsByUser(user_id);
    }

    public void changeEventStatus(Long event_id, EventStatus eventStatus) {
        eventRepository.changeEventStatus(eventStatus, event_id);
    }

    public ResponseEntity<Void> updateEventWithoutImage(LTEvent event) {
        if (eventRepository.findOne(event.getId()) == null) {
            return ResponseEntity.notFound().build();
        }

        event.setDt_post(new Date());
        eventRepository.save(event);

        categoryEventRepository.delete(categoryEventRepository.getLTCategoryEventByEventId(event.getId()));
        for (LTCategory category : event.getCategories()) {
            categoryEventRepository.save(new LTCategoryEvent(category.getId(), event.getId()));
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateEventWithImage(String e, MultipartFile file) {
        LTEvent event = new Gson().fromJson(e, LTEvent.class);

        String oldImage = event.getImage();

        event.setImage(s3Service.uploadFile(file).toString());
        event.setDt_post(new Date());
        eventRepository.save(event);

        s3Service.deleteFile(oldImage);

        categoryEventRepository.delete(categoryEventRepository.getLTCategoryEventByEventId(event.getId()));
        for (LTCategory category : event.getCategories()) {
            categoryEventRepository.save(new LTCategoryEvent(category.getId(), event.getId()));
        }

        return ResponseEntity.ok().build();
    }

    public void saveImage(MultipartFile file) {
        s3Service.uploadFile(file);
    }

    public void deleteImage(String file_name) {
        s3Service.deleteFile(file_name);
    }

}