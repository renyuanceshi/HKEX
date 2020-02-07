package com.hkex.soma.dataModel;

public class Dividend_Result {
    private mainData[] _data;

    public static class mainData {
        private String _announceDate;
        private String _chidetails;
        private String _details;
        private String _exDate;
        private String _payDate;
        private String _ucode;

        public String getAnnounceDate() {
            return this._announceDate;
        }

        public String getChidetails() {
            return this._chidetails;
        }

        public String getDetails() {
            return this._details;
        }

        public String getExDate() {
            return this._exDate;
        }

        public String getPayDate() {
            return this._payDate;
        }

        public String getUcode() {
            return this._ucode;
        }

        public void setAnnounceDate(String str) {
            this._announceDate = str;
        }

        public void setChidetails(String str) {
            this._chidetails = str;
        }

        public void setDetails(String str) {
            this._details = str;
        }

        public void setExDate(String str) {
            this._exDate = str;
        }

        public void setPayDate(String str) {
            this._payDate = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
