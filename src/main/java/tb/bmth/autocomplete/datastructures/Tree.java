package tb.bmth.autocomplete.datastructures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
public class Tree {

    private Node root;

    public Tree(Node root){
        this.root = root;
    }

    public void validate(){
        assert root != null && root.getChildren() != null : "E tree not initialized";
        assert !root.getChildren().isEmpty() : "E root-children not properly loaded";
        System.out.println(root);
        System.out.println(root.getChildren());
    }

    //TODO:  Not case insensitive... it works with capitalized A but not with lowercase a for some reason
    public void addWord(String word) {
        assert word != null && !word.isEmpty() : "E word is null or empty";
        char[] characters = word.toCharArray();
        assert characters.length >= 2 : "E characters has size 1";
        assert Character.toLowerCase(characters[0]) == root.getLetter() : "E wrong search tree";
        for (int i = 0; i < characters.length; i++){
            characters[i] = Character.toLowerCase(characters[i]);
        }
        Node tempRoot = root;
        // start = 1 because the first letter is already in the tree root
        // maybe already call with substring?!
        //TODO: Repeating the first letter returns autocomplete results with this repeated letter
        for (int i = 1; i < word.length(); i++) {
            char character = characters[i];
            if (tempRoot.getChildren().isEmpty()) {
                if (i == word.length() - 1){
                    tempRoot.getChildren().add(new Node(Character.toLowerCase(character), true));
                } else {
                    tempRoot.getChildren().add(new Node(Character.toLowerCase(character)));
                    tempRoot = tempRoot.getChildren().get(tempRoot.getChildren().size() - 1);
                }
            } else {
                int j = 0;
                boolean contained = false;
                while (j < tempRoot.getChildren().size()) {
                    if (tempRoot.getChildren().get(j).getLetter() == character) {
                        contained = true;
                        break;
                    }
                    j++;
                }
                if (contained) {
                    tempRoot = tempRoot.getChildren().get(j);
                } else {
                    if (i == word.length() - 1){
                        tempRoot.getChildren().add(new Node(Character.toLowerCase(character), true));
                    } else {
                        tempRoot.getChildren().add(new Node(Character.toLowerCase(character)));
                        tempRoot = tempRoot.getChildren().get(tempRoot.getChildren().size() - 1);
                    }
                }
            }
        }
    }
    public List<String> search(String prefix){
        assert prefix != null : "E prefix is null";
        assert prefix.length() >= 3 : "E prefix-length is less than 3";
        char[] prefixArr = prefix.toCharArray();
        Node tempRoot = initializeTempRoot(prefixArr);
        //go along some paths and suggests words for autocompletion
        //how to do this without creating new char[]?
        List<char[]> suggestions = new ArrayList<>();
        char[]  suggestion = new char[50]; //reusing the same char[] should save some memory and time
        for (int i = 0; i < 10; i++){
            int j = 0;
            while (j < prefixArr.length){
                suggestion[j] = prefixArr[j];
                j++;
            }
            int k = prefixArr.length;
            Random random = new Random();
            while (!tempRoot.getChildren().isEmpty()){
                //TODO: just take first number (i.e. 10) and if not enough cut off
                int index = random.ints(0, tempRoot.getChildren().size()).findFirst().getAsInt();
                suggestion[k] = tempRoot.getChildren().get(index).getLetter();
                if (tempRoot.getChildren().get(index).isWordEnd()) {
                   break;
                }
                tempRoot = tempRoot.getChildren().get(index);
                k++;
            }

            boolean contained = false;
            for(int l = 0; l < suggestions.size(); l++) {
                char[] containedSuggestion = suggestions.get(l);
                for (int p = prefixArr.length; p < containedSuggestion.length; p++) {
                    if (containedSuggestion[p] != suggestion[p]) {
                        contained = false;
                        break;
                    }
                    if (suggestion[p] == '\u0000') {
                        contained = true;
                        break;
                    }
                }
            }

            if (!contained) {
                suggestions.add(suggestion);
            }


//            if(!suggestions.contains(suggestion)){
//                suggestions.add(suggestion);
//            }
//            index++;
            //but maybe it changes the value of all suggestions... only one way to find out
//            for (char[] a : suggestions){
//                System.out.println(String.valueOf(a));
//            }
            tempRoot = initializeTempRoot(prefixArr);
            suggestion = new char[50];
        }
        List<String> out = suggestions.stream().map(String::valueOf).toList();
        for (String s : out){
            System.out.println(s);
        }
        assert out.size() <= 10 : "E too much autocomplete-suggestions";
        return out;
    }

    private Node initializeTempRoot(char[] prefixArr) {
        Node tempRoot = root;
        for(int i = 1; i < prefixArr.length; i++){
            List<Node> tempNodes = tempRoot.getChildren();
            assert tempNodes != null : "E tree was not properly initialized";
            for (int j = 0; j < tempNodes.size(); j++){
                if (prefixArr[i] == tempNodes.get(j).getLetter()) {
                   tempRoot = tempNodes.get(j);
                   break;
               }
            }
        }
        return tempRoot;
    }

}
