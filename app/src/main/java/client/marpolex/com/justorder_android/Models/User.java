package client.marpolex.com.justorder_android.Models;

import com.orm.SugarRecord;

/**
 * Created by mario on 28/03/2018.
 */

public class User extends SugarRecord<User> {
    String name;
    String surname;
    int expPoints;
    int gender;
    int age;
    String token;

    public User() {
    }

    public User(String name, String surname, int expPoints, int gender, int age, String token) {
        this.name = name;
        this.surname = surname;
        this.expPoints = expPoints;
        this.gender = gender;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
