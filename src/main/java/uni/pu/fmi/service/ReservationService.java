package uni.pu.fmi.service;

import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.data.MockDatabase;
import uni.pu.fmi.models.Reservation;
import uni.pu.fmi.models.ReservationStatus;
import uni.pu.fmi.models.RestaurantTable;
import uni.pu.fmi.models.Role;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class ReservationService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime FIRST_RESERVATION = LocalTime.of(12, 0);
    private static final LocalTime LAST_RESERVATION = LocalTime.of(22, 0);
    private static final int CLIENT_MAX_GUESTS = 6;
    private static final int MAX_GUESTS = 12;

    private final MockDatabase database = new MockDatabase();

    public String makeReservation(Role role, String name, String phone, String date, String time, int guests) {
        if (StringUtils.isBlank(name)) {
            return "Въведете име";
        }
        if (phone == null || !phone.matches("0\\d{9}")) {
            return "Въведете валиден телефонен номер";
        }
        if (StringUtils.isAnyBlank(date, time)) {
            return "Изберете дата и час";
        }

        LocalDate reservationDate;
        LocalTime reservationTime;
        try {
            reservationDate = LocalDate.parse(date, DATE_FORMAT);
            reservationTime = LocalTime.parse(time, TIME_FORMAT);
        } catch (DateTimeParseException e) {
            return "Невалиден формат на дата или час";
        }

        if (reservationDate.isBefore(LocalDate.now())) {
            return "Не може да се резервира за минала дата";
        }
        if (reservationTime.isBefore(FIRST_RESERVATION) || reservationTime.isAfter(LAST_RESERVATION)) {
            return "Ресторантът приема резервации от 12:00 до 22:00";
        }
        if (guests < 1) {
            return "Броят на гостите трябва да е поне 1";
        }
        if (role == Role.CLIENT && guests > CLIENT_MAX_GUESTS) {
            return "За групи над 6 души резервацията се прави от служител";
        }
        if (guests > MAX_GUESTS) {
            return "Максималният брой гости е 12";
        }

        RestaurantTable table = findFreeTable(reservationDate, reservationTime, guests);
        if (table == null) {
            return "Няма свободна маса за избрания ден и час";
        }

        ReservationStatus status = role == Role.STAFF ? ReservationStatus.CONFIRMED : ReservationStatus.PENDING;
        database.getReservations().add(
                new Reservation(name, phone, reservationDate, reservationTime, guests, table, role, status));

        return status == ReservationStatus.CONFIRMED
                ? "Резервацията е потвърдена"
                : "Резервацията е изпратена за потвърждение";
    }

    private RestaurantTable findFreeTable(LocalDate date, LocalTime time, int guests) {
        return database.getTables().stream()
                .filter(table -> table.getCapacity() >= guests)
                .filter(table -> isFree(table, date, time))
                .min(Comparator.comparingInt(RestaurantTable::getCapacity))
                .orElse(null);
    }

    private boolean isFree(RestaurantTable table, LocalDate date, LocalTime time) {
        return database.getReservations().stream()
                .filter(reservation -> reservation.getStatus() != ReservationStatus.CANCELLED)
                .noneMatch(reservation -> reservation.getTable() == table
                        && reservation.getDate().equals(date)
                        && reservation.getTime().equals(time));
    }
}
