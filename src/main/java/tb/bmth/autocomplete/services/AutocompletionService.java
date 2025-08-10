package tb.bmth.autocomplete.services;

import org.springframework.stereotype.Service;
import tb.bmth.autocomplete.datastructures.Tree;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutocompletionService {

    private final List<Tree> searchTrees;

    public AutocompletionService(final List<Tree> searchTrees){
        this.searchTrees = searchTrees;
    }
    public List<String> getSuggestions(String prefix){
        assert prefix != null && !prefix.isEmpty() : "E prefix is null or empty";
        assert prefix.length() >= 3 : "E prefix-length has to be longer or equal to 3";
        List<String> suggestions = new ArrayList<>();
        for (Tree tree : searchTrees){
            if (tree.getRoot().getLetter() == Character.toLowerCase(prefix.charAt(0))){
                suggestions = tree.search(prefix);
            }
        }

        assert suggestions != null && !suggestions.isEmpty() : "E no Autcompletion possible";
        return suggestions;
    }
}
