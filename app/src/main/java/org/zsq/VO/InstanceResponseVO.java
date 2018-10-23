package org.zsq.VO;

/**
 * 功能：
 * <p>
 * Created by danke on 2018/10/23.
 */
public class InstanceResponseVO {

    private MenuInstanceInfoBean menuInstanceInfo;
    private double rating;

    public MenuInstanceInfoBean getMenuInstanceInfo() {
        return menuInstanceInfo;
    }

    public void setMenuInstanceInfo(MenuInstanceInfoBean menuInstanceInfo) {
        this.menuInstanceInfo = menuInstanceInfo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static class MenuInstanceInfoBean {
        private int isValid;
        private int lastVer;
        private long createTime;
        private long modifyTime;
        private int menuInstanceId;
        private String name;
        private int tasteId;

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

        public int getMenuInstanceId() {
            return menuInstanceId;
        }

        public void setMenuInstanceId(int menuInstanceId) {
            this.menuInstanceId = menuInstanceId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTasteId() {
            return tasteId;
        }

        public void setTasteId(int tasteId) {
            this.tasteId = tasteId;
        }
    }
}
