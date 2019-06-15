package code.apps.ripple.webcomponents;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceRipple {
    @FormUrlEncoded
    @POST("req.php")
    Call<ResponseRippleImage> getImages(@Field("package_name") String str);
}
