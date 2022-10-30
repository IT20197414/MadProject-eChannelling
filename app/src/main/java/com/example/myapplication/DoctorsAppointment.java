package com.example.myapplication;

public class DoctorsAppointment {

    String appointmentID;
    String doctorName;
    String hospital;
    String date;
    String patientName;
    String phoneNumber;
    String NIC;
    String address;


    public DoctorsAppointment() {


    }

    public DoctorsAppointment(String appointmentID, String doctorName, String hospital, String date, String patientName, String phoneNumber, String NIC, String address) {

        this.appointmentID = appointmentID;
        this.doctorName = doctorName;
        this.hospital = hospital;
        this.date = date;
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.NIC = NIC;
        this.address = address;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}











