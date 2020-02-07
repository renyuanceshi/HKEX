package com.hkex.soma.dataModel;

public class MS_IndicesResult {
    private String _chart;
    private String _chartText;
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private String _engmsg;
    private String _name;
    private String _stime;

    public static class mainData {
        private String _chng;
        private String _index;
        private String _last;
        private String _name;
        private String _pchng;
        private String chart;

        public String getChart() {
            return this.chart;
        }

        public String getChng() {
            return this._chng;
        }

        public String getIndex() {
            return this._index;
        }

        public String getLast() {
            return this._last;
        }

        public String getName() {
            return this._name;
        }

        public String getPchng() {
            return this._pchng;
        }

        public void setChart(String str) {
            this.chart = str;
        }

        public void setChng(String str) {
            this._chng = str;
        }

        public void setIndex(String str) {
            this._index = str;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setName(String str) {
            this._name = str;
        }

        public void setPchng(String str) {
            this._pchng = str;
        }
    }

    public String getChart() {
        return this._chart;
    }

    public String getChartText() {
        return this._chartText;
    }

    public String getChimsg() {
        return this._chimsg;
    }

    public String getContingency() {
        return this._contingency;
    }

    public String getEngmsg() {
        return this._engmsg;
    }

    public String getName() {
        return this._name;
    }

    public mainData[] getmainData() {
        return this._data;
    }

    public String getstime() {
        return this._stime;
    }

    public void setChart(String str) {
        this._chart = str;
    }

    public void setChartText(String str) {
        this._chartText = str;
    }

    public void setChimsg(String str) {
        this._chimsg = str;
    }

    public void setContingency(String str) {
        this._contingency = str;
    }

    public void setEngmsg(String str) {
        this._engmsg = str;
    }

    public void setName(String str) {
        this._name = str;
    }

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }

    public void setstime(String str) {
        this._stime = str;
    }
}
