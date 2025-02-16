package com.ucsy.ams.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.entity.TeacherHasCourse;

@Repository
public interface TeacherHasCourseRepo extends JpaRepository<TeacherHasCourse, Integer>{

}
