package mySpring;

@Singleton(lazy = false)
@Benchmark
public class VacuumCleaner implements Cleaner {
    @InjectRandomInt(min = 3, max = 5)
    private int repeat;
    public void clean() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("I am cleaning room");
        }

    }
}
