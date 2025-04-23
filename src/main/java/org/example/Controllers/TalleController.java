package org.example.Controllers;

import org.example.Entities.Talle;
import org.example.Repositories.TalleRepository;
import org.example.Services.TalleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/talle")
public class TalleController extends BaseController<Talle,Long, TalleRepository, TalleService> {
    public TalleController(TalleService service) {
        super(service);
    }
}
