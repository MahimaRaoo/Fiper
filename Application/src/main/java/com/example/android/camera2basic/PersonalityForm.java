package com.example.android.camera2basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.vision.face.Landmark;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalityForm extends AppCompatActivity {

    String Landmarks;
    SeekBar sbO;
    SeekBar sbC;
    SeekBar sbE;
    SeekBar sbA;
    SeekBar sbN;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_form);
        Bundle extr=getIntent().getExtras();
        Landmarks=extr.getString("Landmarks");
        etName = (EditText) findViewById(R.id.editText);
        sbO = (SeekBar) findViewById(R.id.seekBar2);
        sbC = (SeekBar) findViewById(R.id.seekBar6);
        sbE = (SeekBar) findViewById(R.id.seekBar5);
        sbA = (SeekBar) findViewById(R.id.seekBar);
        sbN = (SeekBar) findViewById(R.id.seekBar4);
    }

    public void done(View view) {
        String O = Integer.toString(sbO.getProgress());
        String C = Integer.toString(sbC.getProgress());
        String E = Integer.toString(sbE.getProgress());
        String A = Integer.toString(sbA.getProgress());
        String N = Integer.toString(sbN.getProgress());
        String Name = String.valueOf(etName.getText());

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fiper-db.firebaseio.com/");
        DatabaseReference myRef = database.getReference(Name);

        myRef.child("Landmarks").setValue(Landmarks);
        myRef.child("O").setValue(O);
        myRef.child("C").setValue(C);
        myRef.child("E").setValue(E);
        myRef.child("A").setValue(A);
        myRef.child("N").setValue(N);

        Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();
    }
}
