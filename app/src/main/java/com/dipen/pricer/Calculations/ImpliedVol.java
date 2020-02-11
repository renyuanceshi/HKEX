package com.dipen.pricer.Calculations;

public class ImpliedVol extends OneAsset {
    public functionArray[][] allFunctions = {this.euFunctions, this.amFunctions};
    public functionArray[] amFunctions = {new functionArray() {
        public double function(double x) {
            return ImpliedVol.this.callAm();
        }
    }, new functionArray() {
        public double function(double x) {
            return ImpliedVol.this.putAm();
        }
    }};
    public functionArray[] euFunctions = {new functionArray() {
        public double function(double x) {
            return ImpliedVol.this.callEu();
        }
    }, new functionArray() {
        public double function(double x) {
            return ImpliedVol.this.putEu();
        }
    }};
    protected double price;

    public ImpliedVol(double[] x) {
        reconstruct(x);
    }

    protected void prepare() {
        this.b = this.r - this.q;
    }

    public void reconstruct(double[] x) {
        this.s0 = x[0];
        this.K = x[1];
        this.T = x[2];
        this.r = x[3] / 100.0d;
        this.q = x[4] / 100.0d;
        this.price = x[5];
        prepare();
    }

    protected double callEu() {
        double initial;
        prepare();
        if (this.b == 0.0d) {
            initial = Math.sqrt((Math.abs(Math.log(this.s0 / this.K)) * 2.0d) / this.T);
        } else {
            initial = Math.sqrt((Math.abs(Math.log(this.s0 / this.K) + (this.r * this.T)) * 2.0d) / this.T);
        }
        double error = 1.0d;
        double iterations = 1.0d;
        while (error > 1.0E-6d && iterations < 1000.0d) {
            BSM bsm = new BSM(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * initial});
            initial -= (bsm.callPrice() - this.price) / (bsm.vega(0.0d) * 100.0d);
            error = Math.abs(bsm.callPrice() - this.price);
            iterations += 1.0d;
        }
        return 100.0d * initial;
    }

    protected double putEu() {
        double initial;
        prepare();
        if (this.b == 0.0d) {
            initial = Math.sqrt((Math.abs(Math.log(this.s0 / this.K)) * 2.0d) / this.T);
        } else {
            initial = Math.sqrt((Math.abs(Math.log(this.s0 / this.K) + (this.r * this.T)) * 2.0d) / this.T);
        }
        double error = 1.0d;
        double iterations = 1.0d;
        while (error > 1.0E-6d && iterations < 1000.0d) {
            BSM bsm = new BSM(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * initial});
            initial -= (bsm.putPrice() - this.price) / (bsm.vega(0.0d) * 100.0d);
            error = Math.abs(bsm.putPrice() - this.price);
            iterations += 1.0d;
        }
        return 100.0d * initial;
    }

    protected double callAm() {
        prepare();
        double low = 0.05d;
        double high = 0.95d;
        double error = 1.0d;
        double iterations = 1.0d;
        double next = 0.0d;
        while (error > 1.0E-6d && iterations < 1000.0d) {
            double[] p2 = {this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * high};
            American am1 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * low});
            American am2 = new American(p2);
            double price1 = am1.callPrice();
            next = low + (((this.price - price1) * (high - low)) / (am2.callPrice() - price1));
            double price3 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * next}).callPrice();
            if (price3 < this.price) {
                low = next;
            } else {
                high = next;
            }
            error = Math.abs(price3 - this.price);
            iterations += 1.0d;
        }
        return 100.0d * next;
    }

    protected double putAm() {
        prepare();
        double low = 0.05d;
        double high = 0.95d;
        double error = 1.0d;
        double iterations = 1.0d;
        double next = 0.0d;
        while (error > 1.0E-6d && iterations < 1000.0d) {
            double[] p2 = {this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * high};
            American am1 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * low});
            American am2 = new American(p2);
            double price1 = am1.putPrice();
            next = low + (((this.price - price1) * (high - low)) / (am2.putPrice() - price1));
            double price3 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * next}).putPrice();
            if (price3 < this.price) {
                low = next;
            } else {
                high = next;
            }
            error = Math.abs(price3 - this.price);
            iterations += 1.0d;
        }
        return 100.0d * next;
    }
}
