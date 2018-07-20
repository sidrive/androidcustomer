package lawyerku.customer.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LawyerModel {

    public static class Request {
        @SerializedName("languageskill_id") public int language;
        @SerializedName("jobskill_id") public int skill;
        public String latitude;
        public String longitude;
        public String ignore;

        @SerializedName("workman_id") public int workmanId;
    }

    public static String generateIgnored(List<String> ignoredIds){
        StringBuilder builder = new StringBuilder();
        for(String id : ignoredIds){
            builder.append(id);
            builder.append(",");
        }
        return builder.toString();
    }

    public static class ResponseProfile {
        public String success;
        public List<DataProfile> data;

        @Override
        public String toString() {
            return "ResponseProfile{" +
                    "message='" + success + '\'' +
                    ", status=" + success +
                    '}';
        }
    }

    public static class Response {
        public int status;
        public String message;
        public List<Data> data;

        @Override
        public String toString() {
            return "Response{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class ResponseAddWorkman {
        public int status;
        public String message;
        public Data data;
    }

    public static class DataProfile{
        public int id;
        public String username;
        public String email;
        @SerializedName("role_id") public int roleid;

        @Override
        public String toString() {
            return "DataProfile{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", roleid=" + roleid +
                    '}';
        }
    }

    public static class Data {
        public int id;
        public String name;
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
        public double rating;
        public String comment;
        public double distance;


        public Image images;
        public List<Language> languageskills;
        public List<Skill> jobskills;

        public static class Image {
            @SerializedName("small_url") public String smallUrl;
            @SerializedName("medium_url") public String mediumUrl;
            @SerializedName("large_url") public String largeUrl;
        }

        public static class Language {
            public int id;
            public String name;

            @Override
            public String toString() {
                return "Language{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        public static class Skill {
            public int id;
            public String name;

            @Override
            public String toString() {
                return "Skill{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address_1='" + address_1 + '\'' +
                    ", address_2='" + address_2 + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", cellphone1='" + cellphone1 + '\'' +
                    ", cellphone2='" + cellphone2 + '\'' +
                    ", level='" + level + '\'' +
                    ", smartphone=" + smartphone +
                    ", rateMin='" + rateMin + '\'' +
                    ", rateMax='" + rateMax + '\'' +
                    ", rating=" + rating +
                    ", comment='" + comment + '\'' +
                    ", distance=" + distance +
                    ", languageskills=" + languageskills +
                    ", jobskills=" + jobskills +
                    '}';
        }
    }
}
