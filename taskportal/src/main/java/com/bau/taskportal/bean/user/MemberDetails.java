package com.bau.taskportal.bean.user;

public class MemberDetails {

    public String username;
    public String role;
    public String roleDesc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return "TeamMembers{" +
                "username='" + username + '\'' +
                '}';
    }
}
