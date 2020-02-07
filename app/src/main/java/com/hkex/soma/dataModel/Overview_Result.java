package com.hkex.soma.dataModel;

public class Overview_Result {
    private String chimsg;
    private String contingency;
    private String engmsg;
    private String futurestime;
    private mainData[] mainData;
    private mainData2[] mainData2;
    private String optionstime;

    public static class mainData {
        private String last;
        private String mdate;
        private String oi;
        private String oid;
        private String pchng;
        private String strike;
        private String type;
        private String ucode;
        private String uname;
        private String unmll;
        private String vol;

        public String getLast() {
            return this.last;
        }

        public String getMdate() {
            return this.mdate;
        }

        public String getOi() {
            return this.oi;
        }

        public String getOid() {
            return this.oid;
        }

        public String getPchng() {
            return this.pchng;
        }

        public String getStrike() {
            return this.strike;
        }

        public String getType() {
            return this.type;
        }

        public String getUcode() {
            return this.ucode;
        }

        public String getUname() {
            return this.uname;
        }

        public String getUnmll() {
            return this.unmll;
        }

        public String getVol() {
            return this.vol;
        }

        public void setLast(String str) {
            this.last = str;
        }

        public void setMdate(String str) {
            this.mdate = str;
        }

        public void setOi(String str) {
            this.oi = str;
        }

        public void setOid(String str) {
            this.oid = str;
        }

        public void setPchng(String str) {
            this.pchng = str;
        }

        public void setStrike(String str) {
            this.strike = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUcode(String str) {
            this.ucode = str;
        }

        public void setUname(String str) {
            this.uname = str;
        }

        public void setUnmll(String str) {
            this.unmll = str;
        }

        public void setVol(String str) {
            this.vol = str;
        }
    }

    public static class mainData2 {
        private String ask;
        private String bid;
        private String cid;
        private String code;
        private String intraday;
        private String last;
        private String mdate;
        private String month;
        private String pchng;
        private String uname;
        private String unmll;
        private String vol;

        public String getAsk() {
            return this.ask;
        }

        public String getBid() {
            return this.bid;
        }

        public String getCid() {
            return this.cid;
        }

        public String getCode() {
            return this.code;
        }

        public String getIntraday() {
            return this.intraday;
        }

        public String getLast() {
            return this.last;
        }

        public String getMdate() {
            return this.mdate;
        }

        public String getMonth() {
            return this.month;
        }

        public String getPchng() {
            return this.pchng;
        }

        public String getUname() {
            return this.uname;
        }

        public String getUnmll() {
            return this.unmll;
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

        public void setCid(String str) {
            this.cid = str;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public void setIntraday(String str) {
            this.intraday = str;
        }

        public void setLast(String str) {
            this.last = str;
        }

        public void setMdate(String str) {
            this.mdate = str;
        }

        public void setMonth(String str) {
            this.month = str;
        }

        public void setPchng(String str) {
            this.pchng = str;
        }

        public void setUname(String str) {
            this.uname = str;
        }

        public void setUnmll(String str) {
            this.unmll = str;
        }

        public void setVol(String str) {
            this.vol = str;
        }
    }

    public String getChimsg() {
        return this.chimsg;
    }

    public String getContingency() {
        return this.contingency;
    }

    public String getEngmsg() {
        return this.engmsg;
    }

    public String getFuturestime() {
        return this.futurestime;
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public mainData2[] getMainData2() {
        return this.mainData2;
    }

    public String getOptionstime() {
        return this.optionstime;
    }

    public void setChimsg(String str) {
        this.chimsg = str;
    }

    public void setContingency(String str) {
        this.contingency = str;
    }

    public void setEngmsg(String str) {
        this.engmsg = str;
    }

    public void setFuturestime(String str) {
        this.futurestime = str;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }

    public void setMainData2(mainData2[] maindata2Arr) {
        this.mainData2 = maindata2Arr;
    }

    public void setOptionstime(String str) {
        this.optionstime = str;
    }
}
