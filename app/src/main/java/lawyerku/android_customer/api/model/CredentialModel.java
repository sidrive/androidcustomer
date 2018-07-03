package lawyerku.android_customer.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CredentialModel {

    public static class Request {

        public String name;
        @SerializedName("cellphone_number_1") public String cellphone1;
        @SerializedName("cellphone_number_2") public String cellphone2;
        @SerializedName("id_number") public String nik;

        // ====== Request for Register Customer
        public String address;
        @SerializedName("phone_number") public String phoneNumber;
        public String email;

        // ====== Request for Register Workman
        public int level;
        @SerializedName("have_smartphone") public boolean haveSmartphone;
        @SerializedName("address_1") public String address1;
        @SerializedName("address_2") public String address2;
        @SerializedName("gps_latitude") public Double latitude;
        @SerializedName("gps_longitude") public Double longitude;
        @SerializedName("rate_min") public int rateMin;
        @SerializedName("rate_max") public int rateMax;
        public List<Integer> languages;
        public List<Integer> jobs;

        // ====== Request for Login
        public String username;
        public String password;
    }

    public static class RegistrationResponse {
        public int status;
        public Error message;
        public Request data;
    }

    public static class LoginResponse {
        // Response for Login
        public int status;
        public String accessToken;
        public String message;
        public String userType;
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
