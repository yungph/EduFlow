package com.advsoftware.EduFlow.repo;

import com.advsoftware.EduFlow.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByName(String username);


    @Query(value = "select * from user where email = :email",nativeQuery = true)
    User findByEmail(@Param("email") String email);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update user set user_type = :usertype where user_id= :id ",nativeQuery = true)
    void assignRole(@Param("usertype") String usertype, @Param("id") String id );

    @Modifying
    @Transactional
    @Query(value = "update user_roles  set role_name ='ROLE_ADMIN' where user_id=:id ",nativeQuery = true)
    void makeUserRoleAdmin(@Param("id") String id);

    @Query(value = "SELECT u.* FROM eduflow.user u JOIN eduflow.user_roles ur ON u.user_id = ur.user_id WHERE ur.role_name =:rolename ",nativeQuery = true)
    List<User> findByRoleName(@Param("rolename") String rolename);



}
