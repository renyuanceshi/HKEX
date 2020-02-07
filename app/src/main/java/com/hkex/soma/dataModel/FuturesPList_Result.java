package com.hkex.soma.dataModel;

public class FuturesPList_Result {
    public String daynight;
    public String id;
    private mainData[] mainData;

    public static class mainData {
        private String ahtflag;
        private String name;
        private String nmll;
        private String pid;

        public String getAhtflag() {
            return this.ahtflag;
        }

        public String getName() {
            return this.name;
        }

        public String getNmll() {
            return this.nmll;
        }

        public String getPid() {
            return this.pid;
        }

        public void setAhtflag(String str) {
            this.ahtflag = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setNmll(String str) {
            this.nmll = str;
        }

        public void setPid(String str) {
            this.pid = str;
        }
    }

    public String getDaynight() {
        return this.daynight;
    }

    public String getId() {
        return this.id;
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setDaynight(String str) {
        this.daynight = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
