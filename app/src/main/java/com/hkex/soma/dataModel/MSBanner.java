package com.hkex.soma.dataModel;

public class MSBanner {
    private banner _banner1;
    private banner _banner2;
    private banner _banner3;
    private String _ticker;

    public static class banner {
        private String _duration;
        private String _img;
        private String _publish;
        private int _type;
        private String _url;

        public String getImg() {
            return this._img;
        }

        public int getType() {
            return this._type;
        }

        public String getUrl() {
            return this._url;
        }

        public String getduration() {
            return this._duration;
        }

        public String getpublish() {
            return this._publish;
        }

        public void setImg(String str) {
            this._img = str;
        }

        public void setType(int i) {
            this._type = i;
        }

        public void setUrl(String str) {
            this._url = str;
        }

        public void setduration(String str) {
            this._duration = str;
        }

        public void setpublish(String str) {
            this._publish = str;
        }
    }

    public String getTicker() {
        return this._ticker;
    }

    public banner getbanner_1() {
        return this._banner1;
    }

    public banner getbanner_2() {
        return this._banner2;
    }

    public banner getbanner_3() {
        return this._banner3;
    }

    public void setTicker(String str) {
        this._ticker = str;
    }

    public void setbanner_1(banner banner2) {
        this._banner1 = banner2;
    }

    public void setbanner_2(banner banner2) {
        this._banner2 = banner2;
    }

    public void setbanner_3(banner banner2) {
        this._banner3 = banner2;
    }
}
