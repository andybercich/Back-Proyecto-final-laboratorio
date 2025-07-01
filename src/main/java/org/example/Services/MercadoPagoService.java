package org.example.Services;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import org.example.Entities.BackUrl;
import org.example.Entities.DTO.PreferenciaResponse;
import org.example.Entities.OrdenCompra;
import org.example.Entities.OrdenCompraDetalle;
import org.example.Repositories.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @Autowired
    private DetalleRepository detalleRepository;
    public PreferenciaResponse crearPreferencia(OrdenCompra ordenCompra) throws Exception {
        try{

            MercadoPagoConfig.setAccessToken(accessToken);

            List<OrdenCompraDetalle> detalles = ordenCompra.getDetalles();
            detalles.forEach(detalle -> {
                detalle.setDetalle(detalleRepository.getReferenceById(detalle.getDetalle().getId()));
            });
            detalles.forEach(OrdenCompraDetalle::calcularSubtotal);

            List<PreferenceItemRequest> items = detalles.stream()
                    .map(detalle -> PreferenceItemRequest.builder()
                            .title(detalle.getDetalle().getProducto().getNombre())
                            .description("Talle: " + detalle.getDetalle().getTalle().getTalle() +
                                    " Color: " + detalle.getDetalle().getColor())
                            .quantity(detalle.getCantidad())
                            .unitPrice(detalle.getDetalle().calcularTotal())
                            .currencyId("ARS")
                            .build())
                    .toList();

            String referenciaExterna = UUID.randomUUID().toString();
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .externalReference(referenciaExterna)
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://c1c7-191-81-200-2.ngrok-free.app/confirmacionPago")
                            .failure("https://c1c7-191-81-200-2.ngrok-free.app/confirmacionPago")
                            .pending("https://c1c7-191-81-200-2.ngrok-free.app/confirmacionPago")
                            .build())
                    .autoReturn("approved")
                    .build();
            System.out.println(preferenceRequest);

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            System.out.println(preference);
            return new PreferenciaResponse(preference.getInitPoint(), referenciaExterna);
        }catch (MPApiException e) {
            System.err.println("Error de MP: " + e.getApiResponse().getContent());
            throw e;
        }
    }
}
