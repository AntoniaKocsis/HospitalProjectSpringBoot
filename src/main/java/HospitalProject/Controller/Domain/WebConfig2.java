package HospitalProject.Controller.Domain;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig2 implements WebMvcConfigurer {

    private final AdmissionRoomConverter admissionRoomConverter;

    public WebConfig2(AdmissionRoomConverter admissionRoomConverter) {
        this.admissionRoomConverter = admissionRoomConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(admissionRoomConverter);
    }
}
