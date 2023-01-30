package com.bau.taskportal.repository;

import com.bau.taskportal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectName(String projectName);

    Project findByAssignedFor(int assignedFor);

    Project save(Project project);
}
