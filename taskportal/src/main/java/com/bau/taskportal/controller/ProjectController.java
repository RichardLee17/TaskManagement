package com.bau.taskportal.controller;

import com.bau.taskportal.bean.project.ProjectDetails;
import com.bau.taskportal.bean.project.RegionClass;
import com.bau.taskportal.bean.member.MemberDetails;
import com.bau.taskportal.bean.user.UserDetails;
import com.bau.taskportal.constant.Constants;
import com.bau.taskportal.util.project.ProjectUtilService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProjectController {

    @Autowired
    ProjectUtilService projectUtilService;

    private ProjectDetails projectDetails;

    Logger logger = Logger.getLogger(ProjectController.class.getName());

    @PostMapping("/create/project")
    public ProjectDetails createProject(@RequestBody @NotNull RegionClass regionClass) {
        projectDetails = null;
        if (null == projectUtilService.findProjectByName(regionClass.getProjectName())) {
            try {
                projectDetails = projectUtilService.createNewProject(regionClass);
            } catch (NullPointerException e) {
                logger.warning(Constants.INTERNAL_ERROR_MSG + e.getMessage());
            }
            try {
                projectUtilService.assignManager(regionClass.getAssignedFor(), projectDetails);
            } catch (NullPointerException e) {
                logger.warning(Constants.INTERNAL_ERROR_MSG + e.getMessage());
                projectDetails = null;
            }
        }

        if (null == projectDetails) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return projectDetails;
    }

    @PostMapping("/assign/teamMembers")
    public List<UserDetails> assignTeamMembers(@RequestBody @NotNull RegionClass regionClass) {
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (MemberDetails teamMember : regionClass.getTeamMembers()) {
            try {
                userDetailsList.add(projectUtilService.assignUser(teamMember, projectUtilService.findProjectByName(regionClass.getProjectName())));
            } catch (NullPointerException e) {
                logger.warning(Constants.INTERNAL_ERROR_MSG + e.getMessage());
            }
        }
        if (userDetailsList.isEmpty()) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return userDetailsList;
    }

    @PostMapping("/fetch/project")
    public ProjectDetails fetchProject(@RequestBody @NotNull RegionClass regionClass) {
        projectDetails = null;
        try {
            projectDetails = projectUtilService.fetchProject(regionClass);
        } catch (NullPointerException e) {
            logger.warning(Constants.INTERNAL_ERROR_MSG + e.getMessage());
        }
        if (null == projectDetails) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return projectDetails;
    }

    @PostMapping("/fetch/all/project")
    public List<ProjectDetails> fetchAllProject(@RequestBody @NotNull RegionClass regionClass) {
        List<ProjectDetails> taskResultList = new ArrayList<>();
        try {
            taskResultList = projectUtilService.fetchAllProject(regionClass);
        } catch (NullPointerException e) {
            logger.warning(Constants.INTERNAL_ERROR_MSG + e.getMessage());
        }
        if (taskResultList.isEmpty()) logger.warning(Constants.REQUEST_NOT_VALID_MSG);
        return taskResultList;
    }
}
