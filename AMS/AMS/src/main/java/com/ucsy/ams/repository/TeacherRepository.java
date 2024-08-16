package com.ucsy.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucsy.ams.entity.Teachers;

public interface TeacherRepository extends JpaRepository<Teachers, Integer>{
}
