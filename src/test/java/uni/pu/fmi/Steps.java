package uni.pu.fmi;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.pu.fmi.models.Role;
import uni.pu.fmi.service.ReservationService;

import static org.junit.Assert.assertEquals;

public class Steps {
    private Role role;
    private String name;
    private String phone;
    private String date;
    private String time;
    private int guests;
    private String resultMessage;

    @Given("Потребител с роля {string} е на страницата за резервация на маса")
    public void openReservationPage(String roleName) {
        role = toRole(roleName);
    }

    @When("въвежда име {string}")
    public void enterName(String name) {
        this.name = name;
    }

    @When("въвежда телефон {string}")
    public void enterPhone(String phone) {
        this.phone = phone;
    }

    @When("избира дата {string}")
    public void chooseDate(String date) {
        this.date = date;
    }

    @When("избира час {string}")
    public void chooseTime(String time) {
        this.time = time;
    }

    @When("въвежда брой гости {int}")
    public void enterGuests(int guests) {
        this.guests = guests;
    }

    @When("натиска бутона за резервиране")
    public void clickReserveButton() {
        resultMessage = new ReservationService().makeReservation(role, name, phone, date, time, guests);
    }

    @Then("показва се съобщение {string}")
    public void checkResultMessage(String expectedMessage) {
        assertEquals(expectedMessage, resultMessage);
    }

    private Role toRole(String roleName) {
        switch (roleName) {
            case "Клиент":
                return Role.CLIENT;
            case "Служител":
                return Role.STAFF;
            default:
                throw new IllegalArgumentException("Непозната роля: " + roleName);
        }
    }
}
