package org.zsq.VO;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBeanVO implements Parcelable {
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

        public UserInfoBeanVO(){}

    protected UserInfoBeanVO(Parcel in) {
        isValid = in.readInt();
        lastVer = in.readInt();
        createTime = in.readLong();
        modifyTime = in.readLong();
        id = in.readInt();
        name = in.readString();
        mobile = in.readString();
        headImg = in.readString();
    }

    public static final Creator<UserInfoBeanVO> CREATOR = new Creator<UserInfoBeanVO>() {
        @Override
        public UserInfoBeanVO createFromParcel(Parcel in) {
            return new UserInfoBeanVO(in);
        }

        @Override
        public UserInfoBeanVO[] newArray(int size) {
            return new UserInfoBeanVO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(isValid);
        parcel.writeInt(lastVer);
        parcel.writeLong(createTime);
        parcel.writeLong(modifyTime);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(mobile);
        parcel.writeString(headImg);
    }
}