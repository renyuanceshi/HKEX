package com.dipen.pricer.Calculations;

public class AbstractAsset {
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};
    functionArray[] callFunctions = {new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callVega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.callElasticity(d);
        }
    }};
    functionArray[] putFunctions = {new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putPrice();
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putDelta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putGamma(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putVega(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putTheta(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putRho(d);
        }
    }, new functionArray() {
        public double function(double d) {
            return AbstractAsset.this.putElasticity(d);
        }
    }};

    public interface functionArray {
        double function(double d);
    }

    public AbstractAsset() {
    }

    public AbstractAsset(double[] dArr) {
    }

    /* access modifiers changed from: protected */
    public double callDelta(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callElasticity(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callGamma(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callPrice() {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callRho(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callTheta(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double callVega(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public void prepare() {
    }

    /* access modifiers changed from: protected */
    public double putDelta(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putElasticity(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putGamma(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putPrice() {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putRho(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putTheta(double d) {
        return 0.0d;
    }

    /* access modifiers changed from: protected */
    public double putVega(double d) {
        return 0.0d;
    }

    public void reconstruct(double[] dArr) {
    }
}
