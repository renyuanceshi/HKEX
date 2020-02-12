package com.dipen.pricer.Calculations;

public class BSM extends OneAsset {
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};
    functionArray[] callFunctions = {new functionArray() {
        public double function(double d) {
            return BSM.this.callPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.vega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callElasticity(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.vanna(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.volga(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.callCharm(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.veta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.speed(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.color(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.zomma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.ultima(d);
        }
    }};
    functionArray[] putFunctions = {new functionArray() {
        public double function(double d) {
            return BSM.this.putPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.vega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putElasticity(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.vanna(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.volga(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.putCharm(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.veta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.speed(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.color(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.zomma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return BSM.this.ultima(d);
        }
    }};

    public interface functionArray {
        double function(double d);
    }

    public BSM(double[] dArr) {
        reconstruct(dArr);
    }

    /* access modifiers changed from: protected */
    public double callCharm(double d) {
        return ((-Math.exp((this.b - this.r) * this.T)) * ((NN.N(this.d1) * ((this.b / (this.sig * Math.sqrt(this.T))) - (this.d2 / (2.0d * this.T)))) + ((this.b - this.r) * NN.C(this.d1)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double callDelta(double d) {
        return Math.exp((this.b - this.r) * this.T) * NN.C(this.d1);
    }

    /* access modifiers changed from: protected */
    public double callElasticity(double d) {
        return (callDelta(d) * this.s0) / callPrice();
    }

    /* access modifiers changed from: protected */
    public double callGamma(double d) {
        return gamma(d);
    }

    /* access modifiers changed from: protected */
    public double callPrice() {
        prepare();
        if (this.s0 == this.K && this.T == 0.0d) {
            return 0.0d;
        }
        return ((this.s0 * Math.exp((this.b - this.r) * this.T)) * NN.C(this.d1)) - ((this.K * Math.exp((-this.r) * this.T)) * NN.C(this.d2));
    }

    /* access modifiers changed from: protected */
    public double callRho(double d) {
        return (((this.T * this.K) * Math.exp((-this.r) * this.T)) * NN.C(this.d2)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double callTheta(double d) {
        return (((((((-this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.N(this.d1)) * this.sig) / (2.0d * Math.sqrt(this.T))) - ((((this.b - this.r) * this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.C(this.d1))) - (((this.r * this.K) * Math.exp((-this.r) * this.T)) * NN.C(this.d2))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double color(double d) {
        return (gamma(d) * (((this.r - this.b) + ((this.b * this.d1) / (this.sig * Math.sqrt(this.T)))) + ((1.0d - (this.d1 * this.d2)) / (2.0d * this.T)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double gamma(double d) {
        return (NN.N(this.d1) * Math.exp((this.b - this.r) * this.T)) / ((this.s0 * this.sig) * Math.sqrt(this.T));
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
        this.d1 = (1.0d / (this.sig * Math.sqrt(this.T))) * (Math.log(this.s0 / this.K) + ((this.b + (0.5d * this.sig * this.sig)) * this.T));
        this.d2 = this.d1 - (this.sig * Math.sqrt(this.T));
    }

    /* access modifiers changed from: protected */
    public double putCharm(double d) {
        return ((-Math.exp((this.b - this.r) * this.T)) * ((NN.N(this.d1) * ((this.b / (this.sig * Math.sqrt(this.T))) - (this.d2 / (2.0d * this.T)))) - ((this.b - this.r) * NN.C(-this.d1)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double putDelta(double d) {
        return Math.exp((this.b - this.r) * this.T) * (NN.C(this.d1) - 1.0d);
    }

    /* access modifiers changed from: protected */
    public double putElasticity(double d) {
        return (putDelta(d) * this.s0) / putPrice();
    }

    /* access modifiers changed from: protected */
    public double putGamma(double d) {
        return gamma(d);
    }

    /* access modifiers changed from: protected */
    public double putPrice() {
        prepare();
        if (this.s0 == this.K && this.T == 0.0d) {
            return 0.0d;
        }
        return ((-this.s0) * Math.exp((this.b - this.r) * this.T) * NN.C(-this.d1)) + (this.K * Math.exp((-this.r) * this.T) * NN.C(-this.d2));
    }

    /* access modifiers changed from: protected */
    public double putRho(double d) {
        return ((((-this.T) * this.K) * Math.exp((-this.r) * this.T)) * NN.C(-this.d2)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double putTheta(double d) {
        return (((((((-this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.N(this.d1)) * this.sig) / (2.0d * Math.sqrt(this.T))) + ((((this.b - this.r) * this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.C(-this.d1))) + (((this.r * this.K) * Math.exp((-this.r) * this.T)) * NN.C(-this.d2))) / 365.0d;
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

    /* access modifiers changed from: protected */
    public double speed(double d) {
        return ((-gamma(d)) * (1.0d + (this.d1 / (this.sig * Math.sqrt(this.T))))) / this.s0;
    }

    /* access modifiers changed from: protected */
    public double ultima(double d) {
        return ((volga(d) * ((((this.d1 * this.d2) - (this.d1 / this.d2)) - (this.d2 / this.d1)) - 1.0d)) / this.sig) / 10000.0d;
    }

    /* access modifiers changed from: protected */
    public double vanna(double d) {
        return (1.0d / (100.0d * this.sig)) * NN.N(this.d1) * (-Math.exp((this.b - this.r) * this.T)) * this.d2;
    }

    /* access modifiers changed from: protected */
    public double vega(double d) {
        return (((this.s0 * Math.exp((this.b - this.r) * this.T)) * Math.sqrt(this.T)) * NN.N(this.d1)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double veta(double d) {
        return (vega(d) * (((this.r - this.b) + ((this.b * this.d1) / (this.sig * Math.sqrt(this.T)))) - ((1.0d + (this.d1 * this.d2)) / (2.0d * this.T)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double volga(double d) {
        return (((vega(d) * this.d1) * this.d2) / this.sig) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double zomma(double d) {
        return gamma(d) * (((this.d1 * this.d2) - 1.0d) / this.sig);
    }
}
