package com.sfac.javaSpringBoot.modules.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

    @Entity
    @Table(name = "h_student")
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int studentId;
        private String studentName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createDate;

        /**
         * OneToOne：一对一关系中，一方使用 JoinColumn 注解，另一方使用 mappedBy 属性（可选）
         * cascade：联级操作
         * fetch：加载数据策略
         * JoinColumn
         * name 对应 h_student 表中 card_id 外键列
         * referencedColumnName 对应关联表 Card 中的主键 cardId
         * insertable、updatable 标识该属性是否参与插入和更新插入
         * JsonIgnore：不序列化该字段，避免无限递归
         */
        @OneToOne(targetEntity = Card.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
        @JoinColumn(name = "card_id")
        private Card studentCard;

        /**
         * ManyToMany，一方使用 JoinTable 注解，另一方配置 mappedBy 属性
         * cascade：联级操作
         * fetch：加载数据策略
         */
        @ManyToMany(mappedBy = "students", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
        private List<Clazz> clazzes;

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public LocalDateTime getCreateDate() {
            return createDate;
        }

        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        public List<Clazz> getClazzes() {
            return clazzes;
        }

        public void setClazzes(List<Clazz> clazzes) {
            this.clazzes = clazzes;
        }

        public Card getStudentCard() {
            return studentCard;
        }

        public void setStudentCard(Card studentCard) {
            this.studentCard = studentCard;
        }

}
