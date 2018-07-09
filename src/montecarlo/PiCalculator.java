package montecarlo;

import java.util.Random;

/**
 * Created by lhe on 2018-07-09.
 */
public class PiCalculator {

    public double calcPi(int iterNum) {
        Random rand = new Random(System.currentTimeMillis());
        int cnt = 0;

        for (int i = 0; i < iterNum; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            if (x * x + y * y <= 1) {
                cnt++;
            }
        }

        return (double) cnt / iterNum * 4;
    }

    public static void main(String[] args) {
        int iterNum = 30000;
        PiCalculator pc = new PiCalculator();
        System.out.format("iterNum = %d\n", iterNum);
        double avg = 0;
        int maxRoundNum = 100;
        for (int i = 0; i < maxRoundNum; i++) {
            double pi = new PiCalculator().calcPi(iterNum);
            System.out.format("\tround# = %d, pi = %.8f, err = %.2f\n", (i + 1), pi, (pi - Math.PI) * 100 / Math.PI);
            avg += pi;
        }

        System.out.format("avg_pi = %.8f, avg_err = %.2f\n", avg / maxRoundNum, (avg / maxRoundNum - Math.PI) * 100 / Math.PI);
    }

}
