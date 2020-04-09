package com.loghme.loghme5b.service;

import com.loghme.loghme5b.repo.utils.Loghme;
import com.loghme.loghme5b.repo.utils.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentOrderService {
    private Loghme loghme = Loghme.getInstance();
    @RequestMapping(value = "/currentOrder", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getCurrentOrder() {
        return loghme.getCustomer().getCurrentOrder();
    }
}