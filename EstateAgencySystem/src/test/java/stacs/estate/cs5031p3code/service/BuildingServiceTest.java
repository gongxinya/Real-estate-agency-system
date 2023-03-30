package stacs.estate.cs5031p3code.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import stacs.estate.cs5031p3code.exception.EstateException;
import stacs.estate.cs5031p3code.model.po.Building;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class for testing BuildingService.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootTest
@Transactional
public class BuildingServiceTest {

    /**
     * The building service.
     */
    @Autowired
    private BuildingService buildingService;

    /**
     * The method for testing creating building.
     */
    @Test
    public void testCreateBuilding() {
        // 1. Building properties are null.
        var building1 = Building.builder()
                .buildingName(null)
                .buildingAddress(null)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            buildingService.createBuilding(building1);
        });
        var expectedMessage = "Building name or address cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Building name is existed.
        var building2 = Building.builder()
                .buildingName("Powell Hall")
                .buildingAddress("St Andrews")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            buildingService.createBuilding(building2);
        });
        expectedMessage = "Building name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Creating building successful
        var building3 = Building.builder()
                .buildingName("John Burnet Hall")
                .buildingAddress("St Andrews")
                .build();
        assertDoesNotThrow(() -> {
            buildingService.createBuilding(building3);
        });
    }

    /**
     * The method for testing delete building by building id.
     */
    @Test
    public void testDeleteBuildingById() {
        // 1. building id is null
        Long buildingId1 = null;
        var exception = assertThrows(EstateException.class, () -> {
            buildingService.deleteBuildingById(buildingId1);
        });
        var expectedMessage = "Building id cannot be empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. building is not existed
        Long buildingId2 = 5L;
        exception = assertThrows(EstateException.class, () -> {
            buildingService.deleteBuildingById(buildingId2);
        });
        expectedMessage = "Building is not existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. User has flats in there, cannot remove
        Long buildingId3 = 2L;
        exception = assertThrows(EstateException.class, () -> {
            buildingService.deleteBuildingById(buildingId3);
        });
        expectedMessage = "Building has flats in there, cannot remove!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Delete successful
        Long buildingId4 = 3L;
        assertDoesNotThrow(() -> {
            buildingService.deleteBuildingById(buildingId4);
        });
    }

    /**
     * The method for updating building by building id.
     */
    @Test
    public void testUpdateBuildingByBuildingId() {
        // 1. Building or building id is empty
        var building1 = Building.builder()
                .buildingId(null)
                .build();
        var exception = assertThrows(EstateException.class, () -> {
            buildingService.updateBuildingByBuildingId(building1.getBuildingId(), building1);
        });
        var expectedMessage = "Building or building id is empty!";
        var actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 2. Building name is existed!
        var building2 = Building.builder()
                .buildingId(2L)
                .buildingName("David")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            buildingService.updateBuildingByBuildingId(building2.getBuildingId(), building2);
        });
        expectedMessage = "Building name is existed!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 3. Building name can not be empty.
        var building3 = Building.builder()
                .buildingId(2L)
                .buildingName("")
                .build();
        exception = assertThrows(EstateException.class, () -> {
            buildingService.updateBuildingByBuildingId(building3.getBuildingId(), building3);
        });
        expectedMessage = "Building name cannot be empty!";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        // 4. Update successful!
        var building4 = Building.builder()
                .buildingId(2L)
                .buildingName("DRA")
                .buildingAddress("Fife")
                .build();
        assertDoesNotThrow(() -> {
            buildingService.updateBuildingByBuildingId(building4.getBuildingId(), building4);
        });
    }

    /*
     * The method for testing getting building list.
     */
    @Test
    public void testBuildingList() {
        assertDoesNotThrow(() -> {
            var buildingList = buildingService.getBuildingList();
            assertEquals(3, buildingList.size());
        });
    }
}
