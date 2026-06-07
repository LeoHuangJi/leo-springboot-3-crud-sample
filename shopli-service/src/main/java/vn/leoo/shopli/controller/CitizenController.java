package vn.leoo.shopli.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterRequest;
import vn.leoo.shopli.service.CitizenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/citizen")
public class CitizenController {
    private final CitizenService service;
    @PostMapping("/search")
    public Object search(
            @RequestBody
            DynamicFilterRequest req){

        return service.search(req);

    }
}
