package com.hkex.soma.dataModel;

public class FuturesList_Result {
    private mainData[] mainData;

    public static class mainData {
        private String ahtflag;
        private String mid;
        private String name;
        private String nmll;

        public String getAhtflag() {
            return this.ahtflag;
        }

        public String getMid() {
            return this.mid;
        }

        public String getName() {
            return this.name;
        }

        public String getNmll() {
            return this.nmll;
        }

        public void setAhtflag(String str) {
            this.ahtflag = str;
        }

        public void setMid(String str) {
            this.mid = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setNmll(String str) {
            this.nmll = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
