package digital_board.digital_board.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import digital_board.digital_board.Entity.Sport;
import digital_board.digital_board.ServiceImpl.SportServiceImpl;
import java.util.*;

@RestController
@RequestMapping("/api/sport/")
public class SportController {

  @Autowired
  SportServiceImpl sportServiceImpl;

  @PostMapping("/addsport")
  public ResponseEntity<Sport> adddSport(@RequestBody Sport sport) {
    Sport sport2 = sportServiceImpl.addSport(sport);
    return new ResponseEntity<Sport>(sport2, HttpStatus.CREATED);
  }

  @PutMapping("/updatesport/{sportId}")
  public ResponseEntity<Sport> updateSport(@RequestBody Sport sport, @PathVariable String sportId) {
    Sport sport2 = sportServiceImpl.updateSport(sport, sportId);
    return new ResponseEntity<Sport>(sport2, HttpStatus.OK);
  }

  @GetMapping("/getsport/{sportId}")
  public ResponseEntity<Sport> getSport(@PathVariable String sportId) {
    Sport sport2 = sportServiceImpl.getSportById(sportId);
    return new ResponseEntity<Sport>(sport2, HttpStatus.ACCEPTED);
  }

  @GetMapping("/getsports")
  public ResponseEntity<List<Sport>> getSports() {
    List<Sport> sport2 = sportServiceImpl.getAllSport();
    return new ResponseEntity<List<Sport>>(sport2, HttpStatus.ACCEPTED);
  }

  @GetMapping("/bySportName/{sportName}")
  public List<Sport> getSportsByName(@PathVariable String sportName, @RequestParam(required = false) String sort) {
    return sportServiceImpl.getSportsByName(sportName, getSortObject(sort));
  }

  @GetMapping("/getSortedSport")
  public List<Sport> getAllSportsSorted(@RequestParam(required = false) String sort) {
    return sportServiceImpl.getAllSportsSorted(getSortObject(sort));
  }

  



  private Sort getSortObject(String sort) {
    if (sort != null && sort.equalsIgnoreCase("desc")) {
      return Sort.by(Sort.Direction.DESC, "sportStartDate"); // Change the field as per your requirement
    } else {
      return Sort.by(Sort.Direction.ASC, "sportStartDate"); // Change the field as per your requirement
    }
  }
}