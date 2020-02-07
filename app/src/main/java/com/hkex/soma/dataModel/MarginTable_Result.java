package com.hkex.soma.dataModel;

public class MarginTable_Result {
    private mainData[] _data;
    private expirylist[] _expirylist;
    private hcodelist[] _hcodelist;

    public static class expirylist {
        private String _expiry;

        public String getExpiry() {
            return this._expiry;
        }

        public void setExpiry(String str) {
            this._expiry = str;
        }
    }

    public static class hcodelist {
        private String _hcode;
        private String _ucode;

        public String getHcode() {
            return this._hcode;
        }

        public String getUcode() {
            return this._ucode;
        }

        public void setHcode(String str) {
            this._hcode = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }
    }

    public static class mainData {
        private String _cvalue;
        private String _expiry;
        private String _pvalue;
        private String _stime;
        private String _strike;

        public String getCvalue() {
            return this._cvalue;
        }

        public String getExpiry() {
            return this._expiry;
        }

        public String getPvalue() {
            return this._pvalue;
        }

        public String getStime() {
            return this._stime;
        }

        public String getStrike() {
            return this._strike;
        }

        public void setCvalue(String str) {
            this._cvalue = str;
        }

        public void setExpiry(String str) {
            this._expiry = str;
        }

        public void setPvalue(String str) {
            this._pvalue = str;
        }

        public void setStime(String str) {
            this._stime = str;
        }

        public void setStrike(String str) {
            this._strike = str;
        }
    }

    public expirylist[] getexpirylist() {
        return this._expirylist;
    }

    public hcodelist[] gethcodelist() {
        return this._hcodelist;
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setexpirylist(expirylist[] expirylistArr) {
        this._expirylist = expirylistArr;
    }

    public void sethcodelist(hcodelist[] hcodelistArr) {
        this._hcodelist = hcodelistArr;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
