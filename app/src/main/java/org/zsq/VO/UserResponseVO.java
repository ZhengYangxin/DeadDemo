package org.zsq.VO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/24.
 */
public class UserResponseVO implements Parcelable {

    /**
     * userInfo : {"isValid":1,"lastVer":0,"createTime":1540334803427,"modifyTime":1540334803427,"id":7,"name":"海桐","mobile":"18267721127","headImg":"http://ph2p7uakt.bkt.clouddn.com/c.png"}
     * rating : 27.429188517743174
     */

    private UserInfoBean userInfo;
    private double rating;

    protected UserResponseVO(Parcel in) {
        userInfo = (UserInfoBean) in.readValue(UserInfoBean.class.getClassLoader());
        rating = in.readDouble();
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static class UserInfoBean {
        /**
         * isValid : 1
         * lastVer : 0
         * createTime : 1540334803427
         * modifyTime : 1540334803427
         * id : 7
         * name : 海桐
         * mobile : 18267721127
         * headImg : http://ph2p7uakt.bkt.clouddn.com/c.png
         */

        private int isValid;
        private int lastVer;
        private long createTime;
        private long modifyTime;
        private int id;
        private String name;
        private String mobile;
        private String headImg;

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public int getLastVer() {
            return lastVer;
        }

        public void setLastVer(int lastVer) {
            this.lastVer = lastVer;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }

    public static final Creator<UserResponseVO> CREATOR = new Creator<UserResponseVO>() {
        @Override
        public UserResponseVO createFromParcel(Parcel in) {
            return new UserResponseVO(in);
        }

        @Override
        public UserResponseVO[] newArray(int size) {
            return new UserResponseVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userInfo);
        dest.writeDouble(rating);
    }
}
