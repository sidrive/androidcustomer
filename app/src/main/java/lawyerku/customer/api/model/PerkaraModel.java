package lawyerku.customer.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PerkaraModel {

    public static class Response {
        public int status;
        public String message;
        public List<Data> data;

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
            public Customer customer;
            public Lawyer lawyer;
            public Jobskill jobskill;
            public Status last_status;
            public Rating rating;

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
                        ", customer=" + customer +
                        ", lawyer=" + lawyer +
                        ", jobskill=" + jobskill +
                        ", last_status=" + last_status +
                        ", rating=" + rating +
                        '}';
            }
        }

    }

    public static class Customer {
        public int id;
        public String name;
        @SerializedName("address") public String customerAddress;
        @SerializedName("phone_number_1") public String customerPhone;
        @SerializedName("id_number") public String customerId;
        public int user_id;

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", customerAddress='" + customerAddress + '\'' +
                    ", customerPhone='" + customerPhone + '\'' +
                    ", customerId='" + customerId + '\'' +
                    ", user_id=" + user_id +
                    '}';
        }
    }

    public static class Lawyer {
         public int id;
         @SerializedName("name") public String lawyername;
         @SerializedName("address_1") public String lawyerAddress1;
         @SerializedName("address_2") public String lawyerAddress2;
         @SerializedName("gps_latitude") public double latitude;
         @SerializedName("gps_longitude") public double longitude;
         @SerializedName("cellphone_number_1") public String lawyerPhone1;
         @SerializedName("cellphone_number_2") public String lawyerPhone2;
         public int level;
         @SerializedName("have_smartphone") public boolean smartphone;
         @SerializedName("rate_min") public double rateMin;
         @SerializedName("rate_max") public double rateMax;
         @SerializedName("id_number") public String lawyerId;
         public int user_id;

        @Override
        public String toString() {
            return "Lawyer{" +
                    "id=" + id +
                    ", lawyername='" + lawyername + '\'' +
                    ", lawyerAddress1='" + lawyerAddress1 + '\'' +
                    ", lawyerAddress2='" + lawyerAddress2 + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", lawyerPhone1='" + lawyerPhone1 + '\'' +
                    ", lawyerPhone2='" + lawyerPhone2 + '\'' +
                    ", level=" + level +
                    ", smartphone=" + smartphone +
                    ", rateMin=" + rateMin +
                    ", rateMax=" + rateMax +
                    ", lawyerId='" + lawyerId + '\'' +
                    ", user_id=" + user_id +
                    '}';
        }
    }

    public static class Jobskill {
        public int id;
        public String name;

        @Override
        public String toString() {
            return "Jobskill{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Status {
        public int id;
        public String status;

        @Override
        public String toString() {
            return "Status{" +
                    "id=" + id +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    public static class Rating {
        public int id;
        public int project_id;
        public String comment;
        public float rating;

        @Override
        public String toString() {
            return "Rating{" +
                    "id=" + id +
                    ", project_id=" + project_id +
                    ", comment='" + comment + '\'' +
                    ", rating=" + rating +
                    '}';
        }
    }

    public static class Purchase {
        @SerializedName("project_id") public int id;
        public String status;
        @SerializedName("total_transfers") public int total;
        public String path;
    }

    public static class purchaseResponse {
        public int status;
        public String message;
        public List<Payment> data;

        @Override
        public String toString() {
            return "purchaseResponse{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }

        public static class Payment {
            @SerializedName("project_id") public String idProject;
            public String status;
            @SerializedName("customer_id") public int idCustomer;
            @SerializedName("paid_at") public String paidAt;
            @SerializedName("total_transfers") public String total;
            public int id;

            @Override
            public String toString() {
                return "Payment{" +
                        "idProject='" + idProject + '\'' +
                        ", status='" + status + '\'' +
                        ", idCustomer=" + idCustomer +
                        ", paidAt='" + paidAt + '\'' +
                        ", total='" + total + '\'' +
                        ", id=" + id +
                        '}';
            }
        }
    }

    public static class ResponseSetStatus{
        public int status;
        public String message;
        public List<Status> data;

        @Override
        public String toString() {
            return "ResponseSetStatus{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class ResponseRating {
        public int status;
        public String message;
        public Rating data;

        @Override
        public String toString() {
            return "ResponseRating{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
