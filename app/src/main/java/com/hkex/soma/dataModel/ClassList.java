package com.hkex.soma.dataModel;

public class ClassList {
    private mainData[] _data;

    public static class mainData {
        private String _board;
        private String _hcode;
        private String _size;
        private String _stime;
        private String _ucode;

        public String getBoard() {
            return this._board;
        }

        public String getHcode() {
            return this._hcode;
        }

        public String getSize() {
            return this._size;
        }

        public String getStime() {
            return this._stime;
        }

        public String getUcode() {
            return this._ucode;
        }

        public void setBoard(String str) {
            this._board = str;
        }

        public void setHcode(String str) {
            this._hcode = str;
        }

        public void setSize(String str) {
            this._size = str;
        }

        public void setStime(String str) {
            this._stime = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }
}
