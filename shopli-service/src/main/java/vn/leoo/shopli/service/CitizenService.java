package vn.leoo.shopli.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.leoo.shopli.dto.dlsfilter.DynamicFilterRequest;
import vn.leoo.shopli.repository.CitizenDynamicRepository;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenDynamicRepository repo;

    public Object search(
            DynamicFilterRequest req) throws ParseException {
        return repo.search(
                req
        );

    }
}
