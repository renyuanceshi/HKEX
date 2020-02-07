package com.hkex.soma.dataModel;

public class VideoType_Result {
    private mainData[] mainData;

    public static class mainData {
        private String id;
        private String title;

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }
    }

    public mainData[] getMainData() {
        return this.mainData;
    }

    public void setMainData(mainData[] maindataArr) {
        this.mainData = maindataArr;
    }
}
