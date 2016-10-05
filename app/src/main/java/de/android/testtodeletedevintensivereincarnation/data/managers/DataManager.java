package de.android.testtodeletedevintensivereincarnation.data.managers;

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferencesManager preferencesManager;

    public DataManager() {
        this.preferencesManager = new PreferencesManager();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }
}
