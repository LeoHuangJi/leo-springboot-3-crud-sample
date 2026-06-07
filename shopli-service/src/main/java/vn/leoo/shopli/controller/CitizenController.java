package vn.leoo.shopli.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterRequest;
import vn.leoo.shopli.dto.dlsfilter.res.FilterFieldResponse;
import vn.leoo.shopli.service.CitizenService;
import vn.leoo.shopli.service.FilterConfigService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/citizen")
public class CitizenController {
    private final CitizenService service;
    private final FilterConfigService filterConfigService;

    @PostMapping("/search")
    public Object search(
            @RequestBody
            DynamicFilterRequest req){

        return service.search(req);

    }
    @GetMapping
    public Map<String,
                List<FilterFieldResponse>>
    getAll(){

        return filterConfigService.getAll();

    }
}
