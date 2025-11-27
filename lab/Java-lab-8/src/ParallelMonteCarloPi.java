public class ParallelMonteCarloPi {

    // Загальна кількість "кидків" (точок)
    private static final long TOTAL_ITERATIONS = 1_000_000_000L; // 1e9

    // Завдання для одного потоку
    private static class MonteCarloTask implements Runnable {
        private final long iterations;
        private long insideCircle; // скільки точок потрапило в коло

        public MonteCarloTask(long iterations) {
            this.iterations = iterations;
        }

        @Override
        public void run() {
            long inside = 0;
            for (long i = 0; i < iterations; i++) {
                double x = Math.random(); // [0,1)
                double y = Math.random(); // [0,1)
                if (x * x + y * y <= 1.0) {
                    inside++;
                }
            }
            insideCircle = inside;
        }

        public long getInsideCircle() {
            return insideCircle;
        }

        public long getIterations() {
            return iterations;
        }
    }

    public static void main(String[] args) {
        int threadsCount;

        // 1 аргумент – кількість потоків
        if (args.length < 1) {
            System.out.println("Використання: java ParallelMonteCarloPi <threads>");
            System.out.println("За замовчуванням threads = 3");
            threadsCount = 3;
        } else {
            threadsCount = Integer.parseInt(args[0]);
            if (threadsCount <= 0) {
                System.out.println("Кількість потоків повинна бути > 0, встановлюю 1");
                threadsCount = 1;
            }
        }

        long iterationsPerThread = TOTAL_ITERATIONS / threadsCount;
        long remainder = TOTAL_ITERATIONS % threadsCount;

        Thread[] threads = new Thread[threadsCount];
        MonteCarloTask[] tasks = new MonteCarloTask[threadsCount];

        long startTime = System.nanoTime();

        // Стартуємо потоки
        for (int i = 0; i < threadsCount; i++) {
            long iters = iterationsPerThread + (i < remainder ? 1 : 0);
            tasks[i] = new MonteCarloTask(iters);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        // Чекаємо завершення всіх потоків
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();

        // Підрахунок загальних результатів
        long totalInside = 0;
        long totalIterations = 0;
        for (int i = 0; i < threadsCount; i++) {
            totalInside += tasks[i].getInsideCircle();
            totalIterations += tasks[i].getIterations();
        }

        double pi = 4.0 * totalInside / (double) totalIterations;
        double elapsedMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("PI is %.5f%n", pi);
        System.out.printf("THREADS %d%n", threadsCount);
        System.out.printf("ITERATIONS %,d%n", totalIterations);
        System.out.printf("TIME %.2fms%n", elapsedMs);
    }
}
