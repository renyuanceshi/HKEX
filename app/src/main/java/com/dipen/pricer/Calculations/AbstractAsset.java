package com.dipen.pricer.Calculations;

public class AbstractAsset {
    public functionArray[][] allFunctions = {this.callFunctions, this.putFunctions};
    functionArray[] callFunctions = {new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callVega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.callElasticity(x);
        }
    }};
    functionArray[] putFunctions = {new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putPrice();
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putDelta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putGamma(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putVega(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putTheta(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putRho(x);
        }
    }, new functionArray() {
        public double function(double x) {
            return AbstractAsset.this.putElasticity(x);
        }
    }};

    public interface functionArray {
        double function(double d);
    }

    public AbstractAsset() {
    }

    public AbstractAsset(double[] x) {
    }

    protected void prepare() {
    }

    public void reconstruct(double[] x) {
    }

    protected double callPrice() {
        return 0.0d;
    }

    protected double putPrice() {
        return 0.0d;
    }

    protected double callDelta(double ds) {
        return 0.0d;
    }

    protected double putDelta(double ds) {
        return 0.0d;
    }

    protected double callGamma(double ds) {
        return 0.0d;
    }

    protected double putGamma(double ds) {
        return 0.0d;
    }

    protected double callVega(double dv) {
        return 0.0d;
    }

    protected double putVega(double dv) {
        return 0.0d;
    }

    protected double callTheta(double dt) {
        return 0.0d;
    }

     protected double putTheta(double dt) {
        return 0.0d;
    }

    protected double callRho(double dr) {
        return 0.0d;
    }

    protected double putRho(double dr) {
        return 0.0d;
    }

    protected double callElasticity(double dr) {
        return 0.0d;
    }

    protected double putElasticity(double dr) {
        return 0.0d;
    }
}
