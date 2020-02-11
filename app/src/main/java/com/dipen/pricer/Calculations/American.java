package com.dipen.pricer.Calculations;

public class American extends com.dipen.pricer.Calculations.OneAsset {

    /* renamed from: V */
    protected double f5V;
    protected double alpha1;
    protected double alpha2;

    /* renamed from: b0 */
    protected double f6b0;
    protected double beta;
    protected double binf;

    /* renamed from: e1 */
    protected double f7e1;

    /* renamed from: e2 */
    protected double f8e2;

    /* renamed from: e3 */
    protected double f9e3;

    /* renamed from: e4 */
    protected double f10e4;

    /* renamed from: f1 */
    protected double f11f1;

    /* renamed from: f2 */
    protected double f12f2;

    /* renamed from: f3 */
    protected double f13f3;

    /* renamed from: f4 */
    protected double f14f4;

    /* renamed from: h1 */
    protected double f15h1;

    /* renamed from: h2 */
    protected double f16h2;

    /* renamed from: i1 */
    protected double f17i1;

    /* renamed from: i2 */
    protected double f18i2;
    public functionArray[] perpFunctions = {new functionArray() {
        public double function(double x) {
            return American.this.callPerpetualPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return American.this.putPerpetualPrice();
        }
    }};

    /* renamed from: t1 */
    protected double f19t1;

    public American(double[] x) {
        reconstruct(x);
    }

    /* access modifiers changed from: protected */
    public double phi(double s, double T1, double g, double h, double i) {
        return Math.exp(((-this.r) + (this.b * g) + (0.5d * g * (g - 1.0d) * this.f5V)) * T1) * Math.pow(s, g) * (NN.C(-((1.0d / (this.sig * Math.sqrt(T1))) * (Math.log(s / h) + ((this.b + ((g - 0.5d) * this.f5V)) * T1)))) - (Math.pow(i / s, (((2.0d * this.b) / this.f5V) + (2.0d * g)) - 1.0d) * NN.C(-((1.0d / (this.sig * Math.sqrt(T1))) * (Math.log((i * i) / (s * h)) + ((this.b + ((g - 0.5d) * this.f5V)) * T1))))));
    }

