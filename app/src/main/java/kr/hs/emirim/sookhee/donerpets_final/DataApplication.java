package kr.hs.emirim.sookhee.donerpets_final;

import android.app.Application;


public class DataApplication extends Application {
    private boolean switch_alarm1 = false;
    private boolean switch_alarm2 = false;

    public boolean isSwitch_alarm1(){
        return switch_alarm1;
    }

    public void setSwitch_alarm1(boolean switch_alarm1){
        this.switch_alarm1 = switch_alarm1;
    }

    public boolean isSwitch_alarm2() {
        return switch_alarm2;
    }

    public void setSwitch_alarm2(boolean switch_alarm2) {
        this.switch_alarm2 = switch_alarm2;
    }




}