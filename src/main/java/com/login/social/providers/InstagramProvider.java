package com.login.social.providers;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.login.config.InstagramBuildService;
import com.login.model.UserBean;

@Service
public class InstagramProvider {
    private static final String INSTAGRAM = "instagram";
    @Autowired
    BaseProvider baseProvider ;

	public String getInstagramUserData(String code, Model model, UserBean userForm) throws Exception {
        populateUserDetailsFromInstagram(code, userForm);
        baseProvider.saveUserDetails(userForm);
        baseProvider.autoLoginUser(userForm);
        model.addAttribute("loggedInUser",userForm);
        return "secure/user";
    }

    public void populateUserDetailsFromInstagram(String code, UserBean userBean ) throws Exception {
        InstagramBuildService instagramObj = new InstagramBuildService();
        instagramObj.build();
        Instagram instagram = instagramObj.getInstagram(code);
        String token = instagram.getAccessToken().toString();
        System.out.println(token);
        UserInfo userInfo = instagram.getCurrentUserInfo();
        userBean.setEmail(userInfo.getData().getUsername());
        userBean.setFirstName(userInfo.getData().getFirstName());
        userBean.setLastName(userInfo.getData().getLastName());
        userBean.setImage(userInfo.getData().getProfilePicture());
        userBean.setProvider(INSTAGRAM);
    }



}