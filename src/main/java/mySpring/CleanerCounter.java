package mySpring;

public class CleanerCounter implements Cleaner {
    @InjectByType
    VacuumCleaner cleaner;

    @Override
    public void clean() {
        long start = System.nanoTime();
        cleaner.clean();
        long end = System.nanoTime();
        System.out.println(end - start);
    }
}
