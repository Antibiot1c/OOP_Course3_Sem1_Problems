import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class TriDiagonalSystemSolver extends RecursiveTask<double[]> {
    private final double[] a;
    private final double[] b;
    private final double[] c;
    private final double[] d;
    private final int start;
    private final int end;

    public TriDiagonalSystemSolver(double[] a, double[] b, double[] c, double[] d, int start, int end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.start = start;
        this.end = end;
    }

    @Override
    protected double[] compute() {
        if (end - start == 1) {
            double det = b[start];
            double x = d[start] / det;
            return new double[]{x};
        } else {
            int mid = start + (end - start) / 2;

            TriDiagonalSystemSolver leftSolver = new TriDiagonalSystemSolver(a, b, c, d, start, mid);
            TriDiagonalSystemSolver rightSolver = new TriDiagonalSystemSolver(a, b, c, d, mid, end);

            invokeAll(leftSolver, rightSolver);

            double[] leftResult = leftSolver.join();
            double[] rightResult = rightSolver.join();

            double[] result = new double[end - start];
            System.arraycopy(leftResult, 0, result, 0, leftResult.length);
            System.arraycopy(rightResult, 0, result, leftResult.length, rightResult.length);

            return result;
        }
    }

    public static double[] solve(double[] a, double[] b, double[] c, double[] d) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new TriDiagonalSystemSolver(a, b, c, d, 0, a.length));
    }
}

public class Main {
    public static void main(String[] args) {
        double[] a = {0, 1, 2, 3}; // нижня діагональ
        double[] b = {4, 5, 6, 7}; // головна діагональ
        double[] c = {8, 9, 10, 0}; // верхня діагональ
        double[] d = {11, 12, 13, 14}; // права частина системи

        double[] result = TriDiagonalSystemSolver.solve(a, b, c, d);

        System.out.println("Solution:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("x[" + i + "] = " + result[i]);
        }
    }
}
