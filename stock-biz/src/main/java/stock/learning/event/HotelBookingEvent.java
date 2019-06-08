package stock.learning.event;

import org.springframework.context.ApplicationEvent;

public class HotelBookingEvent extends ApplicationEvent {
    public HotelBookingEvent(Object source) {
        super(source);
    }
}
