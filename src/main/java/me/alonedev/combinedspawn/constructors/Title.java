package me.alonedev.combinedspawn.constructors;

public class Title {


    private static int delay;
    private String title;
    private String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;


    public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {

        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;

    }

    public String getTitle(){
        return title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public int getFadeIn() {
        return fadeIn;
    }
    public int getStay() {
        return stay;
    }
    public int getFadeOut() {
        return fadeOut;
    }

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delayint) {
        delay = delayint;
    }


}