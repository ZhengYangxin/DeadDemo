package org.zsq.event;

import org.zsq.VO.UserResponseVO;

/**
 * desc :
 * time  : 24/10/18.
 * author : pielan
 */

public class AddShopCarEvent {

    private UserResponseVO userResponseVO;

    public AddShopCarEvent(UserResponseVO userResponseVO) {
        this.userResponseVO = userResponseVO;
    }

    public UserResponseVO getUserResponseVO() {
        return userResponseVO;
    }

    public void setUserResponseVO(UserResponseVO userResponseVO) {
        this.userResponseVO = userResponseVO;
    }
}
