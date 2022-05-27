package com.blogspot.softlabsja.iiucstudentapp.About;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AboutActivity extends AppCompatActivity {

    RelativeLayout myEmail;
    ImageView backBtn;
    TextView A_section,A_semester,A_department,A_slog;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        myEmail = findViewById(R.id.myEmail);
        backBtn = findViewById(R.id.backBtnA);
        A_section = findViewById(R.id.A_section);
        A_semester = findViewById(R.id.A_semester);
        A_department = findViewById(R.id.A_department);
        A_slog = findViewById(R.id.A_slog);



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("about");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        email = (String) dataSnapshot.child("email").getValue();
                        A_section.setText((String) dataSnapshot.child("section").getValue());
                        A_semester.setText((String) dataSnapshot.child("semester").getValue());
                        A_department.setText((String) dataSnapshot.child("department").getValue());
                        A_slog.setText((String) dataSnapshot.child("slog").getValue());
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        myEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email", email);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(AboutActivity.this, "Already copy the E-mail", Toast.LENGTH_SHORT).show();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}