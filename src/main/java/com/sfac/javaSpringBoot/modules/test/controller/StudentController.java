package com.sfac.javaSpringBoot.modules.test.controller;

import com.github.pagehelper.Page;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.Student;
import com.sfac.javaSpringBoot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService studentService;
    /**
     * 127.0.0.1/api/student ---- post
     * {"studentName":"hujiang1","studentCard":{"cardId":"1"}}
     */
    @PostMapping(value = "student",consumes = "application/json")
    public Result<Student> insertStudent(@RequestBody Student student){
        return studentService.insertStudent(student);
    }

    /**
     * 127.0.0.1/api/student/1 ---- get
     */
    @GetMapping("/student/{studentId}")
    public Student getStudentByStudentId(@PathVariable int studentId ){
        return studentService.getStudentByStudentId(studentId);
    }

    /**
     * 127.0.0.1/api/students ---- post
     * {"currentPage":"1","pageSize":"5","keyWord":"hu","orderBy":"studentName","sort":"desc"}
     */
    @PostMapping(value = "/students",consumes = "application/json")
    public Page<Student> getStudentsBySearchVo(@RequestBody SearchVo searchVo){
        return studentService.getStudentsBySearchVo(searchVo);
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }


}
