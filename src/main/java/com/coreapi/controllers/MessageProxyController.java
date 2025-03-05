package com.coreapi.controllers;

import com.coreapi.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${message.api.url}")
    private String messageApiUrl;

    // GET /messages/student/{student} - Récupérer tous les messages d'un étudiant
    @GetMapping("/messages/student/{student}")
    public ResponseEntity<?> getMessagesByStudent(@PathVariable int student) {
        try {
            String url = messageApiUrl + "/messages/student/" + student;
            ResponseEntity<Message[]> response = restTemplate.getForEntity(url, Message[].class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    // POST /messages/post - Créer un nouveau message
    @PostMapping("/messages/post")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            String url = messageApiUrl + "/messages/post";
            ResponseEntity<Message> response = restTemplate.postForEntity(url, message, Message.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    // GET /messages/{id} - Récupérer un message par son ID
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable String id) {
        try {
            String url = messageApiUrl + "/messages/" + id;
            ResponseEntity<Message> response = restTemplate.getForEntity(url, Message.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    // PUT /messages/{id} - Mettre à jour un message
    @PutMapping("/messages/{id}")
    public ResponseEntity<?> updateMessage(@PathVariable String id, @RequestBody Message message) {
        try {
            String url = messageApiUrl + "/messages/" + id;
            ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.PUT,
                    new HttpEntity<>(message), Message.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    // DELETE /messages/{id} - Supprimer un message
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        try {
            String url = messageApiUrl + "/messages/" + id;
            restTemplate.delete(url);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }
}