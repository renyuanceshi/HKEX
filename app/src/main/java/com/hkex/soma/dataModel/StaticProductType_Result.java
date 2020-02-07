package com.hkex.soma.dataModel;

public class StaticProductType_Result {
    private mainData[] mainData;

    public static class mainData {
        private String name;
        private String tid;

        public String getName() {
            return this.name;
        }

        public String getTid() {
            return this.tid;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setTid(String str) {
            this.tid = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
