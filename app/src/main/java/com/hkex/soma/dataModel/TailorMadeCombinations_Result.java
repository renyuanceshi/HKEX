package com.hkex.soma.dataModel;

public class TailorMadeCombinations_Result {
    private mainData[] _data;
    private optionlist[] optionlist;

    public static class mainData {
        private String ask;
        private String bid;
        private String hcode;
        private String high;
        private String legs;
        private String low;
        private String traded;
        private String vol;

        public String getAsk() {
            return this.ask;
        }

        public String getBid() {
            return this.bid;
        }

        public String getHcode() {
            return this.hcode;
        }

        public String getHigh() {
            return this.high;
        }

        public String getLegs() {
            return this.legs;
        }

        public String getLow() {
            return this.low;
        }

        public String getTraded() {
            return this.traded;
        }

        public String getVol() {
            return this.vol;
        }

        public void setAsk(String str) {
            this.ask = str;
        }

        public void setBid(String str) {
            this.bid = str;
        }

        public void setHcode(String str) {
            this.hcode = str;
        }

        public void setHigh(String str) {
            this.high = str;
        }

        public void setLegs(String str) {
            this.legs = str;
        }

        public void setLow(String str) {
            this.low = str;
        }

        public void setTraded(String str) {
            this.traded = str;
        }

        public void setVol(String str) {
            this.vol = str;
        }
    }

    public static class optionlist {
        private String hcode;
        private String name;

        public String getHcode() {
            return this.hcode;
        }

        public String getName() {
            return this.name;
        }

        public void setHcode(String str) {
            this.hcode = str;
        }

        public void setName(String str) {
            this.name = str;
        }
    }

    public optionlist[] getOptionlist() {
        return this.optionlist;
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setOptionlist(optionlist[] optionlistArr) {
        this.optionlist = optionlistArr;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
