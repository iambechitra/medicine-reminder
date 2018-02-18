package com.example.bechitra.medicinereminder;

/**
 * Created by bechitra on 2/14/18.
 */

public class MedicineInformation {
    String nameOfMedicine, description, time;

    public MedicineInformation(String nameOfMedicine, String description, String time) {
        this.nameOfMedicine = nameOfMedicine;
        this.description = description;
        this.time = time;
    }

    public void setNameOfMedicine(String nameOfMedicine) {
        this.nameOfMedicine = nameOfMedicine;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}
