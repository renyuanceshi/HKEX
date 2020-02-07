package com.hkex.soma.dataModel;

public class MS_StocksResult {
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private String _engmsg;
    private String _stime;

    public static class mainData {
        private String _code;
        private String _last;
        private String _name;
        private String _nmll;
        private String _pchng;
        private String _turnover;

        public String getCode() {
            return this._code;
        }

        public String getLast() {
            return this._last;
        }

        public String getName() {
            return this._name;
        }

        public String getNmll() {
            return this._nmll;
        }

        public String getPchng() {
            return this._pchng;
        }

        public String getTurnover() {
            return this._turnover;
        }

        public void setCode(String str) {
            this._code = str;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setName(String str) {
            this._name = str;
        }

        public void setNmll(String str) {
            this._nmll = str;
        }

        public void setPchng(String str) {
            this._pchng = str;
        }

        public void setTurnover(String str) {
            this._turnover = str;
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
}
