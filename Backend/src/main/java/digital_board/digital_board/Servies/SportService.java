package digital_board.digital_board.Servies;

import digital_board.digital_board.Entity.Sport;

import java.util.*;

public interface SportService {
    
    Sport addSport(Sport sport);

    Sport getSportById(String sportId);
    
   List<Sport> getAllSport();

    Sport updateSport(Sport sport,String sportName);

}