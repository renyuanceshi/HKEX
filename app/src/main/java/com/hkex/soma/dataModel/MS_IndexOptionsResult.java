package com.hkex.soma.dataModel;

public class MS_IndexOptionsResult {
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private String _engmsg;
    private String _stime;
    private String _traded;

    public static class mainData {
        private String _last;
        private String _mdate;
        private String _oi;
        private String _oid;
        private String _pchng;
        private String _strike;
        private String _type;
        private String _ucode;
        private String _uname;
        private String _unmll;
        private String _vol;

        public String getLast() {
            return this._last;
        }

        public String getMdate() {
            return this._mdate;
        }

        public String getOi() {
            return this._oi;
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

        public String getType() {
            return this._type;
        }

        public String getUcode() {
            return this._ucode;
        }

        public String getUname() {
            return this._uname;
        }

        public String getUnmll() {
            return this._unmll;
        }

        public String getVol() {
            return this._vol;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setMdate(String str) {
            this._mdate = str;
        }

        public void setOi(String str) {
            this._oi = str;
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

        public void setType(String str) {
            this._type = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }

        public void setUname(String str) {
            this._uname = str;
        }

        public void setUnmll(String str) {
            this._unmll = str;
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

    public String getstime() {
        return this._stime;
    }

    public String gettraded() {
        return this._traded;
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

    public void setstime(String str) {
        this._stime = str;
    }

    public void settraded(String str) {
        this._traded = str;
    }
}
