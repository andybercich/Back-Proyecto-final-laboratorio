package org.example.Controllers;

import com.mercadopago.MercadoPagoConfig;
import org.example.Socket.SocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("http://localhost:8080/webhook/mercado-pago/webhook/mercado-pago")
@CrossOrigin(origins = "*")
public class MercadoSocketController {

    private final SocketHandler socketHandler;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public MercadoSocketController(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> recibirWebhook(@RequestBody Map<String, Object> payload) {
        String tipo = (String) payload.get("type");
        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        String id = data.get("id").toString();
        System.out.println(id);
        System.out.println("Webhook recibido: type=" + tipo + ", id=" + id);

        if ("payment".equals(tipo)) {
            String estadoPago = consultarEstadoPago(id);
            if ("approved".equalsIgnoreCase(estadoPago)) {
                String referenciaExterna = obtenerReferenciaPorPagoId(id);

                socketHandler.enviarConfirmacionPago(
                        "{\"evento\":\"pago_confirmado\", \"estado\":true, \"referencia\":\"" + referenciaExterna + "\"}"
                );
                System.out.println(referenciaExterna);
                return ResponseEntity.ok(Map.of("referencia", referenciaExterna));

            }
        }

        return ResponseEntity.ok(Map.of("mensaje", "OK"));
    }


    public String obtenerReferenciaPorPagoId(String pagoId) {
        MercadoPagoConfig.setAccessToken(accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        System.out.println(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.mercadopago.com/v1/payments/" + pagoId,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("external_reference")) {
            return body.get("external_reference").toString();
        }

        return null;
    }


    private String consultarEstadoPago(String idPago) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.mercadopago.com/v1/payments/" + idPago,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        return body != null && body.containsKey("status") ? body.get("status").toString() : "unknown";
    }

}

