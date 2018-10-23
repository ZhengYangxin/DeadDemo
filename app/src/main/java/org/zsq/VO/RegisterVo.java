package org.zsq.VO;

/**
 * desc :
 * time  : 24/10/18.
 * author : pielan
 */

public class RegisterVo {

    /**
     * isValid : 1
     * lastVer : 0
     * createTime : 1540324686827
     * modifyTime : 1540324686827
     * userId : 5c9a31eba7ce4369afbafa3dec8e0e3f
     * name : 可乐果
     * mobile : 15021489117
     * provinceId : null
     */

    private int isValid;
    private int lastVer;
    private long createTime;
    private long modifyTime;
    private String userId;
    private String name;
    private String mobile;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
