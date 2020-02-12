package com.dipen.pricer.Calculations;

import com.dipen.pricer.Calculations.OneAsset;

public class American extends OneAsset {
    protected double V;
    protected double alpha1;
    protected double alpha2;
    protected double b0;
    protected double beta;
    protected double binf;
    protected double e1;
    protected double e2;
    protected double e3;
    protected double e4;
    protected double f1;
    protected double f2;
    protected double f3;
    protected double f4;
    protected double h1;
    protected double h2;
    protected double i1;
    protected double i2;
    public OneAsset.functionArray[] perpFunctions = {new OneAsset.functionArray() {
        public double function(double d) {
            return American.this.callPerpetualPrice();
        }
    }, new OneAsset.functionArray() {
        public double function(double d) {
            return American.this.putPerpetualPrice();
        }
    }};
    protected double t1;

    public American(double[] dArr) {
        reconstruct(dArr);
    }

    /* access modifiers changed from: protected */
    public double callPerpetualPrice() {
        prepare();
        if (this.q == 0.0d) {
            return this.s0;
        }
        double sqrt = (0.5d - (this.b / this.V)) + Math.sqrt((((this.b / this.V) - 0.5d) * ((this.b / this.V) - 0.5d)) + ((2.0d * this.r) / this.V));
        return Math.pow((((sqrt - 1.0d) / sqrt) * this.s0) / this.K, sqrt) * (this.K / (sqrt - 1.0d));
    }

    /* access modifiers changed from: protected */
    public double callPrice() {
        prepare();
        if (this.T <= 0.0d) {
            return Math.max(this.s0 - this.K, 0.0d);
        }
        if (this.b >= this.r) {
            double sqrt = (1.0d / (this.sig * Math.sqrt(this.T))) * (Math.log(this.s0 / this.K) + ((this.b + (0.5d * this.sig * this.sig)) * this.T));
            return ((this.s0 * Math.exp((this.b - this.r) * this.T)) * NN.C(sqrt)) - (NN.C(sqrt - (this.sig * Math.sqrt(this.T))) * (this.K * Math.exp((-this.r) * this.T)));
        } else if (this.s0 >= this.i2) {
            return this.s0 - this.K;
        } else {
            double d = this.alpha2;
            double pow = Math.pow(this.s0, this.beta);
            double d2 = this.alpha2;
            double phi = phi(this.s0, this.t1, this.beta, this.i2, this.i2);
            double phi2 = phi(this.s0, this.t1, 1.0d, this.i2, this.i2);
            double phi3 = phi(this.s0, this.t1, 1.0d, this.i1, this.i2);
            double d3 = this.K;
            double phi4 = phi(this.s0, this.t1, 0.0d, this.i2, this.i2);
            double d4 = this.K;
            double phi5 = phi(this.s0, this.t1, 0.0d, this.i1, this.i2);
            double d5 = this.alpha1;
            double phi6 = phi(this.s0, this.t1, this.beta, this.i1, this.i2);
            double d6 = this.alpha1;
            double psi = psi(this.s0, this.T, this.beta, this.i1, this.i2, this.i1, this.t1);
            double psi2 = psi(this.s0, this.T, 1.0d, this.i1, this.i2, this.i1, this.t1);
            double psi3 = psi(this.s0, this.T, 1.0d, this.K, this.i2, this.i1, this.t1);
            double d7 = this.K;
            double psi4 = psi(this.s0, this.T, 0.0d, this.i1, this.i2, this.i1, this.t1);
            return (psi(this.s0, this.T, 0.0d, this.K, this.i2, this.i1, this.t1) * this.K) + (((((((((((d * pow) - (phi * d2)) + phi2) - phi3) - (phi4 * d3)) + (phi5 * d4)) + (phi6 * d5)) - (psi * d6)) + psi2) - psi3) - (psi4 * d7));
        }
    }

    /* access modifiers changed from: protected */
    public double phi(double d, double d2, double d3, double d4, double d5) {
        double sqrt = 1.0d / (this.sig * Math.sqrt(d2));
        double log = Math.log(d / d4);
        double d6 = this.b;
        double d7 = this.V;
        double sqrt2 = 1.0d / (this.sig * Math.sqrt(d2));
        double log2 = Math.log((d5 * d5) / (d * d4));
        double d8 = this.b;
        double d9 = this.V;
        double d10 = -this.r;
        double d11 = this.b;
        double d12 = this.V;
        double d13 = (2.0d * this.b) / this.V;
        return (NN.C(-(sqrt * (log + ((d6 + (d7 * (d3 - 0.5d))) * d2)))) - (Math.pow(d5 / d, ((2.0d * d3) + d13) - 1.0d) * NN.C(-((((((d3 - 0.5d) * d9) + d8) * d2) + log2) * sqrt2)))) * Math.exp((d10 + (d11 * d3) + (0.5d * d3 * (d3 - 1.0d) * d12)) * d2) * Math.pow(d, d3);
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
        this.V = this.sig * this.sig;
        this.beta = (0.5d - (this.b / this.V)) + Math.sqrt((((this.b / this.V) - 0.5d) * ((this.b / this.V) - 0.5d)) + ((this.r * 2.0d) / this.V));
        this.t1 = (Math.sqrt(5.0d) - 1.0d) * 0.5d * this.T;
        this.b0 = Math.max(this.K, (this.K * this.r) / (this.r - this.b));
        this.binf = (this.K * this.beta) / (this.beta - 1.0d);
        this.h1 = (((-((this.b * this.t1) + ((this.sig * 2.0d) * Math.sqrt(this.t1)))) * this.K) * this.K) / ((this.binf - this.b0) * this.b0);
        this.h2 = (((-((this.b * this.T) + ((this.sig * 2.0d) * Math.sqrt(this.T)))) * this.K) * this.K) / ((this.binf - this.b0) * this.b0);
        this.i1 = this.b0 + ((this.binf - this.b0) * (1.0d - Math.exp(this.h1)));
        this.i2 = this.b0 + ((this.binf - this.b0) * (1.0d - Math.exp(this.h2)));
        this.alpha1 = (this.i1 - this.K) * Math.pow(this.i1, -this.beta);
        this.alpha2 = (this.i2 - this.K) * Math.pow(this.i2, -this.beta);
    }

