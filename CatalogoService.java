import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@RestController
public class CatalogoService {

    @Value("${catalogo.url}")
    private String catalogoUrl;

    public static void main(String[] args) {
        SpringApplication.run(CatalogoService.class, args);
    }

    @PostMapping("/addMantenimiento")
    public ResponseEntity<String> addMantenimiento(@RequestBody Map<String, Object> data) {
        Map<String, Object> mantenimiento = new HashMap<>();
        mantenimiento.put("fecha", "07/02/2024");
        mantenimiento.put("hora", "18:43:17");
        mantenimiento.put("usuario", "jdelgad@gmail.com");
        mantenimiento.put("observacion", "Se realizará la actualización.");
        
        // Agregar el campo 'mantenimiento'
        data.put("mantenimiento", Collections.singletonList(mantenimiento));
        
        // Hacer la solicitud POST al servicio del catálogo
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);

        ResponseEntity<String> response = restTemplate.exchange(catalogoUrl, HttpMethod.POST, entity, String.class);
        
        return response.getStatusCode() == HttpStatus.OK
            ? ResponseEntity.ok("Mantenimiento agregado exitosamente")
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al agregar mantenimiento");
    }
}
