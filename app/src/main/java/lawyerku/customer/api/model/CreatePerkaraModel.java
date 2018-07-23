package lawyerku.customer.api.model;

import com.google.gson.annotations.SerializedName;

public class CreatePerkaraModel {

    public static class Request {
        public String description;
        @SerializedName("jobskill_id") public int skillId;
        @SerializedName("languageskill_id") public int languagesId;
        @SerializedName("gps_latitude") public Double latitude;
        @SerializedName("gps_longitude") public Double longitude;
    }

    public static class Response {
        public int status;
        public String message;
        public Data data;

        public static class Data {
            public int id;
            @SerializedName("customer_id") public int customer_id;
            @SerializedName("lawyer_id") public int lawyer_id;
            @SerializedName("jobskill_id") public int jobskill_id;
            @SerializedName("number") public String number;
            public String description;
            @SerializedName("gps_latitude") public Double gps_latitude;
            @SerializedName("gps_longitude")public Double gps_longitud;
            @SerializedName("start_date")public String start_date;
            @SerializedName("end_date")public String end_date;
            @SerializedName("updated_at")public String updated_at;
            @SerializedName("created_at")public String created_at;
            @SerializedName("status")public String status;

            @Override
            public String toString() {
                return "Data{" +
                        "id=" + id +
                        ", customer_id=" + customer_id +
                        ", lawyer_id=" + lawyer_id +
                        ", jobskill_id=" + jobskill_id +
                        ", number='" + number + '\'' +
                        ", description='" + description + '\'' +
                        ", gps_latitude=" + gps_latitude +
                        ", gps_longitud=" + gps_longitud +
                        ", start_date='" + start_date + '\'' +
                        ", end_date='" + end_date + '\'' +
                        ", updated_at='" + updated_at + '\'' +
                        ", created_at='" + created_at + '\'' +
                        ", status='" + status + '\'' +
                        '}';
            }
        }

    }
}
