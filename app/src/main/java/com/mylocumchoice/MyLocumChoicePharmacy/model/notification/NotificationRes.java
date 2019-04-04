package com.mylocumchoice.MyLocumChoicePharmacy.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationRes {

    private ArrayList<FinalNotification> final_notifications;

    public ArrayList<FinalNotification> getFinalNotifications() { return this.final_notifications; }

    public void setFinalNotifications(ArrayList<FinalNotification> final_notifications) { this.final_notifications = final_notifications; }

    @SerializedName("unseen_notifications")
    @Expose
    private boolean unseen_notifications;

    public boolean isUnseen_notifications() {
        return unseen_notifications;
    }

    public void setUnseen_notifications(boolean unseen_notifications) {
        this.unseen_notifications = unseen_notifications;
    }

    public class Notification
    {

        public int entity_id;

        public int getId() {
            return id;
        }

        public void setEntityId(int id) {
            this.entity_id = entity_id;
        }

        public int getEntityId() {
            return entity_id;
        }

        public void setKey(Object id) {
            this.key = key;
        }

        private Object key;

        public Object getKey() {
            return key;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;

        private String message;

        public String getMessage() { return this.message; }

        public void setMessage(String message) { this.message = message; }

        private String time;

        public String getTime() { return this.time; }

        public void setTime(String time) { this.time = time; }

        private boolean seen;

        public boolean getSeen() { return this.seen; }

        public void setSeen(boolean seen) { this.seen = seen; }

        private boolean read;

        public boolean getRead() { return this.read; }

        public void setRead(boolean read) { this.read = read; }
    }

    public class FinalNotification
    {
        private String date;

        public String getDate() { return this.date; }

        public void setDate(String date) { this.date = date; }

        private ArrayList<Notification> notifications;

        public ArrayList<Notification> getNotifications() { return this.notifications; }

        public void setNotifications(ArrayList<Notification> notifications) { this.notifications = notifications; }
    }
}
