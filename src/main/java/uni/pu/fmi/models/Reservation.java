package uni.pu.fmi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class Reservation {
    private String guestName;
    private String guestPhone;
    private LocalDate date;
    private LocalTime time;
    private int guests;
    private RestaurantTable table;
    private Role createdBy;
    private ReservationStatus status;
}
