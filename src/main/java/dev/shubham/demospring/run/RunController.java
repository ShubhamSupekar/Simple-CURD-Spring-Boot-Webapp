package dev.shubham.demospring.run;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import dev.shubham.demospring.run.JdbcClientRunRepository.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;
    private final JdbcClientRunRepository jdbcClientRunRepository;

    public RunController(RunRepository runRepository, JdbcClientRunRepository jdbcClientRunRepository) {
        this.runRepository = runRepository;
        this.jdbcClientRunRepository = jdbcClientRunRepository;
    }

    @GetMapping("")
    List <Run>findAll(){
        return  runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run =  runRepository.findById(id);
        if(run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    //post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run){
        runRepository.save(run);
    }

//    //put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id){
        jdbcClientRunRepository.update(run, id);
    }

//    //delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        runRepository.delete(runRepository.findById(id).get());
    }

    //Finding by location
    @GetMapping("/location/{location}")
    List<Run> findByLocation(@PathVariable String location){
        return runRepository.findAllByLocation(location);
    }
}
