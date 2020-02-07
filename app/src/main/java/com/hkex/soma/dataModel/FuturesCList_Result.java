package com.hkex.soma.dataModel;

public class FuturesCList_Result {
    public String aht;
    public String chimsg;
    public String contingency;
    public String daynight;
    public String engmsg;
    public String id;
    private mainData[] mainData;
    public String stime;

    public static class mainData {
        private String ask;
        private String bid;
        private String cid;
        private String intraday;
        private String last;
        private String mdate;
        private String month;
        private String pchng;
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

        public void setVol(String str) {
            this.vol = str;
        }
    }

    public String getAht() {
        return this.aht;
    }

    public String getChimsg() {
        return this.chimsg;
    }

    public String getContingency() {
        return this.contingency;
    }

    public String getDaynight() {
        return this.daynight;
    }

    public String getEngmsg() {
        return this.engmsg;
    }

    public String getId() {
        return this.id;
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public String getStime() {
        return this.stime;
    }

    public void setAht(String str) {
        this.aht = str;
    }

    public void setChimsg(String str) {
        this.chimsg = str;
    }

    public void setContingency(String str) {
        this.contingency = str;
    }

    public void setDaynight(String str) {
        this.daynight = str;
    }

    public void setEngmsg(String str) {
        this.engmsg = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }

    public void setStime(String str) {
        this.stime = str;
    }
}
