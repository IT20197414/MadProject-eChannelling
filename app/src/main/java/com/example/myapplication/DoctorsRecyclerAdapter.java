package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class DoctorsRecyclerAdapter extends RecyclerView.Adapter<DoctorsRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<DoctorsAppointment> doctorsAppointmentArrayList;
    DatabaseReference databaseReference;

    public DoctorsRecyclerAdapter(Context context, ArrayList<DoctorsAppointment> doctorsAppointmentArrayList) {
        this.context = context;
        this.doctorsAppointmentArrayList = doctorsAppointmentArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.doctor_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DoctorsAppointment appointments = doctorsAppointmentArrayList.get(position);

        holder.textDName.setText("Doctor Name : " + appointments.getDoctorName());
        holder.textHospital.setText("Hospital : " + appointments.getHospital());
        holder.textDate.setText("Date : " + appointments.getDate());
        holder.textPatientName.setText("Patient Name : " + appointments.getPatientName());
        holder.textPhoneNumber.setText("Phone Number : " + appointments.getPhoneNumber());
        holder.textNIC.setText("NIC : " + appointments.getNIC());
        holder.textAddress.setText("Address : " + appointments.getAddress());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, appointments.getAppointmentID(), appointments.getDoctorName(), appointments.getHospital(), appointments.getDate(), appointments.getPatientName(), appointments.getPhoneNumber(), appointments.getNIC(), appointments.getAddress());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, appointments.getAppointmentID());
            }
        });

    }

    @Override
    public int getItemCount() {

        return doctorsAppointmentArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textDName;
        TextView textHospital;
        TextView textDate;
        TextView textPatientName;
        TextView textPhoneNumber;
        TextView textNIC;
        TextView textAddress;

        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           /* textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textCountry = itemView.findViewById(R.id.textCountry);*/

            textDName = itemView.findViewById(R.id.textDName);
            textHospital = itemView.findViewById(R.id.textHospital);
            textDate = itemView.findViewById(R.id.textDate);
            textPatientName = itemView.findViewById(R.id.textPatientName);
            textPhoneNumber = itemView.findViewById(R.id.PhoneNumber);
            textNIC = itemView.findViewById(R.id.textNIC);
            textAddress = itemView.findViewById(R.id.textAddress);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String dname, String hospital , String date, String patientname , String phonenumber , String nic , String address) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_appointment);

            /*EditText textName = dialog.findViewById(R.id.textName);
            EditText textEmail = dialog.findViewById(R.id.textEmail);
            EditText textCountry = dialog.findViewById(R.id.textCountry);*/

            EditText textDName = dialog.findViewById(R.id.textDName);
            EditText textHospital = dialog.findViewById(R.id.textHospital);
            EditText textDate = dialog.findViewById(R.id.textDate);
            EditText textPatientName = dialog.findViewById(R.id.textPatientName);
            EditText textPhoneNumber = dialog.findViewById(R.id.PhoneNumber);
            EditText textNIC = dialog.findViewById(R.id.textNIC);
            EditText textAddress = dialog.findViewById(R.id.textAddress);
            textHospital.setText(hospital);

            textDName.setText(dname);
            textDate.setText(date);
            textPatientName.setText(patientname);
            textPhoneNumber.setText(phonenumber);
            textNIC.setText(nic);
            textAddress.setText(address);



            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newDName = textDName.getText().toString();
                    String newHospital = textHospital.getText().toString();
                    String newDate = textDate.getText().toString();
                    String newPatientName = textPatientName.getText().toString();
                    String newPhoneNumber = textPhoneNumber.getText().toString();
                    String newNIC = textNIC.getText().toString();
                    String newAddress = textAddress.getText().toString();

                    if (dname.isEmpty() || hospital.isEmpty() || date.isEmpty() || patientname.isEmpty() || phonenumber.isEmpty() || nic.isEmpty() || address.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newDName.equals(dname) && newHospital.equals(hospital) && newDate.equals(date) && newPatientName.equals(patientname) && newPhoneNumber.equals(phonenumber) && newNIC.equals(nic) &&  newAddress.equals(address)) {
                            Toast.makeText(context, "you didn't change anything", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("APPOINTMENTS").child(id).setValue(new DoctorsAppointment(id, newDName, newHospital, newDate, newPatientName , newPhoneNumber, newNIC, newAddress));
                            Toast.makeText(context, "Appointment Updated successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }


    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseReference.child("APPOINTMENTS").child(id).removeValue();
                    Toast.makeText(context, "Appointment Deleted successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }

}
