package com.hkex.soma.dataModel;

public class Video_Result {
    private mainData[] _data;

    public static class mainData {
        private String date;
        private String duration;
        private String imgdata;
        private String title_c;
        private String title_e;
        private int uid;
        private String videourl;

        public String getDate() {
            return this.date;
        }

        public String getDuration() {
            return this.duration;
        }

        public String getImgdata() {
            return this.imgdata;
        }

        public String getTitle_c() {
            return this.title_c;
        }

        public String getTitle_e() {
            return this.title_e;
        }

        public int getUid() {
            return this.uid;
        }

        public String getVideourl() {
            return this.videourl;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public void setDuration(String str) {
            this.duration = str;
        }

        public void setImgdata(String str) {
            this.imgdata = str;
        }

        public void setTitle_c(String str) {
            this.title_c = str;
        }

        public void setTitle_e(String str) {
            this.title_e = str;
        }

        public void setUid(int i) {
            this.uid = i;
        }

        public void setVideourl(String str) {
            this.videourl = str;
        }
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
