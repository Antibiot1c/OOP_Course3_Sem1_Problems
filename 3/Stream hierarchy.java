class ThreadHierarchyPrinter implements Runnable {
    private final ThreadGroup threadGroup;

    public ThreadHierarchyPrinter(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread Hierarchy for Group: " + threadGroup.getName());
            printThreadGroupHierarchy(threadGroup, 0);
            System.out.println("------------------------------");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void printThreadGroupHierarchy(ThreadGroup group, int level) {
        int numThreads = group.activeCount();
        int numGroups = group.activeGroupCount();
        Thread[] threads = new Thread[numThreads];
        ThreadGroup[] groups = new ThreadGroup[numGroups];

        group.enumerate(threads, false);
        group.enumerate(groups, false);

        for (int i = 0; i < numThreads; i++) {
            printIndent(level);
            System.out.println("Thread: " + threads[i].getName());
        }

        for (int i = 0; i < numGroups; i++) {
            printIndent(level);
            System.out.println("Subgroup: " + groups[i].getName());
            printThreadGroupHierarchy(groups[i], level + 1);
        }
    }

    private void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  "); 
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ThreadGroup mainGroup = new ThreadGroup("MainGroup");

        Thread thread1 = new Thread(mainGroup, new RunnableTask(), "Thread1");
        Thread thread2 = new Thread(mainGroup, new RunnableTask(), "Thread2");
        thread1.start();
        thread2.start();

        ThreadHierarchyPrinter hierarchyPrinter = new ThreadHierarchyPrinter(mainGroup);
        Thread printerThread = new Thread(hierarchyPrinter);
        printerThread.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainGroup.interrupt();
    }

    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
            }
        }
    }
}
