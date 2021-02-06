package com.example.tripreminder.database;

import java.io.Serializable;

public class NoteData implements Serializable {

    String note;

    public String getNote() {   
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    boolean status;
}
