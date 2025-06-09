package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Entities.Direccion;
import org.example.Repositories.DireccionRepository;
import org.example.Services.DireccionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sneaks/direccion")
public class DireccionController extends BaseController<Direccion,Long, DireccionRepository, DireccionService>{
    public DireccionController(DireccionService service) {
        super(service);
    }
}
