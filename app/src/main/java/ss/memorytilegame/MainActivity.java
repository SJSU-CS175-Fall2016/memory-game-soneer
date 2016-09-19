package ss.memorytilegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GameBoard currentBoard ;
    boolean firstrun = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentBoard = new GameBoard();
       // Intent i = getIntent();
        //currentBoard= (GameBoard)i.getSerializableExtra("currentGameBoard");

//        if (savedInstanceState != null) {
//            currentBoard = (GameBoard) getIntent().getSerializableExtra("currentBoard");
//            firstrun = false;
//        }
//        else{
//            currentBoard = new GameBoard();
//            firstrun = true;
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 111) {
            // Make sure the request was successful
            currentBoard = (GameBoard) data.getSerializableExtra("currentBoard");
            firstrun = false;
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
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

    public void startTheGame(View view) {
        if (firstrun) {
            Intent mainIntent = new Intent(MainActivity.this, GameActivity.class);

            mainIntent.putExtra("currentBoard", currentBoard);
            startActivityForResult(mainIntent, 111);
        }
        else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent mainIntent = new Intent(MainActivity.this, GameActivity.class);
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button

                            mainIntent.putExtra("currentBoard", currentBoard);
                            startActivityForResult(mainIntent,111);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            mainIntent.putExtra("currentBoard", new GameBoard());
                            startActivityForResult(mainIntent,111);
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Resume Game(Yes), Restart(No)?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }


    public void rulesController(View view){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                      dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("1. The user is shown 20 tiles. Hidden below these tiles are 10 random images (use your own) each repeated twice. \n" +
                "\n" +
                "2. The user can touch on any two tiles. Touching on a tile will show the image.\n" +
                "\n" +
                "3. If the two tiles have matching images, they disappear (Use a check or something to indicate it's already matched). The user gets a point. \n" +
                "\n" +
                "4. If the two tiles do NOT have matching images, they flip over to being tiles again (flip animation not required).\n" +
                "\n" +
                "5. Hitting the back soft key while playing will take the user back to the first screen (happens automatically). Trying to play should restore the playing activity to the previous state (may need some state handling). That is, if I hit back after matching say 10 tiles, and return to playing, it should show me the remaining 10 cards to match. ").setPositiveButton("Got It", dialogClickListener)
               .show();

    }
}
