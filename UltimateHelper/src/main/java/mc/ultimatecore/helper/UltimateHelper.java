package mc.ultimatecore.helper;

import mc.ultimatecore.helper.utils.*;

public class UltimateHelper extends UltimatePlugin {
    private static final int BSTATS_ID = 15616;


    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, BSTATS_ID);
        super.onEnable();
    }
}
