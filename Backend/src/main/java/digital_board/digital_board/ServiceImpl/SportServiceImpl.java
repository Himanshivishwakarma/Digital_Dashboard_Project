package digital_board.digital_board.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Sport;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.SportRepository;
import digital_board.digital_board.Servies.SportService;
import java.util.*;

@Service
public class SportServiceImpl implements SportService {

  @Autowired
  SportRepository sportRepository;

  public SportServiceImpl(SportRepository sportRepository) {
    this.sportRepository = sportRepository;
  }

  @Override
  public Sport addSport(Sport sport) {
    Sport sport2 = sportRepository.save(sport);
    return sport2;
  }

  @Override
  public Sport getSportById(String sportId) {
    Sport sport = sportRepository.findById(sportId)
        .orElseThrow(() -> new ResourceNotFoundException("Sport Id not found"));
    return sport;
  }

  @Override 
  public List<Sport> getAllSport() {
    List<Sport> sports = sportRepository.findAll();
    return sports;
  }

  @Override
  public Sport updateSport(Sport sport, String sportId) {
    Sport sports = sportRepository.findById(sportId)
        .orElseThrow(() -> new ResourceNotFoundException("Sport Id not found"));
    sports.setSportName(sport.getSportName());
    sports.setSportDescription(sport.getSportDescription());
    sports.setSportStartDate(sport.getSportStartDate());
    sports.setCreatedBy(sport.getCreatedBy());
    sports.setSportEndDate(sport.getSportEndDate());
    return sportRepository.save(sports);
  }

  @Override
  public List<Sport> getSportsByName(String sportName, Sort sort) {
    return sportRepository.findBySportName(sportName, sort);
  }

  @Override
  public List<Sport> getAllSportsSorted(Sort sort) {
    return sportRepository.findAll(sort);

  }

  
  
}

