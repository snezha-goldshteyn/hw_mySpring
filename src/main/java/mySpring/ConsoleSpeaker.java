package mySpring;

@Singleton(lazy = false)
public class ConsoleSpeaker implements Speaker {
    public void speak(String phrase) {
        System.out.println(phrase);
    }
}
