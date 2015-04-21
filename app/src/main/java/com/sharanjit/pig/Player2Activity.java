package com.sharanjit.pig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Player2Activity extends ActionBarActivity {
    private FrameLayout dice1, dice2;
    private Button roll;
    private Button hold;

    private TextView turnview, scoreview, p1scoret, p2scoret, p1tscoret, p2tscoret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2);

        roll = (Button) findViewById(R.id.rollbutton);
        hold = (Button) findViewById(R.id.holdbutton);
        dice1 = (FrameLayout) findViewById(R.id.die1);
        dice2 = (FrameLayout) findViewById(R.id.die2);

        turnview = (TextView) findViewById(R.id.pturntext);
        scoreview = (TextView) findViewById(R.id.proundtext);

        p1scoret = (TextView) findViewById(R.id.p1score);
        p2scoret = (TextView) findViewById(R.id.p2score);
        p1tscoret = (TextView) findViewById(R.id.p1tscore);
        p2tscoret = (TextView) findViewById(R.id.p2tscore);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdDice();
            }
        });

        //turnview.setText("Player 1's Turn:");
        p1scoret.setText(ScoreBoard.p1score + " ");
        p1tscoret.setText("");

        p2scoret.setText(ScoreBoard.p2score + " ");
        p2tscoret.setText("(" + ScoreBoard.p2score + ")");

        scoreview.setText("Round " + ScoreBoard.round + " Score: 0");

    }


    //get two random ints between 1 and 6 inclusive
    public void holdDice() {
        if ( ScoreBoard.winner == 0 ) {
            int cscore = 0;
            int totscore = 0;

            if (ScoreBoard.turn == 1) {
                cscore = ScoreBoard.p1score;
            } else {
                cscore = ScoreBoard.p2score;
            }

            totscore = cscore + ScoreBoard.rollscore;

            ScoreBoard.p2score = totscore;
            //p2tscoret.setText("");
            //p1tscoret.setText("(" + ScoreBoard.p1score + ")");
            ScoreBoard.turn = 1;
            ScoreBoard.round++;
            /*if (ScoreBoard.turn == 1) {
                ScoreBoard.p1score = totscore;
                p1tscoret.setText("");
                p2tscoret.setText("(" + ScoreBoard.p2score + ")");
                ScoreBoard.turn = 2;
            } else {
                ScoreBoard.p2score = totscore;
                p2tscoret.setText("");
                p1tscoret.setText("(" + ScoreBoard.p1score + ")");
                ScoreBoard.turn = 1;
                ScoreBoard.round++;
            }*/

            ScoreBoard.rollscore = 0;

            isWinner();

            resetview();
        }
    }

    public void isWinner() {
        if (ScoreBoard.p1score >= ScoreBoard.winscore) {
            turnview.setText("Player 1 Wins");
            ScoreBoard.winmsg = "Player 1 Wins";
            ScoreBoard.winner = 1;
        } else if (ScoreBoard.p2score >= ScoreBoard.winscore) {
            turnview.setText("Player 2 Wins");
            ScoreBoard.winmsg = "Player 2 Wins";
            ScoreBoard.winner = 1;
        }
    }

    public void isWinnerRoll() {
        if (ScoreBoard.turn == 1) {
            if ( (ScoreBoard.p1score + ScoreBoard.rollscore) >= ScoreBoard.winscore) {
                turnview.setText("Player 1 Wins");
                ScoreBoard.winmsg = "Player 1 Wins";
                ScoreBoard.winner = 1;
            }
        } else {
            if ( (ScoreBoard.p2score + ScoreBoard.rollscore) >= ScoreBoard.winscore) {
                turnview.setText("Player 2 Wins");
                ScoreBoard.winmsg = "Player 2 Wins";
                ScoreBoard.winner = 1;
            }
        }
    }

    public void resetview() {
        if ( ScoreBoard.winner == 0 ) {
            /*if (ScoreBoard.turn == 1) {
                turnview.setText("Player 1's Turn:");
            } else {
                turnview.setText("Player 2's Turn:");
            }*/

            p1scoret.setText(ScoreBoard.p1score + " ");
            p2scoret.setText(ScoreBoard.p2score + " ");
            scoreview.setText("Round " + ScoreBoard.round + " Score: " + ScoreBoard.rollscore);
        } else {
            /*Toast t1 = Toast.makeText(Player2Activity.this, ScoreBoard.winmsg, Toast.LENGTH_LONG);
            t1.setGravity(Gravity.CENTER, 0, 0);
            t1.show();
            t1 = null;*/


            AlertDialog alertDialog = new AlertDialog.Builder(Player2Activity.this).create();
            alertDialog.setTitle("You Won!");
            alertDialog.setMessage(ScoreBoard.winmsg);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();        }

        if ( ScoreBoard.turn == 1 ) {
            finish();
        }
    }

    //get two random ints between 1 and 6 inclusive
    public void rollDice() {
        if ( ScoreBoard.winner == 0 ) {
            int val1 = 1 + (int) (6 * Math.random());
            int val2 = 1 + (int) (6 * Math.random());

            int d1value = 0;
            int d2value = 0;

            d1value = setDice(1, val1, dice1);
            d2value = setDice(2, val2, dice2);

            if (d1value == 1 || d2value == 1) {
//Dice shows 1, set round score to 0, call change turn
                ScoreBoard.rollscore = 0;
                holdDice();
            } else {
//Add total to round score, show value to user.
                ScoreBoard.rollscore = ScoreBoard.rollscore + d1value + d2value;
                int ptotal = 0;

                ptotal = ScoreBoard.p2score + ScoreBoard.rollscore;
                p2tscoret.setText("(" + ptotal + ")");
                /*if (ScoreBoard.turn == 1) {
                    ptotal = ScoreBoard.p1score + ScoreBoard.rollscore;
                    p1tscoret.setText("(" + ptotal + ")");
                } else {
                    ptotal = ScoreBoard.p2score + ScoreBoard.rollscore;
                    p2tscoret.setText("(" + ptotal + ")");
                }*/

                isWinnerRoll();
                resetview();
            }
        }

    }

    // set the appropriate picture for each die per int
    public int setDice(int dice, int value, FrameLayout layout) {
        Drawable pic = null;
        //Log.d("soni","Value: "+value);
        //dice1.setBackground( getApplicationContext().getResources().getDrawable(R.drawable.die_face_6) );
        int dvalue = 0;

        switch(value) {
            case 1:
                dvalue = 1;
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                dvalue = 2;
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                dvalue = 3;
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                dvalue = 4;
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                dvalue = 5;
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                dvalue = 6;
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }

        if ( dice == 1 ) {
            dice1.setBackground( pic );
        } else {
            dice2.setBackground( pic );
        }

        return dvalue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player2, menu);
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
}
