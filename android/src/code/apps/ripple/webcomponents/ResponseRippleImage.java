package code.apps.ripple.webcomponents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRippleImage {
    @SerializedName("response")
    @Expose
    private Integer a;
    @SerializedName("msg")
    @Expose
    private String b;
    @SerializedName("base_url")
    @Expose
    private String c;
    @SerializedName("img")
    @Expose
    private List<String> d = null;

    public Integer getResponse() {
        return this.a;
    }

    public void setResponse(Integer num) {
        this.a = num;
    }

    public String getMsg() {
        return this.b;
    }

    public void setMsg(String str) {
        this.b = str;
    }

    public String getBaseUrl() {
        return this.c;
    }

    public void setBaseUrl(String str) {
        this.c = str;
    }

    public List<String> getImg() {
        return this.d;
    }

    public void setImg(List<String> list) {
        this.d = list;
    }
}
