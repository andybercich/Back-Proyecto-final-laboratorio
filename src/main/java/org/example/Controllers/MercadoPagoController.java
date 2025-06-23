package org.example.Controllers;

import com.mercadopago.resources.preference.Preference;
import org.example.Entities.DTO.OrdenCompraPostDTO;
import org.example.Entities.DTO.PreferenciaResponse;
import org.example.Entities.OrdenCompra;
import org.example.Entities.OrdenCompraDetalle;
import org.example.Services.MercadoPagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sneaks/api/mp")
@CrossOrigin(origins = "*")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/crearPreferencia")
    public ResponseEntity<Map<String, String>> crearPreferencia(@RequestBody OrdenCompra request) {
        try {
            PreferenciaResponse preference = mercadoPagoService.crearPreferencia(request);
            String initPoint = preference.getInitPoint();

            // Retornás el URL para que el front lo use y abra Mercado Pago
            return ResponseEntity.ok(Map.of("init_point", initPoint, "Referencia", preference.getExternalReference()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error creando preferencia"));
        }
    }
}
