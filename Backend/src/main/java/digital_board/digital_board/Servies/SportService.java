package digital_board.digital_board.Servies;

import digital_board.digital_board.Entity.Sport;

import org.springframework.data.domain.Sort;


import java.util.*;

public interface SportService {
    
    Sport addSport(Sport sport);

    Sport getSportById(String sportId);
    
   List<Sport> getAllSport();

    Sport updateSport(Sport sport,String sportName);
    
    public List<Sport> getSportsByName(String sportName, Sort sort);

    public List<Sport> getAllSportsSorted(Sort sort);

   

}