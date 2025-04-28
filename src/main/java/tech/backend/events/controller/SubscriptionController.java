package tech.backend.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.backend.events.dto.ErrorMessage;
import tech.backend.events.dto.SubscriptionResponse;
import tech.backend.events.exception.EventNotFoundException;
import tech.backend.events.exception.SubscriptionConflictException;
import tech.backend.events.exception.UserIndicadorNotFoundException;
import tech.backend.events.model.Subscription;
import tech.backend.events.model.User;
import tech.backend.events.service.SubscriptionService;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName,
                                                @RequestBody User subscriber,
                                                @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse result = service.createNewSubscription(prettyName, subscriber, userId);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        }
        catch (EventNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
        catch (SubscriptionConflictException e) {
            return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
        }
        catch (UserIndicadorNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
