package com.dipen.pricer.Calculations;

public class OneAsset extends AbstractAsset {

    protected double K;

    protected double T;
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};

    protected double b;
    functionArray[] callFunctions = {new functionArray() {
        public double function(double x) {
            return OneAsset.this.callPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callVega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.callElasticity(x);
        }
    }};

    protected double d1;

    protected double d2;
    functionArray[] putFunctions = {new functionArray() {
        public double function(double x) {
            return OneAsset.this.putPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putVega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return OneAsset.this.putElasticity(x);
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

    OneAsset(double[] x) {
        this.s0 = x[0];
        this.K = x[1];
        this.T = x[2];
        this.r = x[3] / 100.0d;
        this.q = x[4] / 100.0d;
        this.sig = x[5] / 100.0d;
        prepare();
    }

    protected void prepare() {
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

    protected double callPrice() {
        return 0.0d;
    }

    protected double putPrice() {
        return 0.0d;
    }

    protected double callDelta(double ds) {
        double temp = this.s0;
        this.s0 += ds;
        double price1 = callPrice();
        this.s0 -= 2.0d * ds;
        double price2 = callPrice();
        this.s0 = temp;
        return (price1 - price2) / (2.0d * ds);
    }

    protected double putDelta(double ds) {
        double temp = this.s0;
        this.s0 += ds;
        double price1 = putPrice();
        this.s0 -= 2.0d * ds;
        double price2 = putPrice();
        this.s0 = temp;
        return (price1 - price2) / (2.0d * ds);
    }

    protected double callGamma(double ds) {
        double temp = this.s0;
        this.s0 += ds;
        double price1 = callDelta(ds);
        this.s0 -= 2.0d * ds;
        double price2 = callDelta(ds);
        this.s0 = temp;
        return (price1 - price2) / (2.0d * ds);
    }

    protected double putGamma(double ds) {
        double temp = this.s0;
        this.s0 += ds;
        double price1 = putDelta(ds);
        this.s0 -= 2.0d * ds;
        double price2 = putDelta(ds);
        this.s0 = temp;
        return (price1 - price2) / (2.0d * ds);
    }

    protected double callVega(double dv) {
        double temp = this.sig;
        this.sig += dv;
        double price1 = callPrice();
        this.sig -= 2.0d * dv;
        double price2 = callPrice();
        this.sig = temp;
        return (price1 - price2) / (200.0d * dv);
    }

    protected double putVega(double dv) {
        double temp = this.sig;
        this.sig += dv;
        double price1 = putPrice();
        this.sig -= 2.0d * dv;
        double price2 = putPrice();
        this.sig = temp;
        return (price1 - price2) / (200.0d * dv);
    }

    protected double callTheta(double dt) {
        double temp = this.T;
        this.T += dt;
        double price1 = callPrice();
        this.T -= 2.0d * dt;
        double price2 = callPrice();
        this.T = temp;
        return (-(price1 - price2)) / (730.0d * dt);
    }

    protected double putTheta(double dt) {
        double temp = this.T;
        this.T += dt;
        double price1 = putPrice();
        this.T -= 2.0d * dt;
        double price2 = putPrice();
        this.T = temp;
        return (-(price1 - price2)) / (730.0d * dt);
    }

    public double callRho(double dr) {
        double temp = this.r;
        this.r += dr;
        double price1 = callPrice();
        this.r -= 2.0d * dr;
        double price2 = callPrice();
        this.r = temp;
        return (price1 - price2) / (200.0d * dr);
    }

    protected double putRho(double dr) {
        double temp = this.r;
        this.r += dr;
        double price1 = putPrice();
        this.r -= 2.0d * dr;
        double price2 = putPrice();
        this.r = temp;
        return (price1 - price2) / (200.0d * dr);
    }

    protected double callElasticity(double ds) {
        return (callDelta(ds) * this.s0) / callPrice();
    }

    protected double putElasticity(double ds) {
        return (putDelta(ds) * this.s0) / putPrice();
    }
}
