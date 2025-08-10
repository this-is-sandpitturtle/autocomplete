package tb.bmth.autocomplete.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tb.bmth.autocomplete.services.AutocompletionService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutocompletionController {

    private final AutocompletionService service;

    @Autowired
    public AutocompletionController(AutocompletionService service){
        this.service = service;
    }

    @GetMapping("/autocomplete/{prefix}")
    public ResponseEntity<List<String>> autocomplete(@PathVariable String prefix){
        long l = System.currentTimeMillis();
        assert prefix != null && !prefix.isEmpty() : "prefix is null or empty";
        assert prefix.length() >= 3 : "prefix-length has to be longer or equal to 3";
        List<String> out = service.getSuggestions(prefix);
        assert out != null : "E out is null";
        long e = System.currentTimeMillis();
        System.out.println(e-l + "ms");
        return ResponseEntity.ok(out);
    }

}
