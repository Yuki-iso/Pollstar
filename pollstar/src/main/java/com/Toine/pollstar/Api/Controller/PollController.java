package com.Toine.pollstar.Api.Controller;

import com.Toine.pollstar.Core.Model.Container.PollContainer;
import com.Toine.pollstar.Core.Model.Poll;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/poll")
public class PollController
{
    private final PollContainer pollContainer = new PollContainer();

    @GetMapping("/test")
    @ResponseBody
    public String ConfirmTest()
    {
        return "Works!";
    }

    @GetMapping("{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable(value = "id") int id)
    {
        Poll poll = pollContainer.getPoll(id);

        if(poll != null)
        {
            return ResponseEntity.ok().body(poll);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    //POST at http://localhost:XXXX/poll/
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll)
    {
        if(!pollContainer.addPoll(poll))
        {
            String entity = "Poll with id " + poll.getPollID() + " already exists.";
            return new ResponseEntity(entity,HttpStatus.CONFLICT);
        }
        else
        {
            String url = "poll" + "/" + poll.getPollID();
            URI uri = URI.create(url);
            return new ResponseEntity(uri,HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() //add @RequestParam to check by pollOwner
    {
        if(pollContainer.getPolls() != null) {return ResponseEntity.ok().body(pollContainer.getPolls());}
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}