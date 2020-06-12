package com.example.chat;

public class message {
    String id;
    String rsala ;
    String date ;

    public message() {
    }

    public message(String id, String rsala,String date) {
        this.id = id;
        this.rsala = rsala;
        this.date=date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRsala() {
        return rsala;
    }

    public void setRsala(String rsala) {
        this.rsala = rsala;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
