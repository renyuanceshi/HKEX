package com.hkex.soma.dataModel;

public class SS_Result {
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private String _engmsg;
    private stockInfo[] _stockinfo;

    public static class mainData {
        private String _expiry;
        private String _last;
        private String _oid;
        private String _strike;
        private String _vol;
        private String _wtype;

        public String getExpiry() {
            return this._expiry;
        }

        public String getLast() {
            return this._last;
        }

        public String getOid() {
            return this._oid;
        }

        public String getStrike() {
            return this._strike;
        }

        public String getVol() {
            return this._vol;
        }

        public String getWtype() {
            return this._wtype;
        }

        public void setExpiry(String str) {
            this._expiry = str;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setOid(String str) {
            this._oid = str;
        }

        public void setStrike(String str) {
            this._strike = str;
        }

        public void setVol(String str) {
            this._vol = str;
        }

        public void setWtype(String str) {
            this._wtype = str;
        }
    }

    public static class stockInfo {
        private String _ask;
        private String _bid;
        private String _chng;
        private String _clast;
        private String _high;
        private String _low;
        private String _pchng;
        private String _size;
        private String _stime;
        private String _turnover;
        private String _ucode;
        private String _ulast;
        private String _vol;

        public String getAsk() {
            return this._ask;
        }

        public String getBid() {
            return this._bid;
        }

        public String getChng() {
            return this._chng;
        }

        public String getClast() {
            return this._clast;
        }

        public String getHigh() {
            return this._high;
        }

        public String getLow() {
            return this._low;
        }

        public String getPchng() {
            return this._pchng;
        }

        public String getSize() {
            return this._size;
        }

        public String getStime() {
            return this._stime;
        }

        public String getTurnover() {
            return this._turnover;
        }

        public String getUcode() {
            return this._ucode;
        }

        public String getUlast() {
            return this._ulast;
        }

        public String getVol() {
            return this._vol;
        }

        public void setAsk(String str) {
            this._ask = str;
        }

        public void setBid(String str) {
            this._bid = str;
        }

        public void setChng(String str) {
            this._chng = str;
        }

        public void setClast(String str) {
            this._clast = str;
        }

        public void setHigh(String str) {
            this._high = str;
        }

        public void setLow(String str) {
            this._low = str;
        }

        public void setPchng(String str) {
            this._pchng = str;
        }

        public void setSize(String str) {
            this._size = str;
        }

        public void setStime(String str) {
            this._stime = str;
        }

        public void setTurnover(String str) {
            this._turnover = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }

        public void setUlast(String str) {
            this._ulast = str;
        }

        public void setVol(String str) {
            this._vol = str;
        }
    }

    public String getChimsg() {
        return this._chimsg;
    }

    public String getContingency() {
        return this._contingency;
    }

    public String getEngmsg() {
        return this._engmsg;
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public stockInfo[] getstockInfo() {
        return this._stockinfo;
    }

    public void setChimsg(String str) {
        this._chimsg = str;
    }

    public void setContingency(String str) {
        this._contingency = str;
    }

    public void setEngmsg(String str) {
        this._engmsg = str;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }

    public void setstockInfo(stockInfo[] stockinfoArr) {
        this._stockinfo = stockinfoArr;
    }
}
