package HospitalProject.Controller.Domain;

import HospitalProject.Controller.Domain.ExaminationRoomConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ExaminationRoomConverter examinationRoomConverter;

    public WebConfig(ExaminationRoomConverter examinationRoomConverter) {
        this.examinationRoomConverter = examinationRoomConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(examinationRoomConverter);
    }
}
