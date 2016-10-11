package de.android.testtodeletedevintensivereincarnation.data.managers;

import android.content.Context;

import de.android.testtodeletedevintensivereincarnation.data.network.RestService;
import de.android.testtodeletedevintensivereincarnation.data.network.ServiceGenerator;
import de.android.testtodeletedevintensivereincarnation.data.network.req.UserLoginReq;
import de.android.testtodeletedevintensivereincarnation.data.network.res.UserModelRes;
import de.android.testtodeletedevintensivereincarnation.utils.DevintensiveApplication;
import retrofit2.Call;

public class DataManager {
    private static DataManager INSTANCE = null;

    private Context context;
    private PreferencesManager preferencesManager;
    private RestService restService;


    public DataManager() {
        this.preferencesManager = new PreferencesManager();
        this.context = DevintensiveApplication.getContext();
        this.restService = ServiceGenerator.createService(RestService.class);
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

    public Context getContext() {
        return context;
    }
    // region ================================ Network ======================================

    public Call<UserModelRes> loginUser (UserLoginReq userLoginReq) {
        return restService.loginUser(userLoginReq);
    }
    // endregion

    // region ================================ Database ======================================


    // endregion
}
