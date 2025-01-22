import com.thy.challenge.entity.Location;
import com.thy.challenge.entity.Transportation;
import com.thy.challenge.exceptions.ResourceNotFoundException;
import com.thy.challenge.repository.TransportationRepository;
import com.thy.challenge.service.TransportationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportationServiceTest {

    @Mock
    private TransportationRepository transportationRepository;

    @InjectMocks
    private TransportationService transportationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllTransportations() {
        Transportation transportation1 = new Transportation();
        Transportation transportation2 = new Transportation();
        List<Transportation> transportations = Arrays.asList(transportation1, transportation2);

        when(transportationRepository.findAll()).thenReturn(transportations);

        List<Transportation> result = transportationService.findAll();

        assertEquals(2, result.size());
        verify(transportationRepository, times(1)).findAll();
    }

    @Test
    void save_ShouldSaveAndReturnTransportation() {
        Transportation transportation = new Transportation();

        when(transportationRepository.save(transportation)).thenReturn(transportation);

        Transportation result = transportationService.save(transportation);

        assertNotNull(result);
        verify(transportationRepository, times(1)).save(transportation);
    }

    @Test
    void deleteById_ShouldDeleteTransportationById() {
        Long id = 1L;

        transportationService.deleteById(id);

        verify(transportationRepository, times(1)).deleteById(id);
    }

    @Test
    void findById_ShouldReturnTransportationWhenFound() {
        Long id = 1L;
        Transportation transportation = new Transportation();
        when(transportationRepository.findById(id)).thenReturn(Optional.of(transportation));

        Transportation result = transportationService.findById(id);

        assertNotNull(result);
        verify(transportationRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(transportationRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> transportationService.findById(id));
        assertEquals("Transportation not found with id: " + id, exception.getMessage());
        verify(transportationRepository, times(1)).findById(id);
    }

    @Test
    void findByOriginAndDestination_ShouldReturnMatchingTransportations() {
        Location origin = new Location();
        Location destination = new Location();
        Transportation transportation = new Transportation();
        transportation.setOrigin(origin);
        transportation.setDestination(destination);

        List<Transportation> transportations = Collections.singletonList(transportation);
        when(transportationRepository.findByOriginAndDestination(origin, destination)).thenReturn(transportations);

        List<Transportation> result = transportationService.findByOriginAndDestination(origin, destination);

        assertEquals(1, result.size());
        verify(transportationRepository, times(1)).findByOriginAndDestination(origin, destination);
    }
}
