package client.marpolex.com.justorder_android.Models;

import com.orm.SugarRecord;

public class User extends SugarRecord {
   private String name;
   private String surname;
   private int expPoints;
   private String birthDate;
   private int gender;
   private String token;

    public User() {
    }

    public User(String name, String surname, int expPoints, int gender, String birthDate, String token) {
        this.name = name;
        this.surname = surname;
        this.expPoints = expPoints;
        this.gender = gender;
        this.birthDate = birthDate;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getExpPoints() {
        return expPoints;
    }

    public void setExpPoints(int expPoints) {
        this.expPoints = expPoints;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
