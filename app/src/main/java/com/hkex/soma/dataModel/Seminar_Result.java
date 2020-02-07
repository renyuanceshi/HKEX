package com.hkex.soma.dataModel;

public class Seminar_Result {
    private mainData[] mainData;

    public static class mainData {
        private String date;
        private String detail;
        private String language;
        private String link;
        private String sid;
        private String speaker;
        private String time;
        private String title;
        private String type;
        private String venue;

        public String getDate() {
            return this.date;
        }

        public String getDetail() {
            return this.detail;
        }

        public String getLanguage() {
            return this.language;
        }

        public String getLink() {
            return this.link;
        }

        public String getSid() {
            return this.sid;
        }

        public String getSpeaker() {
            return this.speaker;
        }

        public String getTime() {
            return this.time;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getVenue() {
            return this.venue;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public void setDetail(String str) {
            this.detail = str;
        }

        public void setLanguage(String str) {
            this.language = str;
        }

        public void setLink(String str) {
            this.link = str;
        }

        public void setSid(String str) {
            this.sid = str;
        }

        public void setSpeaker(String str) {
            this.speaker = str;
        }

        public void setTime(String str) {
            this.time = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setVenue(String str) {
            this.venue = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
