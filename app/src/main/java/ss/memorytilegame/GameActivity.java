package ss.memorytilegame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    GameBoard currentBoard;
    boolean clickable = true;
    Button button1A,button1B, button1C, button1D, button2A, button2B, button2C, button2D, button3A, button3B, button3C, button3D,button4A,button4B, button4C, button4D, button5A, button5B, button5C, button5D;
    private static final int[] BUTTON_IDS = {
            R.id.button1A, R.id.button1B, R.id.button1C, R.id.button1D,
            R.id.button2A, R.id.button2B, R.id.button2C, R.id.button2D,
            R.id.button1A, R.id.button1B, R.id.button1C, R.id.button1D,
            R.id.button3A, R.id.button3B, R.id.button3C, R.id.button3D,
            R.id.button4A, R.id.button4B, R.id.button4C, R.id.button4D,
            R.id.button5A, R.id.button5B, R.id.button5C, R.id.button5D
    };

    int amountOfSelectedButtons = 0;
    String selectedButtonOne = "";
    Button selectedButton;
    boolean hintUsed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            GameBoard model = (GameBoard) getIntent().getSerializableExtra("currentBoard");
            currentBoard =  model;


            if(currentBoard != null && currentBoard.getHintUsed().equals("true"))
            {
                ( (Button) findViewById(R.id.buttonHint)).setEnabled(false);
            }
        }
        else{
            currentBoard = new GameBoard();

        }

        initializeVariables();
        if(currentBoard.getListOfUsedPokemon().size()>0) {
            disablePokemon();
        }


    }

    public void disablePokemon()
    {
        for (String pokemon : currentBoard.getListOfUsedPokemon()) {
            if(pokemon.length()>0) {
                for (int id : BUTTON_IDS) {
                    if (((Button) findViewById(id)).getText().equals(pokemon)) {
                        ((Button) findViewById(id)).setEnabled(false);
                    }

                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeVariables(){
        //Row 1
        button1A = (Button) findViewById(R.id.button1A);
        button1B = (Button) findViewById(R.id.button1B);
        button1C = (Button) findViewById(R.id.button1C);
        button1D = (Button) findViewById(R.id.button1D);

        //Row 2
        button2A = (Button) findViewById(R.id.button2A);
        button2B = (Button) findViewById(R.id.button2B);
        button2C = (Button) findViewById(R.id.button2C);
        button2D = (Button) findViewById(R.id.button2D);

        //Row 3
        button3A = (Button) findViewById(R.id.button3A);
        button3B = (Button) findViewById(R.id.button3B);
        button3C = (Button) findViewById(R.id.button3C);
        button3D = (Button) findViewById(R.id.button3D);

        //Row 4
        button4A = (Button) findViewById(R.id.button4A);
        button4B = (Button) findViewById(R.id.button4B);
        button4C = (Button) findViewById(R.id.button4C);
        button4D = (Button) findViewById(R.id.button4D);

        //Row 5
        button5A = (Button) findViewById(R.id.button5A);
        button5B = (Button) findViewById(R.id.button5B);
        button5C = (Button) findViewById(R.id.button5C);
        button5D = (Button) findViewById(R.id.button5D);

        for(int id : BUTTON_IDS) {
            ((Button) findViewById(id)).setTextSize(0);
            gameLogic(((Button) findViewById(id)));
        }
        populatePokeBalls();

        ((TextView)findViewById(R.id.score)).setText(currentBoard.getScorePoints());
    }

    public void gameLogic(final Button button)
    {
        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // show interest in events resulting from ACTION_DOWN
                if (event.getAction() == MotionEvent.ACTION_DOWN) return true;

                if (event.getAction() != MotionEvent.ACTION_UP) return false;
                if(clickable) {
                    if (amountOfSelectedButtons == 0) {
                        Animation shake = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                        button.startAnimation(shake);
                        selectedButton = button;
                        button.setPressed(true);
                        amountOfSelectedButtons = 1;
                        selectedButtonOne = (String) button.getText();
                    } else if (amountOfSelectedButtons == 1 && selectedButtonOne.equals((String) button.getText()) && button.getId() != selectedButton.getId()) {

                        currentBoard.addUsedPokemon(selectedButtonOne);
                        ((TextView) findViewById(R.id.score)).setText(currentBoard.setScore(1));

                        Animation shake = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                        button.startAnimation(shake);
                        button.setEnabled(false);
                        selectedButton.setEnabled(false);
                        amountOfSelectedButtons = 0;
                        selectedButtonOne = "";
                        if (currentBoard.getScorePoints().equals("10")) {

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button

                                            Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
                                            mainIntent.putExtra("currentBoard", currentBoard);
                                            startActivity(mainIntent);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                            builder.setMessage("You Win! Return to title screen?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();

                        }
                    } else if (amountOfSelectedButtons == 1 && button.getId() == selectedButton.getId()) {

                    } else {
                        amountOfSelectedButtons = 0;
                        selectedButtonOne = "";

                        button.setPressed(true);
                        selectedButton.setPressed(true);
                        clickable = false;

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                button.setPressed(false);
                                selectedButton.setPressed(false);
                                clickable = true;
                            }
                        }, 200);

                    }
                }
                return true;
            }
        });

    }

    public void restart(View view){

                Intent refresh = new Intent(this, GameActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
    }

    public void shuffle(View view){


        List<String> temp = new ArrayList<String>(currentBoard.getListOfUsedPokemon());
        List<String> all = new ArrayList<String>( currentBoard.getListOfPokemons());

        temp.remove(" ");
        temp.addAll(temp);

       all.removeAll(temp);
        Collections.shuffle(all);
        temp.addAll(all);
       currentBoard.setListOfPokemon(temp);

        Intent mainIntent = new Intent(GameActivity.this, GameActivity.class);

        mainIntent.putExtra("currentBoard", currentBoard);
        startActivityForResult(mainIntent, 111);

    }
    public void previewPokemon(View view) {

        for(int id : BUTTON_IDS) {
            ((Button) findViewById(id)).setPressed(true);

        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int id : BUTTON_IDS) {
                    ((Button) findViewById(id)).setPressed(false);
                }
            }
        }, 2000);
        ( (Button)findViewById(R.id.buttonHint)).setEnabled(false);
        currentBoard.useHint();
        amountOfSelectedButtons = 0;
        selectedButtonOne = "";
    }
    public void populatePokeBalls(){

        for(int i =0; i < currentBoard.getListOfPokemons().size(); i++){
            setPokeballNumber(i + 1, currentBoard.getListOfPokemons().get(i));
        }


    }

    public void setPokeballNumber(int i, String pokemon){
        switch(i){
            case 1:
                populateAllPokeball(button1A, pokemon);
                break;
            case 2:
                populateAllPokeball(button1B, pokemon);
                break;
            case 3: populateAllPokeball(button1C, pokemon);
                break;
            case 4: populateAllPokeball(button1D, pokemon);
                break;
            case 5: populateAllPokeball(button2A, pokemon);
                break;
            case 6: populateAllPokeball(button2B, pokemon);
                break;
            case 7: populateAllPokeball(button2C, pokemon);
                break;
            case 8: populateAllPokeball(button2D, pokemon);
                break;
            case 9: populateAllPokeball(button3A, pokemon);
                break;
            case 10: populateAllPokeball(button3B, pokemon);
                break;
            case 11: populateAllPokeball(button3C, pokemon);
                break;
            case 12: populateAllPokeball(button3D, pokemon);
                break;
            case 13: populateAllPokeball(button4A, pokemon);
                break;
            case 14: populateAllPokeball(button4B, pokemon);
                break;
            case 15: populateAllPokeball(button4C, pokemon);
                break;
            case 16: populateAllPokeball(button4D, pokemon);
                break;
            case 17: populateAllPokeball(button5A, pokemon);
                break;
            case 18: populateAllPokeball(button5B, pokemon);
                break;
            case 19: populateAllPokeball(button5C, pokemon);
                break;
            case 20: populateAllPokeball(button5D, pokemon);
                break;
            default:
                break;
        }
    }
    public void populateAllPokeball(Button buttonPokeball, String pokeMon){

        if(pokeMon.equalsIgnoreCase("pikachu"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlpikachu) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("bullbasaur"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlbulbasaur) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("snorlax"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlsnorlax) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("eevee"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmleevee) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("charmander"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlcharmander) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("dratini"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmldratini) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("abra"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlabra) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("bellsprout"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlbellsprout) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("caterpie"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlcaterpie) ;
            buttonPokeball.setText(pokeMon);
        }
        if(pokeMon.equalsIgnoreCase("mew"))
        {
            buttonPokeball.setBackgroundResource(R.drawable.xmlmew) ;
            buttonPokeball.setText(pokeMon);
        }
    }
    @Override
    public void onBackPressed() {
        //Intent mainIntent = new Intent(this, MainActivity.class);
        //mainIntent.putExtra("currentBoard", currentBoard);
        //startActivity(mainIntent);

        Intent intent=new Intent();
        intent.putExtra("currentBoard", currentBoard);
        setResult(2,intent);

        super.onBackPressed();
    }
}
