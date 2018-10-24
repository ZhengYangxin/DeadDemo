package org.zsq.VO;

public class UserInfoBeanVO {
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