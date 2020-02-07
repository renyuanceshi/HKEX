package com.hkex.soma.dataModel;

public class SO_Result {
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private mainData2[] _data2;
    private String _engmsg;
    private String _stime;

    public static class expiryOption {
        private String _mdate;

        public String getMdate() {
            return this._mdate;
        }

        public void setMdate(String str) {
            this._mdate = str;
        }
    }

    public static class indexInfo {
        private String _uchng;
        private String _ucode;
        private String _ulast;
        private String _uname;
        private String _unmll;
        private String _upchng;

        public String getUchng() {
            return this._uchng;
        }

        public String getUcode() {
            return this._ucode;
        }

        public String getUlast() {
            return this._ulast;
        }

        public String getUname() {
            return this._uname;
        }

        public String getUnmll() {
            return this._unmll;
        }

        public String getUpchng() {
            return this._upchng;
        }

        public void setUchng(String str) {
            this._uchng = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }

        public void setUlast(String str) {
            this._ulast = str;
        }

        public void setUname(String str) {
            this._uname = str;
        }

        public void setUnmll(String str) {
            this._unmll = str;
        }

        public void setUpchng(String str) {
            this._upchng = str;
        }
    }

    public static class mainData {
        private expiryOption[] _expiryoption;
        private indexInfo[] _indexInfo;
        private underlyingInfo[] _underlyinginfo;

        public expiryOption[] getExpiryOption() {
            return this._expiryoption;
        }

        public indexInfo[] getIndexInfo() {
            return this._indexInfo;
        }

        public underlyingInfo[] getUnderlyingInfo() {
            return this._underlyinginfo;
        }

        public void setExpiryOption(expiryOption[] expiryoptionArr) {
            this._expiryoption = expiryoptionArr;
        }

        public void setIndexInfo(indexInfo[] indexinfoArr) {
            this._indexInfo = indexinfoArr;
        }

        public void setUnderlyingInfo(underlyingInfo[] underlyinginfoArr) {
            this._underlyinginfo = underlyinginfoArr;
        }
    }

    public static class mainData2 {
        private String _chng;
        private String _last;
        private String _oid;
        private String _pchng;
        private String _strike;
        private String _vol;

        public String getChng() {
            return this._chng;
        }

        public String getLast() {
            return this._last;
        }

        public String getOid() {
            return this._oid;
        }

        public String getPchng() {
            return this._pchng;
        }

        public String getStrike() {
            return this._strike;
        }

        public String getVol() {
            return this._vol;
        }

        public void setChng(String str) {
            this._chng = str;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setOid(String str) {
            this._oid = str;
        }

        public void setPchng(String str) {
            this._pchng = str;
        }

        public void setStrike(String str) {
            this._strike = str;
        }

        public void setVol(String str) {
            this._vol = str;
        }
    }

    public static class underlyingInfo {
        private String _uchng;
        private String _ucode;
        private String _ulast;
        private String _uname;
        private String _unmll;
        private String _upchng;

        public String getUchng() {
            return this._uchng;
        }

        public String getUcode() {
            return this._ucode;
        }

        public String getUlast() {
            return this._ulast;
        }

        public String getUname() {
            return this._uname;
        }

        public String getUnmll() {
            return this._unmll;
        }

        public String getUpchng() {
            return this._upchng;
        }

        public void setUchng(String str) {
            this._uchng = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }

        public void setUlast(String str) {
            this._ulast = str;
        }

        public void setUname(String str) {
            this._uname = str;
        }

        public void setUnmll(String str) {
            this._unmll = str;
        }

        public void setUpchng(String str) {
            this._upchng = str;
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

    public mainData2[] getmainData2() {
        return this._data2;
    }

    public String getstime() {
        return this._stime;
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

    public void setmainData2(mainData2[] maindata2Arr) {
        this._data2 = maindata2Arr;
    }

    public void setstime(String str) {
        this._stime = str;
    }
}
