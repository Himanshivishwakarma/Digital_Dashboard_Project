package digital_board.digital_board.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import digital_board.digital_board.ServiceImpl.SportServiceImpl;

@RestController
public class SportController {
    
    @Autowired
    private SportServiceImpl sportServiceImpl;
}