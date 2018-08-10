package lawyerku.customer.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageModel {
    public static class Response{
        public int status;
        public List<Data> data;
        public String message;

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", data=" + data +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static class Data {
        public int id;
        public int sender_id;
        public int receiver_id;
        public int project_id;
        public String date;
        public boolean is_approved;
        public String body;
        public String sender;
        public String receiver;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", sender_id=" + sender_id +
                    ", receiver_id=" + receiver_id +
                    ", project_id=" + project_id +
                    ", date='" + date + '\'' +
                    ", is_approved=" + is_approved +
                    ", body='" + body + '\'' +
                    ", sender='" + sender + '\'' +
                    ", receiver='" + receiver + '\'' +
                    '}';
        }
    }

    public static class User {
        public int id;
        public String name;
        public String first_name;
        public String last_name;
        public String address_1;
        public String address_2;
        @SerializedName("gps_latitude") public String latitude;
        @SerializedName("gps_longitude") public String longitude;
        @SerializedName("cellphone_number_1") public String cellphone1;
        @SerializedName("cellphone_number_2") public String cellphone2;
        public int level;
        @SerializedName("have_smartphone") public boolean smartphone;
        @SerializedName("rate_min") public String rateMin;
        @SerializedName("rate_max") public String rateMax;
        @SerializedName("id_number") public String idnumber;
        public double rating;
        public String comment;
        public double distance;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", address_1='" + address_1 + '\'' +
                    ", address_2='" + address_2 + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", cellphone1='" + cellphone1 + '\'' +
                    ", cellphone2='" + cellphone2 + '\'' +
                    ", level=" + level +
                    ", smartphone=" + smartphone +
                    ", rateMin='" + rateMin + '\'' +
                    ", rateMax='" + rateMax + '\'' +
                    ", idnumber='" + idnumber + '\'' +
                    ", rating=" + rating +
                    ", comment='" + comment + '\'' +
                    ", distance=" + distance +
                    '}';
        }
    }

    public static class MessageToSend {
        public int receiver_id;
        public int project_id;
        public String date;
        public String body;

        @Override
        public String toString() {
            return "MessageToSend{" +
                    "receiver_id=" + receiver_id +
                    ", sender_id=" + project_id +
                    ", date='" + date + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }
}
