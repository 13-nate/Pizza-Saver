package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.mineseeker.R;

import java.util.regex.Pattern;

import static android.text.Html.fromHtml;

public class HelpMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
        getSupportActionBar().setTitle("HELP");

        TextView txtBomb = findViewById(R.id.txtBombImage);
        txtBomb.setMovementMethod(LinkMovementMethod.getInstance());
       /* TextView txtBomb = findViewById(R.id.txtBombImage);
        txtBomb.setMovementMethod(LinkMovementMethod.getInstance());
        String bombImageLink = "<a href='https://www.cleanpng.com/png-tsar-bomba-cartoon-weapon-gray-cartoon-bombs-204186/'> " +
                "Bomb Image </a>";
        txtBomb.setText(Html.fromHtml("<h2>Bomb Image</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT));
        txtBomb.setText(fromHtml(bombImageLink, Html.FROM_HTML_MODE_COMPACT));
*/
        TextView txtWinSound = findViewById(R.id.txtWinSound);
        String winSoundLink = "<a href='https://freesound.org/people/rhodesmas/sounds/320775/'> " +
                "Win Sound</a>";
        txtWinSound.setText(fromHtml(winSoundLink, Html.FROM_HTML_MODE_COMPACT));


        TextView txtBombFoundSound = findViewById(R.id.txtBombFoundSound);
        String BombFoundSoundLink = "<a href='https://freesound.org/people/MATTIX/sounds/441497/'> " +
                "Bomb Sound</a>";
        txtBombFoundSound.setText(fromHtml(winSoundLink, Html.FROM_HTML_MODE_COMPACT));

        TextView TxtScanSound = findViewById(R.id.txtScanSound);
        String scanSoundLink = "<a href='https://freesound.org/people/samsara/sounds/49989/'> " +
                "Scan Sound</a>";
        TxtScanSound.setText(fromHtml(winSoundLink, Html.FROM_HTML_MODE_COMPACT));

    }
    public static Intent makeIntentHelpActivity(Context context){
        return new Intent(context, HelpMenuActivity.class);
    }
}
