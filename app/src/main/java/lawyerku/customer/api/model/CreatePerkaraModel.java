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
            @SerializedName("customer_id") public int customerId;
            @SerializedName("lawyer_id") public int lawyerId;
            @SerializedName("jobskill_id") public int jobskill;
            @SerializedName("number") public String number;
            public String description;
            @SerializedName("gps_latitude") public Double latitude;
            @SerializedName("gps_longitude")public Double longitude;
            @SerializedName("start_date")public String startDate;
            @SerializedName("end_date")public String endDate;
            @SerializedName("updated_at")public String updatedAt;
            @SerializedName("created_at")public String createdAt;
            @SerializedName("status")public String status_projek;

            @Override
            public String toString() {
                return "Data{" +
                        "id=" + id +
                        ", customerId=" + customerId +
                        ", lawyerId=" + lawyerId +
                        ", jobskill=" + jobskill +
                        ", number='" + number + '\'' +
                        ", description='" + description + '\'' +
                        ", latitude=" + latitude +
                        ", longitude=" + longitude +
                        ", startDate='" + startDate + '\'' +
                        ", endDate='" + endDate + '\'' +
                        ", updatedAt='" + updatedAt + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", status_projek='" + status_projek + '\'' +
                        '}';
            }
        }

    }
}
