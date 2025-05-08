package com.example.calender.repository;

import com.example.calender.entity.Schedule;
import com.example.calender.dto.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;
import java.sql.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Schedule saveSchedule(Schedule schedule){
        String sql = "INSERT INTO schedule (task, userName, password, generatedTime, modifiedTime) " + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getTask(), schedule.getUserName(), schedule.getPassword(), Timestamp.valueOf(schedule.getGeneratedTime()), Timestamp.valueOf(schedule.getGeneratedTime()));

        String getId = "SELECT LAST_INSERT_ID()";
        Long generatedId = jdbcTemplate.queryForObject(getId, Long.class);
        schedule.setId(generatedId);
        return schedule;
    }

    @Override
    public List<Schedule> findAll(){
        String sql = "SELECT * FROM schedule ORDER BY modifiedTime DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("userName"),
                rs.getString("password"),
                rs.getTimestamp("generatedTime").toLocalDateTime(),
                rs.getTimestamp("updatedTime").toLocalDateTime()
        ));
    }

    @Override
    public Optional<Schedule> findById(Long id){
        String sql = "SELECT * FROM schedule WHERE id = ?";
        Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id},
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getTimestamp("generatedTime").toLocalDateTime(),
                        rs.getTimestamp("updatedTime").toLocalDateTime()
                ));

        return Optional.ofNullable(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule){
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getId());
    }
}
