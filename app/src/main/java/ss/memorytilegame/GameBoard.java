package ss.memorytilegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Soneer Sainion on 9/15/2016.
 */
public class GameBoard implements java.io.Serializable {
    private String scorePoints ="0";
    String hintUsed = "false";
    List<String> listOfPokemons = new ArrayList<String>(Arrays.asList("pikachu", "pikachu", "bullbasaur", "bullbasaur", "snorlax", "snorlax", "eevee", "eevee", "charmander", "charmander", "dratini", "dratini", "abra", "abra", "bellsprout", "bellsprout", "caterpie", "caterpie", "mew", "mew"));;
    List<String> listOfUsedPokemon =new ArrayList<String>(Arrays.asList(" "));


    public GameBoard(){
        Collections.shuffle(listOfPokemons);

    }
    public GameBoard(String score_points, String hint_used,  List<String> list_of_pokemons, ArrayList<String> list_of_used_pokemons)
    {
        scorePoints = score_points;
        hintUsed=hint_used;
        listOfPokemons = list_of_pokemons;
        listOfUsedPokemon = list_of_used_pokemons;
    }

    public void useHint()
    {
       hintUsed = "true";
    }
    public String setScore(int score)
    {
        scorePoints = (score + Integer.parseInt(scorePoints))+"";
        return scorePoints;
    }
    public String getScorePoints(){
        return scorePoints;
    }

    public String getHintUsed(){
        return hintUsed;
    }

    public List<String> getListOfPokemons(){
        return listOfPokemons;
    }

    public List<String> getListOfUsedPokemon(){
        return listOfUsedPokemon;
    }

    public void setListOfPokemon(List<String> temp)
    {
        for(int i =0; i<listOfPokemons.size();i++){
        listOfPokemons.set(i,temp.get(i));
    }

    }
    public void setListOfUsedPokemon(List<String> temp)
    {

        for(int i =0; i<temp.size();i++){
            listOfPokemons.set(i,temp.get(i));
        }

    }
    public void addUsedPokemon(String usedPokemon){
        listOfUsedPokemon.add(usedPokemon);
    }
}
