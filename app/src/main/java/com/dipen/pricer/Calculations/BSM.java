package com.dipen.pricer.Calculations;

public class BSM extends com.dipen.pricer.Calculations.OneAsset {
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};
    functionArray[] callFunctions = {new functionArray() {
        public double function(double x) {
            return BSM.this.callPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.vega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callElasticity(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.vanna(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.volga(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.callCharm(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.veta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.speed(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.color(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.zomma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.ultima(x);
        }
    }};
    functionArray[] putFunctions = {new functionArray() {
        public double function(double x) {
            return BSM.this.putPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.vega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putElasticity(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.vanna(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.volga(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.putCharm(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.veta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.speed(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.color(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.zomma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return BSM.this.ultima(x);
        }
    }};

    public interface functionArray {
        double function(double d);
    }

    public BSM(double[] x) {
        reconstruct(x);
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
    public double putPrice() {
        prepare();
        if (this.s0 == this.K && this.T == 0.0d) {
            return 0.0d;
        }
        return ((-this.s0) * Math.exp((this.b - this.r) * this.T) * NN.C(-this.d1)) + (this.K * Math.exp((-this.r) * this.T) * NN.C(-this.d2));
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
        this.d1 = (1.0d / (this.sig * Math.sqrt(this.T))) * (Math.log(this.s0 / this.K) + ((this.b + (0.5d * this.sig * this.sig)) * this.T));
        this.d2 = this.d1 - (this.sig * Math.sqrt(this.T));
    }

    public void reconstruct(double[] x) {
        this.s0 = x[0];
        this.K = x[1];
        this.T = x[2];
        this.r = x[3] / 100.0d;
        this.q = x[4] / 100.0d;
        this.sig = x[5] / 100.0d;
        prepare();
    }

    /* access modifiers changed from: protected */
    public double callDelta(double x) {
        return Math.exp((this.b - this.r) * this.T) * NN.C(this.d1);
    }

    /* access modifiers changed from: protected */
    public double putDelta(double x) {
        return Math.exp((this.b - this.r) * this.T) * (NN.C(this.d1) - 1.0d);
    }

    /* access modifiers changed from: protected */
    public double vanna(double x) {
        return (1.0d / (100.0d * this.sig)) * NN.N(this.d1) * (-Math.exp((this.b - this.r) * this.T)) * this.d2;
    }

    /* access modifiers changed from: protected */
    public double callCharm(double x) {
        return ((-Math.exp((this.b - this.r) * this.T)) * ((NN.N(this.d1) * ((this.b / (this.sig * Math.sqrt(this.T))) - (this.d2 / (2.0d * this.T)))) + ((this.b - this.r) * NN.C(this.d1)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double putCharm(double x) {
        return ((-Math.exp((this.b - this.r) * this.T)) * ((NN.N(this.d1) * ((this.b / (this.sig * Math.sqrt(this.T))) - (this.d2 / (2.0d * this.T)))) - ((this.b - this.r) * NN.C(-this.d1)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double gamma(double x) {
        return (NN.N(this.d1) * Math.exp((this.b - this.r) * this.T)) / ((this.s0 * this.sig) * Math.sqrt(this.T));
    }

    /* access modifiers changed from: protected */
    public double callGamma(double x) {
        return gamma(x);
    }

    /* access modifiers changed from: protected */
    public double putGamma(double x) {
        return gamma(x);
    }

    /* access modifiers changed from: protected */
    public double zomma(double x) {
        return gamma(x) * (((this.d1 * this.d2) - 1.0d) / this.sig);
    }

    /* access modifiers changed from: protected */
    public double speed(double x) {
        return ((-gamma(x)) * (1.0d + (this.d1 / (this.sig * Math.sqrt(this.T))))) / this.s0;
    }

    /* access modifiers changed from: protected */
    public double color(double x) {
        return (gamma(x) * (((this.r - this.b) + ((this.b * this.d1) / (this.sig * Math.sqrt(this.T)))) + ((1.0d - (this.d1 * this.d2)) / (2.0d * this.T)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double vega(double x) {
        return (((this.s0 * Math.exp((this.b - this.r) * this.T)) * Math.sqrt(this.T)) * NN.N(this.d1)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double volga(double x) {
        return (((vega(x) * this.d1) * this.d2) / this.sig) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double ultima(double x) {
        return ((volga(x) * ((((this.d1 * this.d2) - (this.d1 / this.d2)) - (this.d2 / this.d1)) - 1.0d)) / this.sig) / 10000.0d;
    }

    /* access modifiers changed from: protected */
    public double veta(double x) {
        return (vega(x) * (((this.r - this.b) + ((this.b * this.d1) / (this.sig * Math.sqrt(this.T)))) - ((1.0d + (this.d1 * this.d2)) / (2.0d * this.T)))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double callTheta(double x) {
        return (((((((-this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.N(this.d1)) * this.sig) / (2.0d * Math.sqrt(this.T))) - ((((this.b - this.r) * this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.C(this.d1))) - (((this.r * this.K) * Math.exp((-this.r) * this.T)) * NN.C(this.d2))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double putTheta(double x) {
        return (((((((-this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.N(this.d1)) * this.sig) / (2.0d * Math.sqrt(this.T))) + ((((this.b - this.r) * this.s0) * Math.exp((this.b - this.r) * this.T)) * NN.C(-this.d1))) + (((this.r * this.K) * Math.exp((-this.r) * this.T)) * NN.C(-this.d2))) / 365.0d;
    }

    /* access modifiers changed from: protected */
    public double callRho(double x) {
        return (((this.T * this.K) * Math.exp((-this.r) * this.T)) * NN.C(this.d2)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double putRho(double x) {
        return ((((-this.T) * this.K) * Math.exp((-this.r) * this.T)) * NN.C(-this.d2)) / 100.0d;
    }

    /* access modifiers changed from: protected */
    public double callElasticity(double x) {
        return (callDelta(x) * this.s0) / callPrice();
    }

    /* access modifiers changed from: protected */
    public double putElasticity(double x) {
        return (putDelta(x) * this.s0) / putPrice();
    }
}
