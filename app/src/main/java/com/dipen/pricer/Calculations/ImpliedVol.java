package com.dipen.pricer.Calculations;

import com.dipen.pricer.Calculations.OneAsset;

public class ImpliedVol extends OneAsset {
    public OneAsset.functionArray[][] allFunctions = {this.euFunctions, this.amFunctions};
    public OneAsset.functionArray[] amFunctions = {new OneAsset.functionArray() {
        public double function(double d) {
            return ImpliedVol.this.callAm();
        }
    }, new OneAsset.functionArray() {
        public double function(double d) {
            return ImpliedVol.this.putAm();
        }
    }};
    public OneAsset.functionArray[] euFunctions = {new OneAsset.functionArray() {
        public double function(double d) {
            return ImpliedVol.this.callEu();
        }
    }, new OneAsset.functionArray() {
        public double function(double d) {
            return ImpliedVol.this.putEu();
        }
    }};
    protected double price;

    public ImpliedVol(double[] dArr) {
        reconstruct(dArr);
    }

    /* access modifiers changed from: protected */
    public double callAm() {
        prepare();
        double d = 0.05d;
        double d2 = 0.95d;
        double d3 = 1.0d;
        double d4 = 1.0d;
        double d5 = 0.0d;
        while (d3 > 1.0E-6d && d4 < 1000.0d) {
            double d6 = this.s0;
            double d7 = this.K;
            double d8 = this.T;
            double d9 = this.r;
            double d10 = this.q;
            double d11 = this.s0;
            double d12 = this.K;
            double d13 = this.T;
            double d14 = this.r;
            double d15 = this.q;
            American american = new American(new double[]{d6, d7, d8, 100.0d * d9, 100.0d * d10, 100.0d * d});
            American american2 = new American(new double[]{d11, d12, d13, 100.0d * d14, 100.0d * d15, 100.0d * d2});
            double callPrice = american.callPrice();
            d5 = (((this.price - callPrice) * (d2 - d)) / (american2.callPrice() - callPrice)) + d;
            double callPrice2 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * d5}).callPrice();
            if (callPrice2 < this.price) {
                d = d5;
            } else {
                d2 = d5;
            }
            d3 = Math.abs(callPrice2 - this.price);
            d4 += 1.0d;
        }
        return 100.0d * d5;
    }

    /* access modifiers changed from: protected */
    public double callEu() {
        prepare();
        double d = 1.0d;
        double d2 = 1.0d;
        double sqrt = this.b == 0.0d ? Math.sqrt((Math.abs(Math.log(this.s0 / this.K)) * 2.0d) / this.T) : Math.sqrt((Math.abs(Math.log(this.s0 / this.K) + (this.r * this.T)) * 2.0d) / this.T);
        while (d > 1.0E-6d && d2 < 1000.0d) {
            BSM bsm = new BSM(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * sqrt});
            sqrt -= (bsm.callPrice() - this.price) / (bsm.vega(0.0d) * 100.0d);
            d = Math.abs(bsm.callPrice() - this.price);
            d2 = 1.0d + d2;
        }
        return 100.0d * sqrt;
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
    }

    /* access modifiers changed from: protected */
    public double putAm() {
        prepare();
        double d = 0.05d;
        double d2 = 0.95d;
        double d3 = 1.0d;
        double d4 = 1.0d;
        double d5 = 0.0d;
        while (d3 > 1.0E-6d && d4 < 1000.0d) {
            double d6 = this.s0;
            double d7 = this.K;
            double d8 = this.T;
            double d9 = this.r;
            double d10 = this.q;
            double d11 = this.s0;
            double d12 = this.K;
            double d13 = this.T;
            double d14 = this.r;
            double d15 = this.q;
            American american = new American(new double[]{d6, d7, d8, 100.0d * d9, 100.0d * d10, 100.0d * d});
            American american2 = new American(new double[]{d11, d12, d13, 100.0d * d14, 100.0d * d15, 100.0d * d2});
            double putPrice = american.putPrice();
            d5 = (((this.price - putPrice) * (d2 - d)) / (american2.putPrice() - putPrice)) + d;
            double putPrice2 = new American(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * d5}).putPrice();
            if (putPrice2 < this.price) {
                d = d5;
            } else {
                d2 = d5;
            }
            d3 = Math.abs(putPrice2 - this.price);
            d4 += 1.0d;
        }
        return 100.0d * d5;
    }

    /* access modifiers changed from: protected */
    public double putEu() {
        prepare();
        double d = 1.0d;
        double d2 = 1.0d;
        double sqrt = this.b == 0.0d ? Math.sqrt((Math.abs(Math.log(this.s0 / this.K)) * 2.0d) / this.T) : Math.sqrt((Math.abs(Math.log(this.s0 / this.K) + (this.r * this.T)) * 2.0d) / this.T);
        while (d > 1.0E-6d && d2 < 1000.0d) {
            BSM bsm = new BSM(new double[]{this.s0, this.K, this.T, this.r * 100.0d, this.q * 100.0d, 100.0d * sqrt});
            sqrt -= (bsm.putPrice() - this.price) / (bsm.vega(0.0d) * 100.0d);
            d = Math.abs(bsm.putPrice() - this.price);
            d2 = 1.0d + d2;
        }
        return 100.0d * sqrt;
    }

    public void reconstruct(double[] dArr) {
        this.s0 = dArr[0];                      // Spot Price
        this.K = dArr[1];                       // Strike Price
        this.T = dArr[2];                       // Maturity(Year)
        this.r = dArr[3] / 100.0d;              // Interest Rate
        this.q = dArr[4] / 100.0d;              // Annual Yield
        this.price = dArr[5];                   // Current Price
        prepare();
    }
}
