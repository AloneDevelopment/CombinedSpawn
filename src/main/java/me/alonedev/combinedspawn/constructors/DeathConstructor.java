package me.alonedev.combinedspawn.constructors;

import java.util.HashMap;

public class DeathConstructor {

    private String message;
    private String privatemessage;
    private String title;
    private String subtitle;
    private int MoneyPenalty;
    private int LevelPenalty;
    private int expPenalty;
    private int fadein;
    private int fadeout;
    private int stay;
    private int cooldown;
    private boolean keepinventory;
    private boolean keeplevel;


    public DeathConstructor(String message, String privatemessage, String Title, String Subtitle, int MoneyPenalty, int LevelPenalty, int expPenalty, int fadeIn, int stay, int fadeOut, int cooldown, boolean keepinventory, boolean keeplevel) {
        this.message = message;
        this.privatemessage = privatemessage;
        this.MoneyPenalty = MoneyPenalty;
        this.LevelPenalty = LevelPenalty;
        this.expPenalty = expPenalty;
        this.title = Title;
        this.subtitle = Subtitle;
        this.fadein = fadeIn;
        this.stay = stay;
        this.fadeout = fadeOut;
        this.cooldown = cooldown;
        this.keepinventory = keepinventory;
        this.keeplevel = keeplevel;



    }

    public String getMessage(){
        return message;
    }
    public String getPrivateMessage() {return privatemessage;}
    public int getMoneyPenalty() {
        return MoneyPenalty;
    }
    public int getLevelPenalty() {
        return LevelPenalty;
    }
    public int getExpPenalty() {
        return expPenalty;
    }
    public String getTitle() {return title;}
    public String getSubtitle() {return subtitle;}
    public int getFadeIn() {return fadein;}
    public int getFadeout() {return fadeout;}
    public int getStay() {return stay;}
    public int getCoolown() {return cooldown;}
    public Boolean getInventory() {return keepinventory;}
    public Boolean getKeepLevel() {return keeplevel;}

    //Storage

    private static HashMap<String, DeathConstructor> DeathTypes = new HashMap<String, DeathConstructor>();

    public static void addDeathType(String id, DeathConstructor death){

        DeathTypes.put(id,death);

    }

    public static DeathConstructor getDeathType(String id){
        return DeathTypes.get(id);
    }
}