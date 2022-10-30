package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<DoctorsAppointment> doctorsAppointmentArrayList;
    DoctorsRecyclerAdapter adapter;

    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // work offline
        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorsAppointmentArrayList= new ArrayList<>();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);
            }
        });

        readData();
    }

    private void readData() {

        databaseReference.child("APPOINTMENTS").orderByChild("doctorName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorsAppointmentArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DoctorsAppointment appointments = dataSnapshot.getValue(DoctorsAppointment.class);
                    doctorsAppointmentArrayList.add(appointments);
                }
                adapter = new DoctorsRecyclerAdapter(MainActivity.this, doctorsAppointmentArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_appointment);

            EditText textDName = dialog.findViewById(R.id.textDName);
            EditText textHospital = dialog.findViewById(R.id.textHospital);
            EditText textDate = dialog.findViewById(R.id.textDate);
            EditText textPatientName = dialog.findViewById(R.id.textPatientName);
            EditText textPhoneNumber = dialog.findViewById(R.id.PhoneNumber);
            EditText textNIC= dialog.findViewById(R.id.textNIC);
            EditText textAddress = dialog.findViewById(R.id.textAddress);


            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonAdd.setText("ADD");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "appointment" + new Date().getTime();
                    String Dname = textDName.getText().toString();
                    String Hospital = textHospital.getText().toString();
                    String Date = textDate.getText().toString();
                    String PatientName = textPatientName.getText().toString();
                    String PhoneNumber = textPhoneNumber.getText().toString();
                    String NIC = textNIC.getText().toString();
                    String Address =textAddress.getText().toString();




                    if (Dname.isEmpty() || Hospital.isEmpty() || Date.isEmpty() || PatientName.isEmpty() || PhoneNumber.isEmpty() || NIC.isEmpty() || Address.isEmpty() ) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("APPOINTMENTS").child(id).setValue(new DoctorsAppointment(id, Dname, Hospital, Date, PatientName, PhoneNumber, NIC , Address));
                        Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}