package com.bau.taskportal.controller;

import com.bau.taskportal.entity.Project;
import com.bau.taskportal.bean.project.RegionClass;
import com.bau.taskportal.bean.MemberDetails;
import com.bau.taskportal.util.project.ProjectUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @Autowired
    private ProjectUtilService projectUtilService;

    @PostMapping("/create/project")
    public Project createProject(@RequestBody RegionClass regionClass) {
        Project project = null;
        if (null == projectUtilService.findProject(regionClass.getProjectName())) {
            project = projectUtilService.createNewProject(regionClass);
            projectUtilService.assignManager(regionClass.getAssignedFor(), project);
        }
        return project;
    }

    @PostMapping("/assign/teamMembers")
    public Project assignTeamMembers(@RequestBody RegionClass regionClass) {
        Project project = null;
        project = projectUtilService.findProject(regionClass.getProjectName());
        for (MemberDetails teamMember : regionClass.getTeamMembers())
            projectUtilService.assignUser(teamMember, project);
        return project;
    }

    @PostMapping("/fetch/project")
    public Project fetchProject(@RequestBody RegionClass regionClass) {
        return projectUtilService.fetchProject(regionClass);
    }
}
