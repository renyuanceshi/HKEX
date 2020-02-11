package com.dipen.pricer.Calculations;

import java.lang.reflect.Array;

public class NN {
    public static double SHOCK = 0.01d;
    public static double TIMESHOCK = 0.001d;

    static double N(double x) {
        return 0.398942280401433d * Math.exp(((-x) * x) / 2.0d);
    }

    static double IC(double u) {
        double[] a = {2.50662823884d, -18.61500062529d, 41.39119773534d, -25.44106049637d};
        double[] b = {-8.4735109309d, 23.08336743743d, -21.06224101826d, 3.13082909833d};
        double[] c = {0.3374754822726147d, 0.9761690190917186d, 0.1607979714918209d, 0.0276438810333863d, 0.0038405729373609d, 3.951896511919E-4d, 3.21767881768E-5d, 2.888167364E-7d, 3.960315187E-7d};
        double x = u - 0.5d;
        if (Math.abs(x) < 0.42d) {
            double y = x * x;
            return (((((((a[3] * y) + a[2]) * y) + a[1]) * y) + a[0]) * x) / ((((((((b[3] * y) + b[2]) * y) + b[1]) * y) + b[0]) * y) + 1.0d);
        }
        double r = u;
        if (x > 0.0d) {
            r = 1.0d - u;
        }
        double r2 = Math.log(-Math.log(r));
        double r3 = c[0] + ((c[1] + ((c[2] + ((c[3] + ((c[4] + ((c[5] + ((c[6] + ((c[7] + (c[8] * r2)) * r2)) * r2)) * r2)) * r2)) * r2)) * r2)) * r2);
        if (x < 0.0d) {
            return -r3;
        }
        return r3;
    }

    static double C(double x) {
        double[] a = {0.31938153d, -0.356563782d, 1.781477937d, -1.821255978d, 1.330274429d};
        if (x < -7.0d) {
            return N(x) / Math.sqrt(1.0d + (x * x));
        }
        if (x > 7.0d) {
            return 1.0d - C(-x);
        }
        double tmp = 1.0d / (1.0d + (0.2316419d * Math.abs(x)));
        double result = 1.0d - (N(x) * ((a[0] + ((a[1] + ((a[2] + ((a[3] + (a[4] * tmp)) * tmp)) * tmp)) * tmp)) * tmp));
        if (x <= 0.0d) {
            return 1.0d - result;
        }
        return result;
    }

