
package com.retroapp.set2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resident {

    @SerializedName("community_id")
    @Expose
    private Integer communityId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sugar_id")
    @Expose
    private String sugarId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("do_not_disturb")
    @Expose
    private Boolean doNotDisturb;
    @SerializedName("notify_on_visits")
    @Expose
    private Boolean notifyOnVisits;
    @SerializedName("phone_mobile")
    @Expose
    private String phoneMobile;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("resident_type_id")
    @Expose
    private Integer residentTypeId;
    @SerializedName("resident_status_id")
    @Expose
    private Integer residentStatusId;
    @SerializedName("external_record_id")
    @Expose
    private Object externalRecordId;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("status_c")
    @Expose
    private String statusC;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("display_name")
    @Expose
    private String displayName;

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSugarId() {
        return sugarId;
    }

    public void setSugarId(String sugarId) {
        this.sugarId = sugarId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDoNotDisturb() {
        return doNotDisturb;
    }

    public void setDoNotDisturb(Boolean doNotDisturb) {
        this.doNotDisturb = doNotDisturb;
    }

    public Boolean getNotifyOnVisits() {
        return notifyOnVisits;
    }

    public void setNotifyOnVisits(Boolean notifyOnVisits) {
        this.notifyOnVisits = notifyOnVisits;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getResidentTypeId() {
        return residentTypeId;
    }

    public void setResidentTypeId(Integer residentTypeId) {
        this.residentTypeId = residentTypeId;
    }

    public Integer getResidentStatusId() {
        return residentStatusId;
    }

    public void setResidentStatusId(Integer residentStatusId) {
        this.residentStatusId = residentStatusId;
    }

    public Object getExternalRecordId() {
        return externalRecordId;
    }

    public void setExternalRecordId(Object externalRecordId) {
        this.externalRecordId = externalRecordId;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatusC() {
        return statusC;
    }

    public void setStatusC(String statusC) {
        this.statusC = statusC;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
