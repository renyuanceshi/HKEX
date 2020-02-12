package com.dipen.pricer.Calculations;

public class NN {
   public static double SHOCK = 0.01D;
   public static double TIMESHOCK = 0.001D;

   static double C(double var0) {
      double[] var2 = new double[]{0.31938153D, -0.356563782D, 1.781477937D, -1.821255978D, 1.330274429D};
      double var3;
      if (var0 < -7.0D) {
         var3 = N(var0) / Math.sqrt(1.0D + var0 * var0);
      } else if (var0 > 7.0D) {
         var3 = 1.0D - C(-var0);
      } else {
         var3 = 1.0D / (1.0D + 0.2316419D * Math.abs(var0));
         double var5 = 1.0D - N(var0) * (var2[0] + (var2[1] + (var2[2] + (var2[3] + var2[4] * var3) * var3) * var3) * var3) * var3;
         var3 = var5;
         if (var0 <= 0.0D) {
            var3 = 1.0D - var5;
         }
      }

      return var3;
   }

   static double IC(double var0) {
      double[] var2 = new double[]{2.50662823884D, -18.61500062529D, 41.39119773534D, -25.44106049637D};
      double[] var3 = new double[]{-8.4735109309D, 23.08336743743D, -21.06224101826D, 3.13082909833D};
      double[] var4 = new double[]{0.3374754822726147D, 0.9761690190917186D, 0.1607979714918209D, 0.0276438810333863D, 0.0038405729373609D, 3.951896511919E-4D, 3.21767881768E-5D, 2.888167364E-7D, 3.960315187E-7D};
      double var5 = var0 - 0.5D;
      if (Math.abs(var5) < 0.42D) {
         var0 = var5 * var5;
         var0 = (((var2[3] * var0 + var2[2]) * var0 + var2[1]) * var0 + var2[0]) * var5 / ((((var3[3] * var0 + var3[2]) * var0 + var3[1]) * var0 + var3[0]) * var0 + 1.0D);
      } else {
         double var7 = var0;
         if (var5 > 0.0D) {
            var7 = 1.0D - var0;
         }

         var0 = Math.log(-Math.log(var7));
         var7 = var4[0] + (var4[1] + (var4[2] + (var4[3] + (var4[4] + (var4[5] + (var4[6] + (var4[7] + var4[8] * var0) * var0) * var0) * var0) * var0) * var0) * var0) * var0;
         var0 = var7;
         if (var5 < 0.0D) {
            var0 = -var7;
         }
      }

      return var0;
   }

