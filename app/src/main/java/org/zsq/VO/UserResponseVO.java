package org.zsq.VO;

import android.os.Parcel;
import android.os.Parcelable;

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

    private UserInfoBeanVO userInfo;
    private double rating;
    public boolean isSelect;

    public UserResponseVO() {

    }

    public UserResponseVO(Parcel in) {
        userInfo = (UserInfoBeanVO) in.readValue(UserInfoBeanVO.class.getClassLoader());
        rating = in.readDouble();
    }

    public UserInfoBeanVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBeanVO userInfo) {
        this.userInfo = userInfo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
