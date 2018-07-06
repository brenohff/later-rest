package br.com.brenohff.later.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenohff.later.models.LTCategory;
import br.com.brenohff.later.models.LTCategoryEvent;
import br.com.brenohff.later.models.LTEvent;
import br.com.brenohff.later.repository.CategoryEventRepository;
import br.com.brenohff.later.repository.EventRepository;
import br.com.brenohff.later.repository.UserRepository;
import br.com.brenohff.later.service.exceptions.ObjectNotFound;
import org.w3c.dom.events.EventException;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryEventRepository categoryEventRepository;

    public void saveEvent(LTEvent event) {
        event.setDt_post(new Date());
        eventRepository.save(event);
        for (LTCategory category : event.getCategories()) {
            categoryEventRepository.save(new LTCategoryEvent(category.getId(), event.getId()));
        }
    }

    public List<LTEvent> getPublic() {
        return eventRepository.getPublic();
    }

    public List<LTEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    public LTEvent getEventById(Long event_id) {
        try {
            return eventRepository.findOne(event_id);
        } catch (Exception e) {
            throw new ObjectNotFound("Evento não encontrado");
        }
    }

    public List<LTEvent> getEventsByUser(String user_id) {

        List<LTEvent> lt_events = eventRepository.getEventsByUser(user_id);

        if (!lt_events.isEmpty()) {
            return lt_events;
        } else {
            throw new ObjectNotFound("Nenhum evento encontrado.");
        }
    }

    public void delete(LTEvent event) {
        eventRepository.delete(event.getId());
    }

}