   static double M(double var0, double var2, double var4) {
      double[][] var6 = new double[11][4];
      double[][] var7 = new double[11][4];
      var7[1][1] = 0.17132449237917D;
      var6[1][1] = -0.932469514203152D;
      var7[2][1] = 0.360761573048138D;
      var6[2][1] = -0.661209386466265D;
      var7[3][1] = 0.46791393457269D;
      var6[3][1] = -0.238619186083197D;
      var7[1][2] = 0.0471753363865118D;
      var6[1][2] = -0.981560634246719D;
      var7[2][2] = 0.106939325995318D;
      var6[2][2] = -0.904117256370475D;
      var7[3][2] = 0.160078328543346D;
      var6[3][2] = -0.769902674194305D;
      var7[4][2] = 0.203167426723066D;
      var6[4][2] = -0.587317954286617D;
      var7[5][2] = 0.233492536538355D;
      var6[5][2] = -0.36783149899818D;
      var7[6][2] = 0.249147045813403D;
      var6[6][2] = -0.125233408511469D;
      var7[1][3] = 0.0176140071391521D;
      var6[1][3] = -0.993128599185095D;
      var7[2][3] = 0.0406014298003869D;
      var6[2][3] = -0.963971927277914D;
      var7[3][3] = 0.0626720483341091D;
      var6[3][3] = -0.912234428251326D;
      var7[4][3] = 0.0832767415767048D;
      var6[4][3] = -0.839116971822219D;
      var7[5][3] = 0.10193011981724D;
      var6[5][3] = -0.746331906460151D;
      var7[6][3] = 0.118194531961518D;
      var6[6][3] = -0.636053680726515D;
      var7[7][3] = 0.131688638449177D;
      var6[7][3] = -0.510867001950827D;
      var7[8][3] = 0.142096109318382D;
      var6[8][3] = -0.37370608871542D;
      var7[9][3] = 0.149172986472604D;
      var6[9][3] = -0.227785851141645D;
      var7[10][3] = 0.152753387130726D;
      var6[10][3] = -0.0765265211334973D;
      byte var8;
      byte var9;
      if (Math.abs(var4) < 0.3D) {
         var8 = 1;
         var9 = 3;
      } else if (Math.abs(var4) < 0.75D) {
         var8 = 2;
         var9 = 6;
      } else {
         var8 = 3;
         var9 = 10;
      }

      double var10 = -var0;
      double var12 = -var2;
      double var14 = var10 * var12;
      var0 = 0.0D;
      int var16;
      int var17;
      if (Math.abs(var4) < 0.925D) {
         var2 = var0;
         if (Math.abs(var4) > 0.0D) {
            var2 = Math.asin(var4);
            var16 = 1;

            while(true) {
               if (var16 > var9) {
                  var2 = var0 * var2 / (4.0D * 3.141592653589793D);
                  break;
               }

               for(var17 = -1; var17 <= 1; var17 += 2) {
                  var4 = Math.sin(((double)var17 * var6[var16][var8] + 1.0D) * var2 / 2.0D);
                  var0 += var7[var16][var8] * Math.exp((var4 * var14 - 0.5D * (var10 * var10 + var12 * var12)) / (1.0D - var4 * var4));
               }

               ++var16;
            }
         }

         var0 = var2 + C(-var10) * C(-var12);
      } else {
         double var18 = var14;
         double var20 = var12;
         if (var4 < 0.0D) {
            var20 = -var12;
            var18 = -var14;
         }

         var2 = var0;
         if (Math.abs(var4) < 1.0D) {
            double var22 = (1.0D - var4) * (1.0D + var4);
            double var24 = Math.sqrt(var22);
            double var26 = (var10 - var20) * (var10 - var20);
            var12 = (4.0D - var18) / 8.0D;
            var14 = (12.0D - var18) / 16.0D;
            var2 = -(var26 / var22 + var18) / 2.0D;
            if (var2 > -100.0D) {
               var0 = Math.exp(var2) * var24 * (1.0D - (var26 - var22) * var12 * (1.0D - var14 * var26 / 5.0D) / 3.0D + var12 * var14 * var22 * var22 / 5.0D);
            }

            var2 = var0;
            if (-var18 < 100.0D) {
               var2 = Math.sqrt(var26);
               var2 = var0 - Math.exp(-var18 / 2.0D) * 2.506628274631D * C(-var2 / var24) * var2 * (1.0D - var12 * var26 * (1.0D - var14 * var26 / 5.0D) / 3.0D);
            }

            var24 *= 0.5D;
            var16 = 1;
            var0 = var2;

            while(true) {
               if (var16 > var9) {
                  var2 = -var0 / (2.0D * 3.141592653589793D);
                  break;
               }

               for(var17 = -1; var17 <= 1; var0 = var2) {
                  var2 = var24 * ((double)var17 * var6[var16][var8] + 1.0D);
                  double var28 = Math.abs(var2 * var2);
                  var22 = Math.sqrt(1.0D - var28);
                  double var30 = -(var26 / var28 + var18) / 2.0D;
                  var2 = var0;
                  if (var30 > -100.0D) {
                     var2 = var0 + var7[var16][var8] * var24 * Math.exp(var30) * (Math.exp(-var18 * (1.0D - var22) / (2.0D * (1.0D + var22))) / var22 - (1.0D + var12 * var28 * (1.0D + var14 * var28)));
                  }

                  var17 += 2;
               }

               ++var16;
            }
         }

         if (var4 > 0.0D) {
            var0 = var2 + C(-Math.max(var10, var20));
         } else {
            var2 *= -1.0D;
            var0 = var2;
            if (var20 > var10) {
               var0 = C(var20) + var2 - C(var10);
            }
         }
      }

      return var0;
   }

   static double N(double var0) {
      return 0.398942280401433D * Math.exp(-var0 * var0 / 2.0D);
   }
}
