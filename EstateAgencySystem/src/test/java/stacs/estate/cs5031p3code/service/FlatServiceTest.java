package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Flat;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing FlatService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
@Transactional
public class FlatServiceTest {

    /**
     * The flat service.
     */
    @Autowired
    private FlatService flatService;

    /**
     * The method for testing create flat by building id.
     */
    @Test
    public void testCreateFlatByBuildingId() {
        // 1. Flat properties are null
        var flat1 = Flat.builder()
                .buildingId(null)
                .flatArea(null)
                .flatName(null)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            flatService.createFlatByBuildingId(null, flat1);
        });
        var expectedMessage = "Building id, flat name or area cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Building is not existed
        var flat2 = Flat.builder()
                .buildingId(5L)
                .flatArea(new BigDecimal("100.0"))
                .flatName("C floor")
                .build();

        exception = assertThrows(EstateException.class, () -> {
            flatService.createFlatByBuildingId(5L, flat2);
        });
        expectedMessage = "Building is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Flat name is existed in a certain building
        var flat3 = Flat.builder()
                .buildingId(1L)
                .flatArea(new BigDecimal("100.0"))
                .flatName("A floor")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            flatService.createFlatByBuildingId(1L, flat3);
        });
        expectedMessage = "Flat name is existed in a certain building!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Creating flat successful
        var flat4 = Flat.builder()
                .buildingId(1L)
                .flatArea(new BigDecimal("100.0"))
                .flatName("C floor")
                .build();
        assertDoesNotThrow(() -> {
            flatService.createFlatByBuildingId(1L, flat4);
        });
    }

    /**
     * The method for testing deleteFlatByFlatId.
     */
    @Test
    public void testDeleteFlatByFlatId() {
        // 1. flat id is null
        Long flatId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            flatService.deleteFlatByFlatId(flatId1);
        });
        var expectedMessage = "Flat id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. flat is not existed
        Long flatId2 = 4L;
        exception = assertThrows(EstateException.class, () -> {
            flatService.deleteFlatByFlatId(flatId2);
        });
        expectedMessage = "Flat is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. There have connection between flat and user, cannot remove.
        Long flatId3 = 2L;
        exception = assertThrows(EstateException.class, () -> {
            flatService.deleteFlatByFlatId(flatId3);
        });
        expectedMessage = "Flat has users in there, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Delete successful
        Long flatId4 = 3L;
        assertDoesNotThrow(() -> {
            flatService.deleteFlatByFlatId(flatId4);
        });
    }

    /**
     * The method for testing updateFlatByFlatId.
     */
    @Test
    public void testUpdateFlatByFlatId() {
        // 1. flat or flat id is empty
        var flat1 = Flat.builder()
                .flatId(null)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            flatService.updateFlatByFlatId(flat1.getFlatId(), flat1);
        });
        var expectedMessage = "Flat or flat id is empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Check building id.
        var flat2 = Flat.builder()
                .flatId(3L)
                .buildingId(5L)
                .build();
        exception = assertThrows(EstateException.class, () -> {
            flatService.updateFlatByFlatId(flat2.getFlatId(), flat2);
        });
        expectedMessage = "Building is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Keep the uniqueness of flat name.
        var flat3 = Flat.builder()
                .flatId(2L)
                .buildingId(1L)
                .flatName("A floor")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            flatService.updateFlatByFlatId(flat3.getFlatId(), flat3);
        });
        expectedMessage = "Flat name is existed in a certain building!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Flat name cannot be empty
        var flat4 = Flat.builder()
                .flatId(3L)
                .buildingId(2L)
                .flatName("")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            flatService.updateFlatByFlatId(flat4.getFlatId(), flat4);
        });
        expectedMessage = "Flat name cannot be empty!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 5. Update successful!
        var flat5 = Flat.builder()
                .flatId(3L)
                .buildingId(2L)
                .flatName("Lang")
                .build();
        assertDoesNotThrow(() -> {
            flatService.updateFlatByFlatId(flat5.getFlatId(), flat5);
        });
    }

    /**
     * The method for testing getFlatList
     */
    @Test
    public void testGetFlatList() {
        assertDoesNotThrow(() -> {
            var flatList = flatService.getFlatList();
            assertEquals(3, flatList.size());
        });
    }

    /**
     * The method for testing getFlatListByBuildingId
     */
    @Test
    public void testGetFlatListByBuildingId() {
        // building id is null
        Long buildingId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            flatService.getFlatListByBuildingId(buildingId1);
        });
        var expectedMessage = "Building id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // building id is not null
        Long buildingId2 = 1L;
        assertDoesNotThrow(() -> {
            var flatList = flatService.getFlatListByBuildingId(buildingId2);
            assertEquals(2, flatList.size());
        });
    }
}
