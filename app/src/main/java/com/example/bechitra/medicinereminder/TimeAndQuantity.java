package com.example.bechitra.medicinereminder;

/**
 * Created by bechitra on 2/21/18.
 */

public class TimeAndQuantity {
    String TIME, QUANTITY;

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public TimeAndQuantity(String TIME, String QUANTITY) {
        this.TIME = TIME;
        this.QUANTITY = QUANTITY;
    }
}
