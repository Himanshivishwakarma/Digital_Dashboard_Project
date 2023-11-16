package digital_board.digital_board.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import digital_board.digital_board.Entity.Sport;
import digital_board.digital_board.Repository.SportRepository;
import digital_board.digital_board.ServiceImpl.SportServiceImpl;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SportServiceImplTest {

    @Mock
    SportRepository sportRepository;

    @InjectMocks
    SportServiceImpl sportServiceImpl;

    @Test
    void testgetSportById() {

        String sportid = "11";
        Sport exppecSport = new Sport(sportid, "Cricket", "SPL", "23-11-2023", "24-11-2023", "mangal singh");
        Mockito.when(sportRepository.findById(eq(sportid))).thenReturn(Optional.of(exppecSport));
        Sport resultSport = sportServiceImpl.getSportById(sportid);
        assertEquals(exppecSport, resultSport);
    }

    @Test
    void testGetAllNotice() {

        List<Sport> expectedSports = Arrays.asList(
                new Sport("123", "Bollowoll", "spl", "15-11-2023", "17-11-2023", "devendra singh"),
                new Sport("124", "kho-kho", "ipl", "16-11-2023", "18-11-2023", "aditya singh"));
        Mockito.when(sportRepository.findAll()).thenReturn(expectedSports);
        List<Sport> resultSports = sportServiceImpl.getAllSport();
        assertEquals(expectedSports, resultSports);

    }

    @Test
    void testaddSport() {

        Sport inpuSport = new Sport("121", "football", "university", "15-11-2023", "16-11-2023", "raj singh");
        Sport savedSport = new Sport("122", "football", "university", "15-11-2023", "16-11-2023", "raj singh");

        Mockito.when(sportRepository.save(any(Sport.class))).thenReturn(savedSport);

        Sport sportResult = sportServiceImpl.addSport(inpuSport);
        assertEquals(savedSport, sportResult);
    }

    @Test
    void testUpdateSport() {
      
        SportRepository sportRepository = mock(SportRepository.class);
        Sport existingSport = new Sport("1", "Football", "spl", "2023-11-11", "2023-12-31", "mangal singh");
        when(sportRepository.findById("1")).thenReturn(java.util.Optional.of(existingSport));
        when(sportRepository.save(any(Sport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SportServiceImpl sportService = new SportServiceImpl(sportRepository);

        Sport updatedSport = sportService.updateSport(
                new Sport("1", "cricket", "ipl", "2023-02-01", "2023-11-30", "anand singh"),
                "1");

        assertNotNull(updatedSport);
        assertEquals("cricket", updatedSport.getSportName());
        assertEquals("ipl", updatedSport.getSportDescription());
        assertEquals("2023-02-01", updatedSport.getSportStartDate());
        assertEquals("2023-11-30", updatedSport.getSportEndDate());
        assertEquals("anand singh", updatedSport.getCreatedBy());

        verify(sportRepository, times(1)).findById("1");
        verify(sportRepository, times(1)).save(existingSport);
    }
}