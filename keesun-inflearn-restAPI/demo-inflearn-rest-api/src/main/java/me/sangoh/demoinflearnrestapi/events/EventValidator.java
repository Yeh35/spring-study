package me.sangoh.demoinflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getMaxPrice() != 0) {
            if (eventDto.getBasePrice() > eventDto.getMaxPrice()) {
                errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
                errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong.");
            }
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDataTime is wrong");
        }
        
        //TODO 나머지 다른 데이타도 검증해야함
        //TODO CloseEnrollmentDateTime....
    }

}
