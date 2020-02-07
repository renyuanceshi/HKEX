package com.hkex.soma.dataModel;

public class OverviewTop_Result {
    private mainData[] mainData;

    public static class mainData {
        private String uname;
        private String unmll;
        private String volume;

        public String getUname() {
            return this.uname;
        }

        public String getUnmll() {
            return this.unmll;
        }

        public String getVolume() {
            return this.volume;
        }

        public void setUname(String str) {
            this.uname = str;
        }

        public void setUnmll(String str) {
            this.unmll = str;
        }

        public void setVolume(String str) {
            this.volume = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
