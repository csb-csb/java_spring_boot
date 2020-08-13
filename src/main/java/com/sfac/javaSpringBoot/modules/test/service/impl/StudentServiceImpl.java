package com.sfac.javaSpringBoot.modules.test.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.util.StringUtil;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.Student;
import com.sfac.javaSpringBoot.modules.test.repository.CardRepository;
import com.sfac.javaSpringBoot.modules.test.repository.StudentRepository;
import com.sfac.javaSpringBoot.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;


    @Override
    @Transactional
    public Result<Student> insertStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        studentRepository.saveAndFlush(student);
        return new Result<Student>(Result.ResultStatus.SUCCESS.status,
                "Insert success", student);
    }

    @Override
    public Student getStudentByStudentId(int studentId) {

        return studentRepository.findById(studentId).get();
    }

    @Override
    public Page<Student> getStudentsBySearchVo(SearchVo searchVo) {
        //以什么排序
       String orderBy= StringUtils.isBlank(searchVo.getOrderBy()) ? searchVo.getOrderBy() : "studentId";
       //排序方式
       Sort.Direction direction=StringUtils.isBlank(searchVo.getSort())||
                searchVo.getSort().equalsIgnoreCase("asc")?
                Sort.Direction.ASC:Sort.Direction.DESC;
       //封装一个排序方法
       Sort sort=new Sort(direction,orderBy);
       //起始当前页0
        Pageable pageable= PageRequest.of(searchVo.getCurrentPage()-1,searchVo.getPageSize(),sort);
        //build example
        //如果keyword为null，则设置的studentName不参与查询
        Student student=new Student();
        student.setStudentName(searchVo.getKeyWord());
        ExampleMatcher matcher=ExampleMatcher.matching()
        //全部进行模糊查询，即%{studentName}%
        //.withMatcher("studentName", ExampleMatcher.GenericPropertyMatchers.contains())
           .withMatcher("studentName", match -> match.contains())
                .withIgnorePaths("studentId");

        Example<Student> example=Example.of(student,matcher);
        return (Page<Student>) studentRepository.findAll(example,pageable);

    }
    @Override
    public List<Student> getStudents() {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = new Sort(direction, "studentName");
        return studentRepository.findAll(sort);
    }
}
