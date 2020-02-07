package com.hkex.soma.dataModel;

public class MarketReport_Result {
    private mainData[] mainData;

    public static class mainData {
        private String date;
        private String file;
        private String mrid;
        private String title;

        public mainData() {
        }

        public mainData(String str, String str2, String str3, String str4) {
            this.mrid = str;
            this.date = str2;
            this.title = str3;
            this.file = str4;
        }

        public String getDate() {
            return this.date;
        }

        public String getFile() {
            return this.file;
        }

        public String getMrid() {
            return this.mrid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public void setFile(String str) {
            this.file = str;
        }

        public void setMrid(String str) {
            this.mrid = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
