package com.dipen.pricer.Calculations;

public class OneAsset extends AbstractAsset {
    protected double K;
    protected double T;
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};
    protected double b;
    functionArray[] callFunctions = {new functionArray() {
        public double function(double d) {
            return OneAsset.this.callPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callVega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.callElasticity(d);
        }
    }};
    protected double d1;
    protected double d2;
    functionArray[] putFunctions = {new functionArray() {
        public double function(double d) {
            return OneAsset.this.putPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putVega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return OneAsset.this.putElasticity(d);
        }
    }};
    protected double q;
    protected double r;
    protected double s0;
    protected double sig;

    public interface functionArray {
        double function(double d);
    }

    OneAsset() {
    }

    OneAsset(double[] dArr) {
        this.s0 = dArr[0];
        this.K = dArr[1];
        this.T = dArr[2];
        this.r = dArr[3] / 100.0d;
        this.q = dArr[4] / 100.0d;
        this.sig = dArr[5] / 100.0d;
        prepare();
    }

    /* access modifiers changed from: protected */
    public double callDelta(double d) {
        double d3 = this.s0;
        this.s0 += d;
        double callPrice = callPrice();
        this.s0 -= 2.0d * d;
        double callPrice2 = callPrice();
        this.s0 = d3;
        return (callPrice - callPrice2) / (2.0d * d);
    }

    /* access modifiers changed from: protected */
    public double callElasticity(double d) {
        return (callDelta(d) * this.s0) / callPrice();
    }

    /* access modifiers changed from: protected */
    public double callGamma(double d) {
        double d3 = this.s0;
        this.s0 += d;
        double callDelta = callDelta(d);
        this.s0 -= 2.0d * d;
        double callDelta2 = callDelta(d);
        this.s0 = d3;
        return (callDelta - callDelta2) / (2.0d * d);
    }

    /* access modifiers changed from: protected */
    public double callPrice() {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callRho(double d) {
        double d3 = this.r;
        this.r += d;
        double callPrice = callPrice();
        this.r -= 2.0d * d;
        double callPrice2 = callPrice();
        this.r = d3;
        return (callPrice - callPrice2) / (200.0d * d);
    }

    /* access modifiers changed from: protected */
    public double callTheta(double d) {
        double d3 = this.T;
        this.T += d;
        double callPrice = callPrice();
        this.T -= 2.0d * d;
        double callPrice2 = callPrice();
        this.T = d3;
        return (-(callPrice - callPrice2)) / (730.0d * d);
    }

    /* access modifiers changed from: protected */
    public double callVega(double d) {
        double d3 = this.sig;
        this.sig += d;
        double callPrice = callPrice();
        this.sig -= 2.0d * d;
        double callPrice2 = callPrice();
        this.sig = d3;
        return (callPrice - callPrice2) / (200.0d * d);
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
        this.d1 = (1.0d / (this.sig * Math.sqrt(this.T))) * (Math.log(this.s0 / this.K) + ((this.b + (0.5d * this.sig * this.sig)) * this.T));
        this.d2 = this.d1 - (this.sig * Math.sqrt(this.T));
    }

    /* access modifiers changed from: protected */
    public double putDelta(double d) {
        double d3 = this.s0;
        this.s0 += d;
        double putPrice = putPrice();
        this.s0 -= 2.0d * d;
        double putPrice2 = putPrice();
        this.s0 = d3;
        return (putPrice - putPrice2) / (2.0d * d);
    }

    /* access modifiers changed from: protected */
    public double putElasticity(double d) {
        return (putDelta(d) * this.s0) / putPrice();
    }

    /* access modifiers changed from: protected */
    public double putGamma(double d) {
        double d3 = this.s0;
        this.s0 += d;
        double putDelta = putDelta(d);
        this.s0 -= 2.0d * d;
        double putDelta2 = putDelta(d);
        this.s0 = d3;
        return (putDelta - putDelta2) / (2.0d * d);
    }

    /* access modifiers changed from: protected */
    public double putPrice() {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putRho(double d) {
        double d3 = this.r;
        this.r += d;
        double putPrice = putPrice();
        this.r -= 2.0d * d;
        double putPrice2 = putPrice();
        this.r = d3;
        return (putPrice - putPrice2) / (200.0d * d);
    }

    /* access modifiers changed from: protected */
    public double putTheta(double d) {
        double d3 = this.T;
        this.T += d;
        double putPrice = putPrice();
        this.T -= 2.0d * d;
        double putPrice2 = putPrice();
        this.T = d3;
        return (-(putPrice - putPrice2)) / (730.0d * d);
    }

    /* access modifiers changed from: protected */
    public double putVega(double d) {
        double d3 = this.sig;
        this.sig += d;
        double putPrice = putPrice();
        this.sig -= 2.0d * d;
        double putPrice2 = putPrice();
        this.sig = d3;
        return (putPrice - putPrice2) / (200.0d * d);
    }

    public void reconstruct(double[] dArr) {
        this.s0 = dArr[0];
        this.K = dArr[1];
        this.T = dArr[2];
        this.r = dArr[3] / 100.0d;
        this.q = dArr[4] / 100.0d;
        this.sig = dArr[5] / 100.0d;
        prepare();
    }
}
