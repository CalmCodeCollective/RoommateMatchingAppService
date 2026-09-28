package com.calmcodecollective.roommatematcher.model.roommate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.Objects;

@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    private String bio;
    private String cleanlinessPref;
    private String sleepSchedule;
    private String smokingPref;
    private String drinkingPref;
    private String petPref;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCleanlinessPref() {
        return cleanlinessPref;
    }

    public void setCleanlinessPref(String cleanlinessPref) {
        this.cleanlinessPref = cleanlinessPref;
    }

    public String getSleepSchedule() {
        return sleepSchedule;
    }

    public void setSleepSchedule(String sleepSchedule) {
        this.sleepSchedule = sleepSchedule;
    }

    public String getSmokingPref() {
        return smokingPref;
    }

    public void setSmokingPref(String smokingPref) {
        this.smokingPref = smokingPref;
    }

    public String getDrinkingPref() {
        return drinkingPref;
    }

    public void setDrinkingPref(String drinkingPref) {
        this.drinkingPref = drinkingPref;
    }

    public String getPetPref() {
        return petPref;
    }

    public void setPetPref(String petPref) {
        this.petPref = petPref;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Profile profile)) return false;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
