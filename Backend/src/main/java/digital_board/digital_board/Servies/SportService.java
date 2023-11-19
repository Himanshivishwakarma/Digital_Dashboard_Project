package digital_board.digital_board.Servies;

import java.util.List;

import org.springframework.data.domain.Sort;

import digital_board.digital_board.Entity.Sport;

public interface SportService {

    public Sport createSportByUser(Sport sport);

    public Sport getSportBySportId(String sportId);

    public List<Sport> getSportByCreatedUserName(String userName);

    public List<Sport> getAllSport();

    public List<Sport> getSportsByName(String sportName, Sort sort);

    public List<Sport> getAllSportsSorted(Sort sort);

}
