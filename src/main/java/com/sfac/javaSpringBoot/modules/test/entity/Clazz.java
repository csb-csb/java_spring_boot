package com.sfac.javaSpringBoot.modules.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @Description Clazz
 * @Author HymanHu
 * @Date 2020/7/30 13:47
 */
@Entity
@Table(name = "h_clazz")
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clazzId;
    private String schoolName;

    /**
     * ManyToOne：多方使用 joinClumn，创建外键，一方使用 mappedBy 属性
     * cascade：联级操作
     * fetch：加载数据策略
     * JoinColumn
     * name：多方 h_clazz 表的外键 school_id
     * insertable、updatable：标识该属性是否参与插入和更新插入
     * JsonIgnore：不序列化该字段，避免无限递归
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", insertable = false, updatable = false)
    @JsonIgnore
    private School school;

    /**
     * ManyToMany，一方使用 JoinTable 注解，另一方配置 mappedBy 属性
     * JoinTable：name 中间表表名
     * JoinTable：joinColumns 该表 h_clazz 参与中间表的主键
     * JoinTable：inverseJoinColumns 关联表 h_student 参与中间表的主键
     * cascade：联级操作
     * fetch：加载数据策略
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "h_clazz_student",
            joinColumns = @JoinColumn(name = "clazz_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}