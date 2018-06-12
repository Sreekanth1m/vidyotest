package com.medtronic.mcms.connectandroid.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationModel implements Serializable{

    public static class ATTRIBUTE_TAGS {
        public static final String VIDYOTOKEN = "token";
        public static final String NOTIFICATIONTYPE = "notificationType";
        public static final String NOTIFICATIONSTATUS = "notificationStatus";
        public static final String VIDYOMEETINGROOM = "meetingRoom";
        public static final String CLINICIANFIRSTNAME = "clinicianFirstName";
        public static final String CLINICIANLASTNAME = "clinicianLastName";
        public static final String CLINICIANPROFESSION = "clinicianProfession";
        public static final String NOTIFICATIONTEXT = "notificationText";
        public static final String NOTIFICATIONTITLE = "notificationTitle";
    }

    @SerializedName("token")
    private String vidyoToken;

    @SerializedName("notificationType")
    private String notificationType;

    @SerializedName("notificationStatus")
    private String notificationStatus;

    @SerializedName("meetingRoom")
    private String vidyoMeetingRoom;

    @SerializedName("clinicianFirstName")
    private String clinicianFirstName;

    @SerializedName("clinicianLastName")
    private String clinicianLastName;

    @SerializedName("clinicianProfession")
    private String clinicianProfession;

    @SerializedName("notificationText")
    private String notificationText;

    @SerializedName("notificationTitle")
    private String notificationTitle;

//    @SerializedName("patientFirstName")
//    private String patientFirstName;

    public String getVidyoToken() {
        return vidyoToken;
    }

    public void setVidyoToken(String vidyoToken) {
        this.vidyoToken = vidyoToken;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
    public String getVidyoMeetingRoom() {
        return vidyoMeetingRoom;
    }

    public void setVidyoMeetingRoom(String vidyoMeetingRoom) {
        this.vidyoMeetingRoom = vidyoMeetingRoom;
    }

    public String getClinicianFirstName() {
        return clinicianFirstName;
    }

    public void setClinicianFirstName(String clinicianFirstName) {
        this.clinicianFirstName = clinicianFirstName;
    }

    public String getClinicianLastName() {
        return clinicianLastName;
    }

    public void setClinicianLastName(String clinicianLastName) {
        this.clinicianLastName = clinicianLastName;
    }

    public String getClinicianProfession() {
        return clinicianProfession;
    }

    public void setClinicianProfession(String clinicianProfession) {
        this.clinicianProfession = clinicianProfession;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

//    public String getPatientFirstName() {
//        return patientFirstName;
//    }
//
//    public void setPatientFirstName(String patientFirstName) {
//        this.patientFirstName = patientFirstName;
//    }
    public static NotificationModel fromJSON(String jsonString) {
        NotificationModel toReturn = new NotificationModel();

        if (jsonString != null) {
            try {
                JSONObject json = new JSONObject(jsonString);
                if (json.has(ATTRIBUTE_TAGS.VIDYOTOKEN)) {
                    toReturn.setVidyoToken(json.getString(ATTRIBUTE_TAGS.VIDYOTOKEN));
                }

                if (json.has(ATTRIBUTE_TAGS.VIDYOMEETINGROOM)) {
                    toReturn.setVidyoMeetingRoom(json.getString(ATTRIBUTE_TAGS.VIDYOMEETINGROOM));
                }

                if (json.has(ATTRIBUTE_TAGS.CLINICIANFIRSTNAME)) {
                    toReturn.setClinicianFirstName(json.getString(ATTRIBUTE_TAGS.CLINICIANFIRSTNAME));
                }

                if (json.has(ATTRIBUTE_TAGS.CLINICIANLASTNAME)) {
                    toReturn.setClinicianLastName(json.getString(ATTRIBUTE_TAGS.CLINICIANLASTNAME));
                }

                if (json.has(ATTRIBUTE_TAGS.CLINICIANPROFESSION)) {
                    toReturn.setClinicianProfession(json.getString(ATTRIBUTE_TAGS.CLINICIANPROFESSION));
                }

                if (json.has(ATTRIBUTE_TAGS.NOTIFICATIONTYPE)) {
                    toReturn.setNotificationType(json.getString(ATTRIBUTE_TAGS.NOTIFICATIONTYPE));
                }

                if (json.has(ATTRIBUTE_TAGS.NOTIFICATIONSTATUS)) {
                    toReturn.setNotificationStatus(json.getString(ATTRIBUTE_TAGS.NOTIFICATIONSTATUS));
                }

                if (json.has(ATTRIBUTE_TAGS.NOTIFICATIONTEXT)) {
                    toReturn.setNotificationText(json.getString(ATTRIBUTE_TAGS.NOTIFICATIONTEXT));
                }

                if (json.has(ATTRIBUTE_TAGS.NOTIFICATIONTITLE)) {
                    toReturn.setNotificationTitle(json.getString(ATTRIBUTE_TAGS.NOTIFICATIONTITLE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return toReturn;
    }


    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ATTRIBUTE_TAGS.VIDYOTOKEN, getVidyoToken());
            jsonObject.put(ATTRIBUTE_TAGS.NOTIFICATIONTYPE, getNotificationType());
            jsonObject.put(ATTRIBUTE_TAGS.NOTIFICATIONSTATUS, getNotificationStatus());
            jsonObject.put(ATTRIBUTE_TAGS.VIDYOMEETINGROOM, getVidyoMeetingRoom());
            jsonObject.put(ATTRIBUTE_TAGS.CLINICIANFIRSTNAME, getClinicianFirstName());
            jsonObject.put(ATTRIBUTE_TAGS.CLINICIANLASTNAME, getClinicianLastName());
            jsonObject.put(ATTRIBUTE_TAGS.CLINICIANPROFESSION, getClinicianProfession());
            jsonObject.put(ATTRIBUTE_TAGS.NOTIFICATIONTEXT, getNotificationText());
            jsonObject.put(ATTRIBUTE_TAGS.NOTIFICATIONTITLE, getNotificationTitle());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    }






