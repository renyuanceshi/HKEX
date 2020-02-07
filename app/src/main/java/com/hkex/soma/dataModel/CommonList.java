package com.hkex.soma.dataModel;

public class CommonList {
    private String _android;
    private mainData[] _data;
    private options[] _option;
    private stocklist[] _stock;
    private String _stockcode;
    private String _stockname;
    private String _stocknmll;
    private underlyinglist[] _underlying;
    private String _underlyingcode;
    private String indexcode;
    private indexlist[] indexlist;
    private String indexname;
    private String indexnmll;

    public static class indexlist {
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

    public static class mainData {
        private options[] _options;
        private String _ucode;

        public options[] getOptions() {
            return this._options;
        }

        public String getUcode() {
            return this._ucode;
        }

        public void setOptions(options[] optionsArr) {
            this._options = optionsArr;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }
    }

    public static class options {
        private String _hcode;
        private String _mdate;
        private String _strike;

        public String getHcode() {
            return this._hcode;
        }

        public String getMdate() {
            return this._mdate;
        }

        public String getStrike() {
            return this._strike;
        }

        public void setHcode(String str) {
            this._hcode = str;
        }

        public void setMdate(String str) {
            this._mdate = str;
        }

        public void setStrike(String str) {
            this._strike = str;
        }
    }

    public static class stocklist {
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

    public static class underlyinglist {
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

    public String getIndexcode() {
        return this.indexcode;
    }

    public indexlist[] getIndexlist() {
        return this.indexlist;
    }

    public String getIndexname() {
        return this.indexname;
    }

    public String getIndexnmll() {
        return this.indexnmll;
    }

    public options[] getOptions() {
        return this._option;
    }

    public String getandroid() {
        return this._android;
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public String getstockcode() {
        return this._stockcode;
    }

    public stocklist[] getstocklist() {
        return this._stock;
    }

    public String getstockname() {
        return this._stockname;
    }

    public String getstocknmll() {
        return this._stocknmll;
    }

    public String getunderlyingcode() {
        return this._underlyingcode;
    }

    public underlyinglist[] getunderlyinglist() {
        return this._underlying;
    }

    public void setIndexcode(String str) {
        this.indexcode = str;
    }

    public void setIndexlist(indexlist[] indexlistArr) {
        this.indexlist = indexlistArr;
    }

    public void setIndexname(String str) {
        this.indexname = str;
    }

    public void setIndexunmll(String str) {
        this.indexnmll = str;
    }

    public void setOptions(options[] optionsArr) {
        this._option = optionsArr;
    }

    public void setandroid(String str) {
        this._android = str;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }

    public void setstockcode(String str) {
        this._stockcode = str;
    }

    public void setstocklist(stocklist[] stocklistArr) {
        this._stock = stocklistArr;
    }

    public void setstockname(String str) {
        this._stockname = str;
    }

    public void setstocknmll(String str) {
        this._stocknmll = str;
    }

    public void setunderlyingcode(String str) {
        this._underlyingcode = str;
    }

    public void setunderlyinglist(underlyinglist[] underlyinglistArr) {
        this._underlying = underlyinglistArr;
    }
}
