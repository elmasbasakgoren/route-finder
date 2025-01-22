import com.thy.challenge.entity.Location;
import com.thy.challenge.exceptions.ResourceNotFoundException;
import com.thy.challenge.repository.LocationRepository;
import com.thy.challenge.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllLocations() {
        Location location1 = new Location();
        Location location2 = new Location();
        List<Location> locations = Arrays.asList(location1, location2);

        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> result = locationService.findAll();

        assertEquals(2, result.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void save_ShouldSaveAndReturnLocation() {
        Location location = new Location();

        when(locationRepository.save(location)).thenReturn(location);

        Location result = locationService.save(location);

        assertNotNull(result);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void deleteById_ShouldDeleteLocationById() {
        Long id = 1L;

        locationService.deleteById(id);

        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    void findById_ShouldReturnLocationWhenFound() {
        Long id = 1L;
        Location location = new Location();
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Location result = locationService.findById(id);

        assertNotNull(result);
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> locationService.findById(id));
        assertEquals("Location not found with id: " + id, exception.getMessage());
        verify(locationRepository, times(1)).findById(id);
    }
}

