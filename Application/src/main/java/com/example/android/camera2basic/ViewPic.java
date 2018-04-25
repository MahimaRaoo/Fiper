package com.example.android.camera2basic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.List;

public class ViewPic extends AppCompatActivity {

    ImageView iv;
    Canvas canvas;
    Bitmap tempBt;
    Paint rect;
    Bitmap myBitmap;
    List<Landmark> landmarkList;
    String landmarkString = "[";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pic);
        Bundle extr=getIntent().getExtras();
        String path1=extr.getString("path");
        File file=new File(path1);
        if(!file.exists())Toast.makeText(this,"No File",Toast.LENGTH_SHORT).show();
        Bitmap bitmap= BitmapFactory.decodeFile(path1);

        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,1500,1150,true);

        myBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);

        iv=(ImageView) findViewById(R.id.imageView1);
        iv.setImageBitmap(myBitmap);

        rect=new Paint();
        rect.setStrokeWidth(5);
        rect.setColor(Color.WHITE);
        rect.setStyle(Paint.Style.STROKE);

        tempBt=Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(),Bitmap.Config.RGB_565);
        canvas=new Canvas(tempBt);
        canvas.drawBitmap(tempBt,0,0,null);
    }

    public void Next(View view) {
        FaceDetector fd=new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).setLandmarkType(FaceDetector.ALL_LANDMARKS).setMode(FaceDetector.FAST_MODE).build();
        if(!fd.isOperational()){
            Toast.makeText(getApplicationContext(),"Do again",Toast.LENGTH_SHORT).show();
            return ;
        }
        Frame frame=new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> sparseArray=fd.detect(frame);

        //Toast.makeText(getApplicationContext(),"Faces = "+sparseArray.size(),Toast.LENGTH_SHORT).show();

        for(int i=0;i<sparseArray.size();i++){

            Face face=sparseArray.valueAt(i);
            float x1=face.getPosition().x;
            float y1=face.getPosition().y;
            float x2=x1 + face.getWidth();
            float y2=y1 + face.getHeight();

            landmarkList = face.getLandmarks();
            for(int j=0;j<8;j++){
                Landmark landmark=landmarkList.get(j);
                float x1l=landmark.getPosition().x;

                float y1l=landmark.getPosition().y;
                landmarkString=landmarkString + " (" + Float.toString(x1l) + "," + Float.toString(y1l) +") ";
                canvas.drawPoint(x1l,y1l,rect);
            }

            RectF rectf=new RectF(x1,y1,x2,y2);
            canvas.drawRoundRect(rectf,2,2,rect);
        }
//        Toast.makeText(getApplicationContext(),"Kevin",Toast.LENGTH_SHORT).show();
        iv.setImageDrawable(new BitmapDrawable(getResources(),tempBt));
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://fiper-db.firebaseio.com/");
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");
        landmarkString=landmarkString + "]";
        Intent i = new Intent(this,PersonalityForm.class);
        i.putExtra("Landmarks",landmarkString);
        startActivity(i);
    }

    public void detectFace(View view){
        FaceDetector fd=new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).setLandmarkType(FaceDetector.ALL_LANDMARKS).setMode(FaceDetector.FAST_MODE).build();
        if(!fd.isOperational()){
            Toast.makeText(getApplicationContext(),"Do again",Toast.LENGTH_SHORT).show();
            return ;
        }
        Frame frame=new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> sparseArray=fd.detect(frame);

        //Toast.makeText(getApplicationContext(),"Faces = "+sparseArray.size(),Toast.LENGTH_SHORT).show();

        for(int i=0;i<sparseArray.size();i++){

            Face face=sparseArray.valueAt(i);
            float x1=face.getPosition().x;
            float y1=face.getPosition().y;
            float x2=x1 + face.getWidth();
            float y2=y1 + face.getHeight();

            landmarkList = face.getLandmarks();
            for(int j=0;j<8;j++){
                Landmark landmark=landmarkList.get(j);
                float x1l=landmark.getPosition().x;

                float y1l=landmark.getPosition().y;
                landmarkString=landmarkString + " (" + Float.toString(x1l) + "," + Float.toString(y1l) +") ";
                canvas.drawPoint(x1l,y1l,rect);
            }

            RectF rectf=new RectF(x1,y1,x2,y2);
            canvas.drawRoundRect(rectf,2,2,rect);
        }
//        Toast.makeText(getApplicationContext(),"Kevin",Toast.LENGTH_SHORT).show();
        iv.setImageDrawable(new BitmapDrawable(getResources(),tempBt));
    }
}
