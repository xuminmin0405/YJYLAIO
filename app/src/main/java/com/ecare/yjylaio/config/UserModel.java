package com.ecare.yjylaio.config;

import java.io.Serializable;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.config
 * ClassName: UserModel
 * Description:
 * Author:
 * CreateDate: 2021/7/8 15:02
 * Version: 1.0
 */
public class UserModel implements Serializable {

    private String nickname;
    private String imAccid;
    private String imToken;
    private String avatar;
    private long g2Uid;

    public UserModel(String nickname, String imAccid, String imToken, String avatar) {
        this.nickname = nickname;
        this.imAccid = imAccid;
        this.imToken = imToken;
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImAccid() {
        return imAccid;
    }

    public void setImAccid(String imAccid) {
        this.imAccid = imAccid;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getG2Uid() {
        return g2Uid;
    }

    public void setG2Uid(long g2Uid) {
        this.g2Uid = g2Uid;
    }
}
