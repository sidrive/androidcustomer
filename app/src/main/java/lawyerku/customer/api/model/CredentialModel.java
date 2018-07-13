package lawyerku.customer.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CredentialModel {

    public static class Request {

        public String name;
//        @SerializedName("cellphone_number_1") public String cellphone1;
//        @SerializedName("cellphone_number_2") public String cellphone2;
        @SerializedName("id_number") public String nik;

        // ====== Request for Register Customer
        public String address;
        @SerializedName("phone_number") public String phoneNumber;
        public String email;

        // ====== Request for Register Workman
//        public int level;
//        @SerializedName("have_smartphone") public boolean haveSmartphone;
//        @SerializedName("address_1") public String address1;
//        @SerializedName("address_2") public String address2;
//        @SerializedName("gps_latitude") public Double latitude;
//        @SerializedName("gps_longitude") public Double longitude;
//        @SerializedName("rate_min") public int rateMin;
//        @SerializedName("rate_max") public int rateMax;
//        public List<Integer> languages;
//        public List<Integer> jobs;

        // ====== Request for Login
        public String username;
        public String password;
        public String c_password;
        public String first_name;
        public String last_name;
        public String role_id;
        public String phone_number_1;
        public String phone_number_2;


        @Override
        public String toString() {
            return "Request{" +
                    "address='" + address + '\'' +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", c_password='" + c_password + '\'' +
                    ", first_name='" + first_name + '\'' +
                    ", last_name='" + last_name + '\'' +
                    ", role_id='" + role_id + '\'' +
                    ", phone_number_1='" + phone_number_1 + '\'' +
                    ", phone_number_2='" + phone_number_2 + '\'' +
                    '}';
        }
    }

    public static class RegistrationResponse {
        public int status;
        public Error message;
        public Request data;
        public Success success;
        public String error;

        @Override
        public String toString() {
            return "RegistrationResponse{" +
                    "status=" + status +
                    ", message=" + message +
                    ", data=" + data +
                    ", success=" + success +
                    ", error='" + error + '\'' +
                    '}';
        }
    }



    public static class Success{
        public String name;
        public String token;

        @Override
        public String toString() {
            return "Success{" +
                    "name='" + name +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    public static class LoginResponse {
        // Response for Login
        public int status;
        public String accessToken;
        public String message;
        public String userType;
        public Success success;
    }

    public static class Error{
        public List<String> id_number;
        public List<String> email;
        public List<String> name;
        public List<String> address;
        public List<String> phone_number;
        public List<String> cellphone_number_1;
        public List<String> cellphone_number_2;
        public List<String> level;

        public List<String> address_1;
        public List<String> address_2;
        public List<String> gps_latitude;
        public List<String> gps_longitude;
        public List<String> rate_min;
        public List<String> rate_max;
        public List<String> languages;
        public List<String> jobs;
        public List<String> username;
        public List<String> password;
    }


}
