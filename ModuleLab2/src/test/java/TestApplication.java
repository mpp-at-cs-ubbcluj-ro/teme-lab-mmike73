import org.example.model.ComputerRepairRequest;
import org.example.repository.AbstractRepository;
import org.example.repository.RequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.ref.ReferenceQueue;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestApplication {
    @Test
    @DisplayName("Test1")
    public void testCreateRequestGettersSetters() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        String date = LocalDate.now().toString();
        computerRepairRequest.setDate(date);
        assertEquals(date, computerRepairRequest.getDate());

        computerRepairRequest.setID(1000);
        assertEquals(1000, computerRepairRequest.getID());

        computerRepairRequest.setModel("Macbook Pro");
        assertEquals("Macbook Pro", computerRepairRequest.getModel());

        computerRepairRequest.setPhoneNumber("012949072389312");
        assertEquals("012949072389312", computerRepairRequest.getPhoneNumber());

        computerRepairRequest.setOwnerName("Spiderman");
        assertEquals("Spiderman", computerRepairRequest.getOwnerName());

        computerRepairRequest.setOwnerAddress("Bistrita");
        assertEquals("Bistrita", computerRepairRequest.getOwnerAddress());
    }

    @Test
    @DisplayName("Test2")
    public void testCreateAndGetters() {
        RequestRepository ar = new RequestRepository();

        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        computerRepairRequest.setID(1);

        assertEquals(0, ar.getAll().size());

        ar.add(computerRepairRequest);

        assertEquals(1, ar.getAll().size());

        ComputerRepairRequest retrieved = (ComputerRepairRequest) ar.findById(1);

        assertNotNull(retrieved);

        assertEquals(computerRepairRequest, retrieved);
    }

}
