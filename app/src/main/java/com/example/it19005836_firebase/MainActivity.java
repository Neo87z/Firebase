package com.example.it19005836_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText txt1,txt2,txt3,txt4;
    Button b1,b2,b3,b4;
    DatabaseReference dbref;
    Student std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1=findViewById(R.id.textID);
        txt2=findViewById(R.id.textName);
        txt3=findViewById(R.id.textAddress);
        txt4=findViewById(R.id.textConatc);
        b1=findViewById(R.id.Savebutton);
        b2=findViewById(R.id.ShowButton);
        b3=findViewById(R.id.UpdateButton);
        b4=findViewById(R.id.DeleteButton);
        std= new Student();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveFuntion();

            }
        });
        DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txt1.setText(dataSnapshot.child("id").getValue().toString());
                    txt2.setText(dataSnapshot.child("name").getValue().toString());
                    txt3.setText(dataSnapshot.child("address").getValue().toString());
                    txt4.setText(dataSnapshot.child("conNo").getValue().toString());
                }
                else{
                    Toast.makeText(MainActivity.this, "No Source to Display", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseReference upRef=FirebaseDatabase.getInstance().getReference().child("Student");
               upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(dataSnapshot.hasChild("Std1")){
                           try{
                               std.setId(txt1.getText().toString().trim());
                               std.setName(txt2.getText().toString().trim());
                               std.setAddress(txt3.getText().toString().trim());
                               std.setConNo(Integer.parseInt(txt4.getText().toString().trim()));
                               dbref=FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                               dbref.setValue(std);
                               clearControls();
                               Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                           }catch(NumberFormatException e){
                               Toast.makeText(MainActivity.this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();

                           }
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }
       });
    b4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dlete();
        }
    });
    }

    private void clearControls(){
        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
        txt4.setText("");

    }

    public void SaveFuntion(){
        dbref= FirebaseDatabase.getInstance().getReference().child(("Student"));
        try{
            if(TextUtils.isEmpty(txt1.getText().toString())){
                Toast.makeText(this, "Please Enter ID", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(txt2.getText().toString())){
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(txt3.getText().toString())){
                Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show();

            }
            else{
                std.setId(txt1.getText().toString());
                std.setName(txt2.getText().toString());
                std.setAddress(txt3.getText().toString());
                std.setConNo(Integer.parseInt(txt4.getText().toString()));

                //dbref.push().setValue(std);
                dbref.child("Std1").setValue(std);
                Toast.makeText(this, "Data Saved Sucessfuly", Toast.LENGTH_SHORT).show();
                clearControls();

            }

        }
        catch (NumberFormatException e){
            Toast.makeText(this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
        }
    }

public void Dlete(){
        DatabaseReference delfef= FirebaseDatabase.getInstance().getReference().child("Student");
    delfef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.hasChild("Std1")){
                dbref=FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                dbref.removeValue();
                clearControls();
                Toast.makeText(MainActivity.this, "Data Deleted Sucessfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "No Souce TO Delete", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}
}
