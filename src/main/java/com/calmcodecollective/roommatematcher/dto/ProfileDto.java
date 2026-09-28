package com.calmcodecollective.roommatematcher.dto;

public class ProfileDto {
    private Long id;
    private String bio;
    private String cleanlinessPref;
    private String sleepSchedule;
    private String smokingPref;
    private String drinkingPref;
    private String petPref;
    private Long accountId;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