    /* access modifiers changed from: protected */
    public double psi(double s, double T1, double g, double h, double i2, double i1, double t1) {
        double e1 = (1.0d / (this.sig * Math.sqrt(t1))) * (Math.log(s / i1) + ((this.b + ((g - 0.5d) * this.f5V)) * t1));
        double e2 = (1.0d / (this.sig * Math.sqrt(t1))) * (Math.log((i2 * i2) / (s * i1)) + ((this.b + ((g - 0.5d) * this.f5V)) * t1));
        double e3 = (1.0d / (this.sig * Math.sqrt(t1))) * (Math.log(s / i1) - ((this.b + ((g - 0.5d) * this.f5V)) * t1));
        double e4 = (1.0d / (this.sig * Math.sqrt(t1))) * (Math.log((i2 * i2) / (s * i1)) - ((this.b + ((g - 0.5d) * this.f5V)) * t1));
        double f1 = (1.0d / (this.sig * Math.sqrt(T1))) * (Math.log(s / h) + ((this.b + ((g - 0.5d) * this.f5V)) * T1));
        double f2 = (1.0d / (this.sig * Math.sqrt(T1))) * (Math.log((i2 * i2) / (s * h)) + ((this.b + ((g - 0.5d) * this.f5V)) * T1));
        double f3 = (1.0d / (this.sig * Math.sqrt(T1))) * (Math.log((i1 * i1) / (s * h)) + ((this.b + ((g - 0.5d) * this.f5V)) * T1));
        double f4 = (1.0d / (this.sig * Math.sqrt(T1))) * (Math.log(((s * i1) * i1) / ((i2 * i2) * h)) + ((this.b + ((g - 0.5d) * this.f5V)) * T1));
        double lambda = (-this.r) + (this.b * g) + (0.5d * g * (g - 1.0d) * this.f5V);
        double kappa = (((2.0d * this.b) / this.f5V) + (2.0d * g)) - 1.0d;
        double rho = Math.sqrt(t1 / T1);
        return (((NN.M(-e1, -f1, rho) - (NN.M(-e2, -f2, rho) * Math.pow(i2 / s, kappa))) - (Math.pow(i1 / s, kappa) * NN.M(-e3, -f3, -rho))) + (Math.pow(i1 / i2, kappa) * NN.M(-e4, -f4, -rho))) * Math.exp(lambda * T1) * Math.pow(s, g);
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.b = this.r - this.q;
        this.f5V = this.sig * this.sig;
        this.beta = (0.5d - (this.b / this.f5V)) + Math.sqrt((((this.b / this.f5V) - 0.5d) * ((this.b / this.f5V) - 0.5d)) + ((this.r * 2.0d) / this.f5V));
        this.f19t1 = (Math.sqrt(5.0d) - 1.0d) * 0.5d * this.T;
        this.f6b0 = Math.max(this.K, (this.K * this.r) / (this.r - this.b));
        this.binf = (this.K * this.beta) / (this.beta - 1.0d);
        this.f15h1 = (((-((this.b * this.f19t1) + ((this.sig * 2.0d) * Math.sqrt(this.f19t1)))) * this.K) * this.K) / ((this.binf - this.f6b0) * this.f6b0);
        this.f16h2 = (((-((this.b * this.T) + ((this.sig * 2.0d) * Math.sqrt(this.T)))) * this.K) * this.K) / ((this.binf - this.f6b0) * this.f6b0);
        this.f17i1 = this.f6b0 + ((this.binf - this.f6b0) * (1.0d - Math.exp(this.f15h1)));
        this.f18i2 = this.f6b0 + ((this.binf - this.f6b0) * (1.0d - Math.exp(this.f16h2)));
        this.alpha1 = (this.f17i1 - this.K) * Math.pow(this.f17i1, -this.beta);
        this.alpha2 = (this.f18i2 - this.K) * Math.pow(this.f18i2, -this.beta);
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
    public double callPrice() {
        prepare();
        if (this.T <= 0.0d) {
            return Math.max(this.s0 - this.K, 0.0d);
        }
        if (this.b >= this.r) {
            double d1 = (1.0d / (this.sig * Math.sqrt(this.T))) * (Math.log(this.s0 / this.K) + ((this.b + (0.5d * this.sig * this.sig)) * this.T));
            return ((this.s0 * Math.exp((this.b - this.r) * this.T)) * NN.C(d1)) - ((this.K * Math.exp((-this.r) * this.T)) * NN.C(d1 - (this.sig * Math.sqrt(this.T))));
        } else if (this.s0 >= this.f18i2) {
            return this.s0 - this.K;
        } else {
            return (psi(this.s0, this.T, 0.0d, this.K, this.f18i2, this.f17i1, this.f19t1) * this.K) + (((((((((((this.alpha2 * Math.pow(this.s0, this.beta)) - (phi(this.s0, this.f19t1, this.beta, this.f18i2, this.f18i2) * this.alpha2)) + phi(this.s0, this.f19t1, 1.0d, this.f18i2, this.f18i2)) - phi(this.s0, this.f19t1, 1.0d, this.f17i1, this.f18i2)) - (phi(this.s0, this.f19t1, 0.0d, this.f18i2, this.f18i2) * this.K)) + (phi(this.s0, this.f19t1, 0.0d, this.f17i1, this.f18i2) * this.K)) + (phi(this.s0, this.f19t1, this.beta, this.f17i1, this.f18i2) * this.alpha1)) - (psi(this.s0, this.T, this.beta, this.f17i1, this.f18i2, this.f17i1, this.f19t1) * this.alpha1)) + psi(this.s0, this.T, 1.0d, this.f17i1, this.f18i2, this.f17i1, this.f19t1)) - psi(this.s0, this.T, 1.0d, this.K, this.f18i2, this.f17i1, this.f19t1)) - (psi(this.s0, this.T, 0.0d, this.f17i1, this.f18i2, this.f17i1, this.f19t1) * this.K));
        }
    }

    /* access modifiers changed from: protected */
    public double putPrice() {
        if (this.T <= 0.0d) {
            return Math.max(this.K - this.s0, 0.0d);
        }
        prepare();
        return new American(new double[]{this.K, this.s0, this.T, this.q * 100.0d, this.r * 100.0d, this.sig * 100.0d}).callPrice();
    }

    /* access modifiers changed from: protected */
    public double callPerpetualPrice() {
        prepare();
        if (this.q == 0.0d) {
            return this.s0;
        }
        double y1 = (0.5d - (this.b / this.f5V)) + Math.sqrt((((this.b / this.f5V) - 0.5d) * ((this.b / this.f5V) - 0.5d)) + ((2.0d * this.r) / this.f5V));
        return (this.K / (y1 - 1.0d)) * Math.pow((((y1 - 1.0d) / y1) * this.s0) / this.K, y1);
    }

    /* access modifiers changed from: protected */
    public double putPerpetualPrice() {
        prepare();
        double y2 = (0.5d - (this.b / this.f5V)) - Math.sqrt((((this.b / this.f5V) - 0.5d) * ((this.b / this.f5V) - 0.5d)) + ((2.0d * this.r) / this.f5V));
        return (this.K / (1.0d - y2)) * Math.pow((((y2 - 1.0d) / y2) * this.s0) / this.K, y2);
    }
}
