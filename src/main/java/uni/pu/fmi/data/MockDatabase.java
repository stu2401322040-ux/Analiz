package uni.pu.fmi.data;

import lombok.Getter;
import uni.pu.fmi.models.Reservation;
import uni.pu.fmi.models.ReservationStatus;
import uni.pu.fmi.models.RestaurantTable;
import uni.pu.fmi.models.Role;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MockDatabase {
    private final List<RestaurantTable> tables = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public MockDatabase() {
        RestaurantTable table1 = new RestaurantTable(1, 2);
        RestaurantTable table2 = new RestaurantTable(2, 4);
        RestaurantTable table3 = new RestaurantTable(3, 4);
        RestaurantTable table4 = new RestaurantTable(4, 6);
        RestaurantTable table5 = new RestaurantTable(5, 12);
        tables.add(table1);
        tables.add(table2);
        tables.add(table3);
        tables.add(table4);
        tables.add(table5);

        LocalDate valentinesDay = LocalDate.of(2030, 2, 14);
        LocalTime eightPm = LocalTime.of(20, 0);
        reservations.add(new Reservation("Георги Иванов", "0888111222", valentinesDay, eightPm, 6,
                table4, Role.CLIENT, ReservationStatus.CONFIRMED));
        reservations.add(new Reservation("Фирма Алфа", "0888333444", valentinesDay, eightPm, 10,
                table5, Role.STAFF, ReservationStatus.CONFIRMED));
    }
}
