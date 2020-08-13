package com.sfac.javaSpringBoot.modules.test.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @Description School
 * @Author HymanHu
 * @Date 2020/7/30 13:51
 */
@Entity
@Table(name = "h_school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schoolId;
    private int schoolName;
    @Transient
    private String region;

    /**
     * OneToMany：多方使用 joinClumn，创建外键，一方使用 mappedBy 属性
     * cascade：联级操作
     * fetch：加载数据策略
     */
    @OneToMany(mappedBy = "school", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Clazz> clazzes;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(int schoolName) {
        this.schoolName = schoolName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }
}