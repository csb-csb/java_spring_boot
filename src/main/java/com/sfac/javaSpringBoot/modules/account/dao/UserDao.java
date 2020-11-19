package com.sfac.javaSpringBoot.modules.account.dao;

import com.sfac.javaSpringBoot.modules.account.entity.User;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    @Select("select * from user where user_name= #{userName}")
    @ResultMap(value = "userResults")
    User getUserByUserName(String userName);

    @Insert("insert into user(user_name,password, user_img, create_date)"+
            "values(#{userName},#{password},#{userImg},#{createDate})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    void insertUser(User user);


    @Select("<script>" +
            "select * from user "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (user_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by user_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearchVo(SearchVo searchVo);

    @Update("update user set user_img=#{userImg},user_name=#{userName} where user_id = #{userId}")
    void updateUser(User user);

    @Delete("delete from user where user_id=#{userId}")
    void deleteUser(int userId);

    @Select("select * from user where user_id = #{userId}")
    @Results(id = "userResults", value = {
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_id", property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.sfac.javaSpringBoot.modules." +
                            "account.dao.RoleDao.getRolesByUserId"))
    }
    )
    User getUserByUserId(int userId);
}
