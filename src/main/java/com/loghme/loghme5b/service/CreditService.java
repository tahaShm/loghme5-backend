package com.loghme.loghme5b.service;

import com.loghme.loghme5b.repo.utils.Loghme;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditService {
    private Loghme loghme = Loghme.getInstance();
    @RequestMapping(value = "/credit", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCredit(HttpEntity<String> httpEntity) {
        try {
            loghme.addCredit(httpEntity.getBody());
        }
        catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(null);
    }
}
