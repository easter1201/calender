package com.example.calender.repository;

import com.example.calender.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user){ //유저 신규 저장
        String sql = "INSERT INTO user (user_name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getEmail(), user.getPassword());
    }

    @Override
    public String findUserNameById(Long userId){ //유저ID 기반 유저 검색, 문자열로 반환
        String sql = "SELECT user_name FROM user WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, userId);
    }

    @Override
    public User findByUserName(String userName){ //유저명 기반 유저 검색
        String sql = "SELECT * FROM user WHERE user_name = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userName);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public User findById(Long userId){ //ID기반 유저 검색, User로 반환
        String sql = "SELECT * FROM user WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId},
                (rs, rowNum) -> new User(
                    rs.getLong("user_id"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("password")
            ));
    }

    @Override
    public void updateUserName(Long userId, String newUserName){ //유저명 업데이트
        String sql = "UPDATE user SET user_name = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, newUserName, userId);
    }
}