    /* access modifiers changed from: protected */
    public double psi(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        double sqrt = 1.0d / (this.sig * Math.sqrt(d7));
        double log = Math.log(d / d6);
        double d8 = this.b;
        double d9 = this.V;
        double log2 = Math.log((d5 * d5) / (d * d6));
        double d10 = this.b;
        double d11 = this.V;
        double log3 = Math.log(d / d6);
        double d12 = this.b;
        double d13 = this.V;
        double log4 = Math.log((d5 * d5) / (d * d6));
        double d14 = this.b;
        double d15 = this.V;
        double log5 = Math.log(d / d4);
        double d16 = this.b;
        double d17 = this.V;
        double log6 = Math.log((d5 * d5) / (d * d4));
        double d18 = this.b;
        double d19 = this.V;
        double log7 = Math.log((d6 * d6) / (d * d4));
        double d20 = this.b;
        double d21 = this.V;
        double log8 = Math.log(((d * d6) * d6) / ((d5 * d5) * d4));
        double d22 = this.b;
        double d23 = this.V;
        double d24 = -this.r;
        double d25 = this.b;
        double d26 = this.V;
        double d27 = (((2.0d * this.b) / this.V) + (2.0d * d3)) - 1.0d;
        double sqrt2 = Math.sqrt(d7 / d2);
        double exp = Math.exp((d24 + (d25 * d3) + (0.5d * d3 * (d3 - 1.0d) * d26)) * d2);
        double pow = Math.pow(d, d3);
        return ((NN.M(-((log4 - ((((d3 - 0.5d) * d15) + d14) * d7)) * (1.0d / (this.sig * Math.sqrt(d7)))), -((((((d3 - 0.5d) * d23) + d22) * d2) + log8) * (1.0d / (this.sig * Math.sqrt(d2)))), -sqrt2) * Math.pow(d6 / d5, d27)) + ((NN.M(-(sqrt * (log + ((d8 + (d9 * (d3 - 0.5d))) * d7))), -((((((d3 - 0.5d) * d17) + d16) * d2) + log5) * (1.0d / (this.sig * Math.sqrt(d2)))), sqrt2) - (NN.M(-((((((d3 - 0.5d) * d11) + d10) * d7) + log2) * (1.0d / (this.sig * Math.sqrt(d7)))), -((((((d3 - 0.5d) * d19) + d18) * d2) + log6) * (1.0d / (this.sig * Math.sqrt(d2)))), sqrt2) * Math.pow(d5 / d, d27))) - (Math.pow(d6 / d, d27) * NN.M(-((log3 - ((((d3 - 0.5d) * d13) + d12) * d7)) * (1.0d / (this.sig * Math.sqrt(d7)))), -((((((d3 - 0.5d) * d21) + d20) * d2) + log7) * (1.0d / (this.sig * Math.sqrt(d2)))), -sqrt2)))) * exp * pow;
    }

    /* access modifiers changed from: protected */
    public double putPerpetualPrice() {
        prepare();
        double sqrt = (0.5d - (this.b / this.V)) - Math.sqrt((((this.b / this.V) - 0.5d) * ((this.b / this.V) - 0.5d)) + ((2.0d * this.r) / this.V));
        return Math.pow((((sqrt - 1.0d) / sqrt) * this.s0) / this.K, sqrt) * (this.K / (1.0d - sqrt));
    }

    /* access modifiers changed from: protected */
    public double putPrice() {
        if (this.T <= 0.0d) {
            return Math.max(this.K - this.s0, 0.0d);
        }
        prepare();
        return new American(new double[]{this.K, this.s0, this.T, this.q * 100.0d, this.r * 100.0d, this.sig * 100.0d}).callPrice();
    }

    public void reconstruct(double[] dArr) {
        this.s0 = dArr[0];                      // Spot Price
        this.K = dArr[1];                       // Strike Price
        this.T = dArr[2];                       // Maturity(Year)
        this.r = dArr[3] / 100.0d;              // Interest Rate
        this.q = dArr[4] / 100.0d;              // Annual Yield
        this.sig = dArr[5] / 100.0d;            // Volatility
        prepare();
    }
}
