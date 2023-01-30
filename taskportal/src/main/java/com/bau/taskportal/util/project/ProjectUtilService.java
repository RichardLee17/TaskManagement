package com.bau.taskportal.util.project;

import com.bau.taskportal.bean.MemberDetails;
import com.bau.taskportal.entity.Project;
import com.bau.taskportal.bean.project.RegionClass;
import com.bau.taskportal.bean.User;
import com.bau.taskportal.repository.ProjectRepository;
import com.bau.taskportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Date;
@Service
public class ProjectUtilService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public void assignManager(String managerUName, Project project) {
        User user = findUser(managerUName);
        user.setProjectId(project.getProjectId());
        userRepository.save(user);
    }

    public void assignUser(MemberDetails memberDetails, Project project) {
        User user = findUser(memberDetails.getUsername());
        user.setProjectId(project.getProjectId());
        userRepository.save(user);
    }

    public Project createNewProject(RegionClass regionClass) {
        Project project = new Project();
        project.setProjectName(regionClass.getProjectName());
        project.setDescription(regionClass.getProjectDesc());
        project.setStatus("New");
        project.setCreatedTimestamp(new Timestamp(new Date().getTime()));
        project.setUpdatedTimestamp(new Timestamp(new Date().getTime()));
        project.setCreatedBy(findUserId(regionClass.getCreatedBy()));
        project.setAssignedFor(findUserId(regionClass.getAssignedFor()));
        return projectRepository.save(project);
    }


    public Project fetchProject(RegionClass regionClass) {
        return projectRepository.findByAssignedFor(findUserId(regionClass.getAssignedFor()));
    }

    private int findUserId(String username) {
        return userRepository.findByUserName(username).getUserId();
    }

    private User findUser(String username) {
        return userRepository.findByUserName(username);
    }

    public Project findProject(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }
}
