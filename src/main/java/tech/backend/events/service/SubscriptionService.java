package tech.backend.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.backend.events.dto.SubscriptionResponse;
import tech.backend.events.exception.EventNotFoundException;
import tech.backend.events.exception.SubscriptionConflictException;
import tech.backend.events.exception.UserIndicadorNotFoundException;
import tech.backend.events.model.Subscription;
import tech.backend.events.model.User;
import tech.backend.events.repository.EventRepository;
import tech.backend.events.repository.SubscriptionRepository;
import tech.backend.events.repository.UserRepository;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        // recuperar o event pelo nome
        var event = eventRepository.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe!");
        }

        User userRecuperado = userRepository.findByEmail(user.getEmail());
        if (userRecuperado == null) {
            userRecuperado = userRepository.save(user);
        }

        User indicador = userRepository.findById(userId).orElse(null);
        if (indicador == null) {
            throw new UserIndicadorNotFoundException("Usuário " + userId + " indicador não existe!");
        }


        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(userRecuperado);
        subs.setIndication(indicador);

        Subscription sub = subscriptionRepository.findByEventAndSubscriber(event, userRecuperado);
        if (sub != null) {
            throw new SubscriptionConflictException("Já existe inscrição para o usuario " + user.getName() + " no evento " + event.getTitle());
        }

        Subscription res = subscriptionRepository.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/" + res.getEvent().getPrettyName() + "/" + res.getSubscriber().getId());
    }
}
