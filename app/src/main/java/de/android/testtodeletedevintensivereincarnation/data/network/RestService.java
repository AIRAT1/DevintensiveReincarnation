package de.android.testtodeletedevintensivereincarnation.data.network;

import de.android.testtodeletedevintensivereincarnation.data.network.req.UserLoginReq;
import de.android.testtodeletedevintensivereincarnation.data.network.res.UserModelRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestService {

    @POST("login")
    Call<UserModelRes> loginUser (@Body UserLoginReq req);
}
