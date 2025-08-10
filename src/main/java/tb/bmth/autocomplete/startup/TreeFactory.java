package tb.bmth.autocomplete.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tb.bmth.autocomplete.datastructures.Node;
import tb.bmth.autocomplete.datastructures.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TreeFactory {

    private static final char[] firstLetters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

//    @Bean(name = "A")
//    public Tree createSearchTreeA() throws FileNotFoundException {
//        Tree tree = new Tree(new Node('a'));
//        File a = new File("./src/main/resources/wordlist_A.txt");
//        Scanner reader = new Scanner(a);
//        while (reader.hasNext()){
//            String data = reader.nextLine();
//            assert data != null && !data.isEmpty() : "E scanner read in empty line";
//            tree.addWord(data);
//        }
//        tree.validate();
//        return tree;
//    }

    @Bean(name = "searchTrees")
    public List<Tree> createAllSearchTrees() throws FileNotFoundException {
        File[] wordlists = new File("./src/main/resources/wordlists").listFiles();
        assert wordlists != null && wordlists.length >= 1 : "E no dictionaries to read in";
        List<Tree> searchTrees = new ArrayList<>();
        assert wordlists.length == firstLetters.length : "E not enough dictionaries to read in";
        for (int i = 0; i <wordlists.length; i++){
            File file = wordlists[i];
            if (file.isFile()){
                Scanner reader = new Scanner(file);
                Tree tree = new Tree(new Node(firstLetters[i]));
                while (reader.hasNext()){
                    String word = reader.nextLine();
                    assert word != null && !word.isEmpty() : "E scanner read in empty line";
                    if (firstLetters[i] == Character.toLowerCase(word.charAt(0))){
                        tree.addWord(word);
                    }else {
                        System.out.printf("Buchstabe bei wort: %s passt nicht zu Buchstaben %c%n", word, firstLetters[i]);
                    }
                }
                tree.validate();
                searchTrees.add(tree);
            }

        }
        return searchTrees;
    }

}
