package mySpring;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext(new JavaConfig("mySpring"));
        context.getBean(IRobot.class);
    }
}
