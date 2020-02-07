package com.hkex.soma.dataModel;

public class Portfolio_Result {
    private String _chimsg;
    private String _contingency;
    private mainData[] _data;
    private String _engmsg;
    private String _stime;

    public static class mainData {
        private String _code;
        private String _contract;
        private String _direction;
        private String _expiry;
        private String _last;
        private String _margin;
        private String _oid;
        private String _price;
        private String _qty;
        private boolean _selected = false;
        private String _strike;
        private String _type;
        private String _ucode;
        private String _uname;
        private String _unmll;
        private String expiry_str;
        private String l_margin;
        private String s_margin;
        private String typename;

        public String getCode() {
            return this._code;
        }

        public String getContract() {
            return this._contract;
        }

        public String getDirection() {
            return this._direction;
        }

        public String getExpiry() {
            return this._expiry;
        }

        public String getExpiry_str() {
            return this.expiry_str;
        }

        public String getL_margin() {
            return this.l_margin;
        }

        public String getLast() {
            return this._last;
        }

        public String getMargin() {
            return this._margin;
        }

        public String getOid() {
            return this._oid;
        }

        public String getPrice() {
            return this._price;
        }

        public String getQty() {
            return this._qty;
        }

        public String getS_margin() {
            return this.s_margin;
        }

        public boolean getSelected() {
            return this._selected;
        }

        public String getStrike() {
            return this._strike;
        }

        public String getType() {
            return this._type;
        }

        public String getTypename() {
            return this.typename;
        }

        public String getUName() {
            return this._uname;
        }

        public String getUcode() {
            return this._ucode;
        }

        public String getUnmll() {
            return this._unmll;
        }

        public void setContract(String str) {
            this._contract = str;
        }

        public void setDirection(String str) {
            this._direction = str;
        }

        public void setExpiry(String str) {
            this._expiry = str;
        }

        public void setExpiry_str(String str) {
            this.expiry_str = str;
        }

        public void setL_margin(String str) {
            this.l_margin = str;
        }

        public void setLast(String str) {
            this._last = str;
        }

        public void setMargin(String str) {
            this._margin = str;
        }

        public void setOid(String str) {
            this._oid = str;
        }

        public void setPrice(String str) {
            this._price = str;
        }

        public void setQty(String str) {
            this._qty = str;
        }

        public void setS_margin(String str) {
            this.s_margin = str;
        }

        public void setSelected(boolean z) {
            this._selected = z;
        }

        public void setStrike(String str) {
            this._strike = str;
        }

        public void setType(String str) {
            this._type = str;
        }

        public void setTypename(String str) {
            this.typename = str;
        }

        public void setUName(String str) {
            this._uname = str;
        }

        public void setUcode(String str) {
            this._ucode = str;
        }

        public void setUnmll(String str) {
            this._unmll = str;
        }

        public void setcode(String str) {
            this._code = str;
        }
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

    public mainData[] getmainData() {
        return this._data;
    }

    public String getstime() {
        return this._stime;
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

    public void setmainData(mainData[] maindataArr) {
        this._data = maindataArr;
    }

    public void setstime(String str) {
        this._stime = str;
    }
}