    static double M(double x, double y, double rho) {
        int NG;
        int LG;
        double[][] XX = (double[][]) Array.newInstance(Double.TYPE, new int[]{11, 4});
        double[][] W = (double[][]) Array.newInstance(Double.TYPE, new int[]{11, 4});
        W[1][1] = 4595340635650841579L;
        XX[1][1] = -0.932469514203152d;
        W[2][1] = 4600170522661702673L;
        XX[2][1] = -0.661209386466265d;
        W[3][1] = 4602100808003438037L;
        XX[3][1] = -0.238619186083197d;
        W[1][2] = 4586959503511678347L;
        XX[1][2] = -0.981560634246719d;
        W[2][2] = 4592370211202425186L;
        XX[2][2] = -0.904117256370475d;
        W[3][2] = 4594935449896758663L;
        XX[3][2] = -0.769902674194305d;
        W[4][2] = 4596487898268806005L;
        XX[4][2] = -0.587317954286617d;
        W[5][2] = 4597580475494918844L;
        XX[5][2] = -0.36783149899818d;
        W[6][2] = 4598144488632021037L;
        XX[6][2] = -0.125233408511469d;
        W[1][3] = 4580734113311680682L;
        XX[1][3] = -0.993128599185095d;
        W[2][3] = 4586012103727625849L;
        XX[2][3] = -0.963971927277914d;
        W[3][3] = 4589180417679549559L;
        XX[3][3] = -0.912234428251326d;
        W[4][3] = 4590665142300500557L;
        XX[4][3] = -0.839116971822219d;
        W[5][3] = 4592009259857192961L;
        XX[5][3] = -0.746331906460151d;
        W[6][3] = 4593181234264750890L;
        XX[6][3] = -0.636053680726515d;
        W[7][3] = 4593912603514924556L;
        XX[7][3] = -0.510867001950827d;
        W[8][3] = 4594287572170351924L;
        XX[8][3] = -0.37370608871542d;
        W[9][3] = 4594542543540869531L;
        XX[9][3] = -0.227785851141645d;
        W[10][3] = 4594671541069427574L;
        XX[10][3] = -0.0765265211334973d;
        if (Math.abs(rho) < 0.3d) {
            NG = 1;
            LG = 3;
        } else if (Math.abs(rho) < 0.75d) {
            NG = 2;
            LG = 6;
        } else {
            NG = 3;
            LG = 10;
        }
        double h = -x;
        double k = -y;
        double hk = h * k;
        double BVN = 0.0d;
        if (Math.abs(rho) < 0.925d) {
            if (Math.abs(rho) > 0.0d) {
                double hs = 0.5d * ((h * h) + (k * k));
                double asr = Math.asin(rho);
                for (int i = 1; i <= LG; i++) {
                    for (int ISs = -1; ISs <= 1; ISs += 2) {
                        double sn = Math.sin((((((double) ISs) * XX[i][NG]) + 1.0d) * asr) / 2.0d);
                        BVN += W[i][NG] * Math.exp(((sn * hk) - hs) / (1.0d - (sn * sn)));
                    }
                }
                BVN = (BVN * asr) / (4.0d * 3.141592653589793d);
            }
            return BVN + (C(-h) * C(-k));
        }
        if (rho < 0.0d) {
            k = -k;
            hk = -hk;
        }
        if (Math.abs(rho) < 1.0d) {
            double Ass = (1.0d - rho) * (1.0d + rho);
            double A = Math.sqrt(Ass);
            double bs = (h - k) * (h - k);
            double c = (4.0d - hk) / 8.0d;
            double d = (12.0d - hk) / 16.0d;
            double asr2 = (-((bs / Ass) + hk)) / 2.0d;
            if (asr2 > -100.0d) {
                BVN = Math.exp(asr2) * A * ((1.0d - ((((bs - Ass) * c) * (1.0d - ((d * bs) / 5.0d))) / 3.0d)) + ((((c * d) * Ass) * Ass) / 5.0d));
            }
            if ((-hk) < 100.0d) {
                double B = Math.sqrt(bs);
                BVN -= (((Math.exp((-hk) / 2.0d) * 2.506628274631d) * C((-B) / A)) * B) * (1.0d - (((c * bs) * (1.0d - ((d * bs) / 5.0d))) / 3.0d));
            }
            double A2 = A * 0.5d;
            for (int i2 = 1; i2 <= LG; i2++) {
                for (int iss = -1; iss <= 1; iss += 2) {
                    double xs = A2 * ((((double) iss) * XX[i2][NG]) + 1.0d);
                    double xs2 = Math.abs(xs * xs);
                    double rs = Math.sqrt(1.0d - xs2);
                    double asr3 = (-((bs / xs2) + hk)) / 2.0d;
                    if (asr3 > -100.0d) {
                        BVN += W[i2][NG] * A2 * Math.exp(asr3) * ((Math.exp(((-hk) * (1.0d - rs)) / (2.0d * (1.0d + rs))) / rs) - (1.0d + ((c * xs2) * (1.0d + (d * xs2)))));
                    }
                }
            }
            BVN = (-BVN) / (2.0d * 3.141592653589793d);
        }
        if (rho > 0.0d) {
            return BVN + C(-Math.max(h, k));
        }
        double BVN2 = BVN * -1.0d;
        if (k > h) {
            return (C(k) + BVN2) - C(h);
        }
        return BVN2;
    }
}
