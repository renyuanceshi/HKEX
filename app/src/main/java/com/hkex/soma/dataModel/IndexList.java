package com.hkex.soma.dataModel;

public class IndexList {
    private mainData[] _data;

    public static class mainData {
        private String _ucode;
        private String _uname;
        private String _unmll;

        public String getUcode() {
            return this._ucode;
        }

        public String getUname() {
            return this._uname;
        }

        public String getUnmll() {
            return this._unmll;
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
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
