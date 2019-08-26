package mySpring;

import java.beans.ConstructorProperties;

@Singleton
public class IRobot {
    @InjectByType
    private Speaker speaker;
    @InjectByType
    private Cleaner cleaner;

    @PostConstruct
    public void cleanRoom(){
        speaker.speak("I start");
        cleaner.clean();
        speaker.speak("I've finished");
    }
}
