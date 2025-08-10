package tb.bmth.autocomplete.datastructures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Node {

    private List<Node> children;
    private char letter;
    private boolean isWordEnd;

    public Node(char letter){
        this.letter = letter;
        this.children = new ArrayList<>();
        this.isWordEnd = false;
        this.validate();
    }

    public Node(char letter, boolean isWordEnd){
        this.letter = letter;
        this.children = new ArrayList<>();
        this.isWordEnd = isWordEnd;
        this.validate();
    }

    @Override
    public String toString(){
       return String.valueOf(this.letter);
    }
    private void validate(){
        assert children != null : "E children-nodes not initialized";
        assert letter != '\u0000': "E letter not set";
    }
}
