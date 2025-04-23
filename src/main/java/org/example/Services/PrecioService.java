package org.example.Services;

import org.example.Entities.Base;
import org.example.Entities.Precio;
import org.example.Repositories.PrecioRepository;
import org.springframework.stereotype.Service;

@Service
public class PrecioService extends BaseService<Precio,Long, PrecioRepository> {
}
