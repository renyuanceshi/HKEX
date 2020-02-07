package com.hkex.soma.dataModel;

public class StaticProductTable_Result {
    private mainData[] mainData;

    public static class mainData {
        private String link;
        private String pid;
        private String title;
        private String type;

        public String getLink() {
            return this.link;
        }

        public String getPid() {
            return this.pid;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public void setLink(String str) {
            this.link = str;
        }

        public void setPid(String str) {
            this.pid = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setType(String str) {
            this.type = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
