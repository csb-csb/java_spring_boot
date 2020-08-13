package com.sfac.javaSpringBoot.modules.test.service;

import com.github.pagehelper.Page;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.Student;

import java.util.List;

public interface StudentService {
    Result<Student> insertStudent(Student student);

    Student getStudentByStudentId(int studentId);

    Page<Student> getStudentsBySearchVo(SearchVo searchVo);

    List<Student> getStudents();
}
