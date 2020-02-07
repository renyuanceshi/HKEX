package com.hkex.soma.dataModel;

public class OptionsVolume_Result {
    private advData[] advData;
    private String stime;
    private totalData[] totalData;
    private String ucode;

    public static class advData {
        private String mvol;
        private String time;
        private String yvol;

        public String getMvol() {
            return this.mvol;
        }

        public String getTime() {
            return this.time;
        }

        public String getYvol() {
            return this.yvol;
        }

        public void setMvol(String str) {
            this.mvol = str;
        }

        public void setTime(String str) {
            this.time = str;
        }

        public void setYvol(String str) {
            this.yvol = str;
        }
    }

    public static class totalData {
        private String time;
        private String vol;

        public String getTime() {
            return this.time;
        }

        public String getVol() {
            return this.vol;
        }

        public void setTime(String str) {
            this.time = str;
        }

        public void setVol(String str) {
            this.vol = str;
        }
    }

    public advData[] getAdvData() {
        return this.advData;
    }

    public String getStime() {
        return this.stime;
    }

    public totalData[] getTotalData() {
        return this.totalData;
    }

    public String getUcode() {
        return this.ucode;
    }

    public void setAdvData(advData[] advdataArr) {
        this.advData = advdataArr;
    }

    public void setStime(String str) {
        this.stime = str;
    }

    public void setTotalData(totalData[] totaldataArr) {
        this.totalData = totaldataArr;
    }

    public void setUcode(String str) {
        this.ucode = str;
    }
}
