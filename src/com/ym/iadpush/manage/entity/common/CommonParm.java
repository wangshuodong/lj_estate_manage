/**
 * 
 */
package com.ym.iadpush.manage.entity.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: lixingbiao 2017-5-17 下午11:25:02
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public class CommonParm {
    private String serverUrl = "https://openapi.alipay.com/gateway.do";
    private String app_id;
    private String private_key;
    private String alipay_public_key;
    // 陆甲物业
    // private String app_id = "2017061507496273";

    // 陆甲物业
    // private String private_key =
    // "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDK1QvIRkwYCpNOrWOEuRFLReqoO/4PN8aRze9QVaY7keaJrQNZGJM1/TnXwBOyaUEiacdSK0ulYVyEF24NvTf4+I4VwE9r6kk4WdloHokJKrmz/clcMaogQWV3fGoyaFlbA8T/AP4ge30U1yscrWmhGkmUGwfoY4Y/0f1evTp//3+gZg49rl9hpI6ycY81n1PieA/qc7V/97YgRrukpDvPytctgrUE/4WcsVKcPjmtzSncYsEXpxj59C7uVXnRdzKRrZeNFoH/YmyCV4h94apT/QiOIn3WSIbsXfCSRFcwV5YlnioV24rSd4g34xIqO6pDJxut3MkeAgYqBALGE0aRAgMBAAECggEACTauR1WyJTFtyZgkuiWx/Gpbo3K+MrDE62Cga1TIrVIOV9itG0H7hetP0lYeTu9rDwYhEMF4uTV3/2QVEriyrzOTOLe4o91LoCysrIcekHUJZHy1xeNXqAlwc4IKTOnijTjZnP/Ni2own166zZ2u+6t3BixIKq1Crul6CfCslJ3QwqiF1HjUkc9WMfbxYpYcsMrtX07iXxVr+6lVF7IQW0CT4YpQb0T7u4Dzwoa3Ui0aXfPmfBUkjFkdHf5wChWdLf825tB0jswrJNHQljVwg3drlrXH3qz4kRYaer108eVvPyZZdsd9Gq2i6aO/CO3CZea2l9ERrzXpSXU/N8UwtQKBgQD6EQTIABz9wwW5YwJlPrBEafoufXijU1+nbwsAeSqn58HpXV/2cvHyfhZkznobg+pZiZLim3m7T30KLE2cO//iyolcbr+SnF2w9zo/yhF3veBqb6QxjeqovLwpl7o1bsX0j4sz5KQI8HosG7JNqOkEwtLOjmVFKwpfy+Yo2wNfMwKBgQDPpRycO8qQFiiByRoWUBC+5156pPCbgdSXJ8o99lFqLDuRMFg75cYdWpG0fdaIi7WI8eAVhZUazrKGQ6jtRo39sDvNN609XD+kf5hN8ifZE36k/MCiaHSilpfs6jTasiTAlE2OTVcpo9bey48LIg+LTX+DVrQC2aJjbwLgn8iTKwKBgHOVcjRPeRcCEZ9C2FSbyTWWFoFulVhIrSni9Z7eYo0BXmvTMQvkzVhcV1V46o/VRXjlUfNUEcw2TYFqQOU6Bmh5XUxQFFRb2iJ144Kgj7pdFWGdALO2+QPNWNuQug0Aipcj0h/GStum65F/q8ahkDMXKgE3aC+0WTM2N8gik0hpAoGASqs2SZxasEu3j0xQ3Ds4el3bJMFRYKpkV2LQoE2GY2a6mPBImml7P60E/mHOFtYelrtb+IFL6BLyMcP86C5Z+yM3rglae9lDbZUQoN72o2IWRaA6aWyknJ8IzQI/tXEgNDxgU07OjEPFjeKncrpx2oco1oMEQo4ejiEqaLost3cCgYEAr9YsNKhWwWpoc07yAAuI5/DUum4M3hMybC84XE55V7EKOG66+KVP/3VBGFeNRLyZppEZto3X7Q8iOEywNUfACmf5uX0LA7thylqpEdV64pRO1yq2KWDTzQY64uti2kBLkJGPg+Wslu8Ijxrt/SgoCJNa/+hKDR/fGfBcxzh03Vg=";

    // 陆甲物业
    // private String alipay_public_key =
    // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk/TZHQq9cainm7LOvuuppP789rXrBoJK0ZTqriPSgJkptkPMGi1aI/g3be3W8qyYt0XhptavSFEKOvMeyYFtUwZPC733/YO0EEOpj4V8ecWM+MmgesaeBZDiZfv/wMAE2HA/m5BRNwk5ULuekXhS5rKRN4K+LRywzra7/8vvQmp2Lm79GYGJO4YjrgGkiJAA5CYB8ICDiWAIRP+PNQ//YRHaVLbjovaulFKbPo50yzuIPOQ8Jf9Zmg7OoME2AuAuANCkU2D7yEjPMC4194UQFhHpMEAPlg5bXXYgSxet9RfJgCz5jt8E49z/i73dVYP4GGiUI5R+tZVr+PmJ6keYnQIDAQAB";

    // 智通道和
//    private String app_id = "2017052607353106";
    // 智通道和
//    private String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCunU3r5Gf0WCdiCZJUnfyY78NXomwjEfHRJRypIPDt+4QECc+lw/nrmUYSe/AGz5l6ureIWez8FbVxLUecQOLs4XfnckWJgrWbry+YgYtR3OqBaI+OX9OPOQM7RObOK8Zm2lH6FkvHeNcTSjm9Uxtp7xhBoiXFANVfruRBkbQFdLxNIS5qVLkK72U5U//e6sP0aw1ZbVx+bOzyz/oLceUF8fB9VHhdl91rWZaEJg6rdOZ+KBsEO8etzsvBGhBGuN/GXzlup7675/nt/BrlrzKJvLkrGO87UqtzDG3BAwBNA+hap6nH6NzcV8FsSctVs/sX5mNencbNkQYJyfO6dh9vAgMBAAECggEBAJS44GRZPv1LlAeZVWwCv+FW3U8nxOi94POco2Od7fcUcSWrYZOzborvGlKmAxMaAIQawjU9Om6e4tJJmaOJVH4jr6eOmZF8pce7F1PSimIoQggEkbjW/28ORSVXEypo+Gwj6QyTrulvj+gbIUCSp1e70DIetAKuKg57/GeofObjUSmaCeeixh8+qU66Ovgls+O6tV/XlGOr2ncAcOWZDHNbu01kjgvY6MT6FIzkQW1Ng5h7acBm10WfceL1MOc74DqYDZ4voW+tSyBuHfiTUOccnOxMj8PJa8FjJN4sUwpxwrEDqWAzIn90S0iAhJH+jdyXW7EwytBuwNZxXE4KSZkCgYEA9hb+1yKmVa3d7bQ9acbZE5NFxdC/VM8B4hoAiMfDvaHsTdpOLd0gLSNDAUg2LRys0Ebr41PNaGEbsErMTzN3OYo3MHi+ep19iqLx9IXNaxx0OsY5Um8Y7muABhMDWNiFvp3nLbHmgCBGLY5oFjiAcn8ItwRNtFCkK0ueLVLsqWUCgYEAtaVzYwk7T56hM7qMNhfFwkJg2F6rgatRISHWb9sxJD9MjwQpe+FR+RfskN0SQOrINgkCpls2FjFP1LejnTMZjLSsMQQMqh2a8Jlj2usYp+mseU7r5OkO9usEkXasfsarkiYiF5VfJxYoSQwr74WwdrghHzjc7zDwBbcTPV63AkMCgYACyjnzaHIoUINOmZeEpnt5jFbjKj2xEXO9A3x0EIe4k6hhLZNrU7F0sYyl7BKpKkUPS26LMj0G7Orqcb6lOzkjztxXUVcffr+YncZOdmHgz/7FV8/4jkfjFBbserS0AihqA9JGI1kwpK0HATFO5y8VnTkVEhhrbeqejwnpC6KhPQKBgDaN+Wi+Jg0UpG+n0GKSozdjvjhdvn7UWCpuQjwYBdtQE4tEgb0OABOi5s7fRpMusHDiJeSWsljZ+JL3kHEvHrZOsQSLpc4V1Cn8t6vRPfd4S++nD1wRzwfDr/yMOuRcgL0FLOoOV6Sil/jQagWnDkepp3D8IAbKmNvujx24a5iHAoGAepmrfvd6lNC2xRX2rH4HGKQ08BrGCN6vu7tb4Ts50UEsgIlM1yCZUIEcGnINCsAwViNdkA3Zx9MAoCVvqxnhcBDrINhoMnzxbRW3MfckuVn7/gK2GvWEKT7piFe4QWmzUIbyE2KnefYmjm4ZhxIkrbyiiZDHu3AINv6jDd56F5g=";
    // 智通道和
//    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvgf6kvv6InHoI9dtJGV7R8nIEp0nw+kSivm73D/wMfy1IjA+2mGf3LW3z6IROnbEDH38RcKMgTGXIGrJag5GsNt2lRmOIH5lbvK7mwkdXk3SJN9K6ZNBpufvNdntzVlfDIhLMJ3gNjM7eZBRfOVKUtZuzQDQlKlKqOB5v0Qvrocm2PMn+562EerGEPeZCxWxmSro4HKGDl0TZQumn+4mlrXibjEZceyT02u8kSOa78+FI9CvoVHl+CGVO+7eyYnIiA9/DPnxt1Gt3rk/zCYLgCKjlVV3+lLwYiLay1sWXvgQTyX8KbKKensabww6c1M5WoamGziW0Y7RPKykGAdHsQIDAQAB";

    // 天策网络APP_ID
    // private String app_id = "2017050507120796";

    // 天策网络私钥
    // private String private_key =
    // "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgp5T+ztXQWoenulv+eOiS7N6gnI/tBQ61f2yumX5qVmR8YTmGg/nmxTBT6CfEYKDCnqcf0cNSMa5TxCRC+I10lc6ciA/EVUu1fvrRtxw5pGDrW/eEdqC/IrXGW8j3zEJgPNs43cOmtMRSf6f3XTVvBuv9wc4C1JeShwNEEMVfDH6rUwpvOHQq8Ziy0YY4C72Hr+4SEnxr2IO6mFfczfIYvlY+RRGA4nktOQkNi2ssxiyhIDOr9xm/Kb24CvgQ1n8lla7XjiY6aHF9wdHF0LBGczng03wB97g1jz5aGK+hBNnBm5RmN/a9fVrV7tJvPhcb1K8BT4PnyVOgy8k4zHh/AgMBAAECggEAOzrNocvb5H8b+Giqtf0ZseGGiqxuG7J7o8qg5RZOFyJW1cx6iu9pWP1mPekfkDUXz+jyMLcDTfBnFxb/T3mXnV4F2IwJVBcmXHm+O0dJLq891EgJz1p1FULUxZbdVw6FgjmtkR+hnHqUzt2lrXs90fs5Ukr7iuuaHjmMqLGOMuYw0VJvhvxJOvC1LO42lWB9cJGNf+Z27zjlnMKcBuIxFxzVy93kVWGOgaoeYZxLyBeFDKqaBCN1ZlKCYH3ZFcfpaEINKpkDyDnP3Jxb6BjjaMICq5G4wFcwIBwqoKfauWJKjCq6pgtTK8NcnK3nIyRTvk4TtMfqHsomoH7YqNW3sQKBgQDPkT++YL4O5fXHUIZB7AOql4/Nosu4SBF3PWouo/1fbg7uJ4cigU/478pVpCQldjgJG2I9o/xRYaYik8Rpz5PWv9CNqkjr+DqCNKEX2IMkwcUcFYr6iE+72/z5eUXw0AjEz1/EjSSilk8QV1AlJXmejqH327RBFv5MKa4Q0GYL/QKBgQDGJBBdJF6iTkQDFDxE6nXEevQtY0MMjOIZlpXLYB7UNzhUJC4MoUw8L1r+TRYDkvaClIPvTlZEHnPlh72QnGkVnExktBTIU4ng5JlFvFhLMDNYibzLcywv5GLLgz3j3XR8XwhEubbR+DI/5iZx8bo6AyUsuQgK3aGsAElWxCvZKwKBgEWvK8lMTUJPLa5WeFxVUf3JmDufNemOOF7hvW4fHAcXued2WcXZx/F4/2K1EnWJNNLJ4Ld1AFaKA+6D0WL8j1N/XhLRYl3mHi5sYNDSnWSPsQ72pIvri2k0N7/BWRu5m9gHDs273NrAv5Zq2bs+04/JrtK5bdCUWZdUPt3iOQA5AoGBAKFXOm/trpVxL5ZmQtFHLZBJIk1XF4C/s1hssxsA8PmS4W46a7jjGsWjNe/+6x8L62jW8DvLyRj8xxivGGAJzNPJCrexn36j75DN9DTBsg+/dv/wYGg2J1P0H7Jh8uJ6MGEZrQzxQHnnJVu8169Oy3pzlzC8llzqrbv2G8HoSWAzAoGBAMFRnZyR4+78iihwYZoWTur1bThVr7bOd1dkfuscV06fCs8hTomf8oJM1GMjzEHZNPqWh+YXJFv5FqFvc0CHqf9U9mAOtCOlMgNL2yFCV8ynDv5vV8dNcrEUnasl4OHTUaM+DHk3dlm61fO6bQrOs3QpuMdUGrgMuDGv3a8YiePG";

    // 天策网络支付宝公钥
    // private String alipay_public_key =
    // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxCrW0bC9B84dBrYl4liOFB3Y1boPxP+b4up7lgbI8CBt2SWNCmZ3OKGoXTisg/RZhwf9I8OCchtaoOtTQgKANL8tjIXLV+y4gbBkGtkt3/7MPoqXULySjQ7B1+/C3bXT8rFPvwrzIUfRvf+Hd9W9tiD+dDqo8wn8Zoi9TtpPck1RID+zQUnfYw7to4CAwXbcdO5owc75pV64Qlf5BFyHGrWdIEus020paT/QkHAwZoTkjxVWvQZRCJviXROwAltzJo6IhJplU/hDu1eMuf5UWGoMQzvlmvAzgoW8lVpY1ZmjdbMtSYgnx5QTUv1wez0jaZtlO4EBqCFB6RVmZCkdNQIDAQAB";

    private String format = "json";
    private String charset = "GBK";
    private String sign_type = "RSA2";
    private String token = "";
    public CommonParm(){
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/CommonParmConfig.properties");
            Properties p = new Properties();
            p.load(in);
            this.app_id = p.getProperty("app_id");
            this.private_key = p.getProperty("private_key");
            this.alipay_public_key = p.getProperty("alipay_public_key");
        } catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

}
