package digital_board.digital_board.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;


import digital_board.digital_board.Entity.Sport;


@SpringBootTest
public class SportRepositoryTest {
    
    @Autowired
    SportRepository sportRepository;

      @Test
    public void testFindBySportName() {
        // Given
        String sportNameToSearch = "Football";
        Sort sort = Sort.by(Sort.Direction.ASC, "sportCreatedDate"); 

        // When
        List<Sport> foundSports = sportRepository.findBySportName(sportNameToSearch, sort);

        // Then
        assertEquals(0, foundSports.size());

}

    @Test
    public void testFindAll() {
        // Given
        Sort sort = Sort.by(Sort.Direction.ASC, "sportCreatedDate"); 

        // When
        List<Sport> allSports = sportRepository.findAll(sort);

        // Then
        assertEquals(11, allSports.size());

}

}