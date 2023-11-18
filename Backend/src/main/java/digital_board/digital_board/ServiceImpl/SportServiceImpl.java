package digital_board.digital_board.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Sport;
import digital_board.digital_board.Repository.SportRepository;
import digital_board.digital_board.Servies.SportService;


@Service
public class SportServiceImpl implements SportService{

    @Autowired
    SportRepository sportRepository; 

    @Override
    public Sport createSportByUser(Sport sport) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSportByUser'");
    }

    @Override
    public Sport getSportBySportId(String sportId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSportBySportId'");
    }

    @Override
    public List<Sport> getSportByCreatedUserName(String userName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSportByCreatedUserName'");
    }

    @Override
    public List<Sport> getAllSport() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSport'");
    }

    @Override
    public List<Sport> getSportsByName(String sportName, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSportsByName'");
    }

    @Override
    public List<Sport> getAllSportsSorted(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSportsSorted'");
    }
    
}
