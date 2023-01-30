package com.bau.taskportal.controller;

import com.bau.taskportal.bean.project.ProjectDetails;
import com.bau.taskportal.bean.project.RegionClass;
import com.bau.taskportal.bean.user.MemberDetails;
import com.bau.taskportal.bean.user.UserDetails;
import com.bau.taskportal.util.project.ProjectUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectUtilService projectUtilService;

    @PostMapping("/create/project")
    public ProjectDetails createProject(@RequestBody RegionClass regionClass) {
        ProjectDetails projectDetails = null;
        if (null == projectUtilService.findProject(regionClass.getProjectName())) {
            projectDetails = projectUtilService.createNewProject(regionClass);
            projectUtilService.assignManager(regionClass.getAssignedFor(), projectDetails);
        }
        return projectDetails;
    }

    @PostMapping("/assign/teamMembers")
    public List<UserDetails> assignTeamMembers(@RequestBody RegionClass regionClass) {
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (MemberDetails teamMember : regionClass.getTeamMembers())
            userDetailsList.add(projectUtilService.assignUser(teamMember, projectUtilService.findProject(regionClass.getProjectName())));
        return userDetailsList;
    }

    @PostMapping("/fetch/project")
    public ProjectDetails fetchProject(@RequestBody RegionClass regionClass) {
        if (null != regionClass.getProjectName() && null != regionClass.getAssignedFor())
            return projectUtilService.fetchProject(regionClass);
        return null;
    }

    @PostMapping("/fetch/all/project")
    public List<ProjectDetails> fetchAllProject(@RequestBody RegionClass regionClass) {
        List<ProjectDetails> taskResultList = new ArrayList<>();
        if (null != regionClass.getAssignedFor())
            taskResultList = projectUtilService.fetchAllProject(regionClass);
        return taskResultList;
    }
}
