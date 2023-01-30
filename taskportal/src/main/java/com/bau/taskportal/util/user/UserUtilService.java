package com.bau.taskportal.util.user;

import com.bau.taskportal.bean.user.UserDetails;
import com.bau.taskportal.entity.User;
import com.bau.taskportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserUtilService {
    @Autowired
    private UserRepository userRepository;

    public int findUserId(String username) {
        return userRepository.findByUserName(username).getUserId();
    }

    public String findUserName(int userId) {
        return userRepository.findByUserId(userId).getUserName();
    }

    public User findUser(String username) {
        return userRepository.findByUserName(username);
    }

    public User saveUserDetails(User user) {
        return userRepository.save(user);
    }

    public List<UserDetails> getUserDetailsList(List<User> userList) {
        if (null != userList) {
            List<UserDetails> userDetailsList = new ArrayList<>();
            for (User user1 : userList)
                userDetailsList.add(setUserDetails(user1));
            return userDetailsList;
        }
        return null;
    }

    public UserDetails setUserDetails(User user) {
        if (null != user) {
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(user.getUserId());
            userDetails.setUserName(user.getUserName());
            userDetails.setEmail(user.getEmail());
            userDetails.setRole(user.getRole());
            userDetails.setProjectId(user.getProjectId());
            return userDetails;
        }
        return null;
    }
}
