package com.example.calender.repository;

import com.example.calender.entity.Schedule;
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
        String sql = "INSERT INTO schedule (content, user_id, created_time, updated_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getContent(), schedule.getUserId(), Timestamp.valueOf(schedule.getGeneratedTime()), Timestamp.valueOf(schedule.getModifiedTime()));

        String getId = "SELECT LAST_INSERT_ID()";
        Long generatedId = jdbcTemplate.queryForObject(getId, Long.class);
        schedule.setContentId(generatedId);
        return schedule;
    }

    @Override
    public List<Schedule> findAll(){
        String sql = "SELECT * FROM schedule ORDER BY updated_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Schedule(
                rs.getLong("content_id"),
                rs.getString("content"),
                rs.getString("user_id"),
                rs.getTimestamp("created_time").toLocalDateTime(),
                rs.getTimestamp("updated_time").toLocalDateTime()
        ));
    }

    @Override
    public Optional<Schedule> findById(Long id){
        String sql = "SELECT * FROM schedule WHERE content_id = ?";
        Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id},
                (rs, rowNum) -> new Schedule(
                        rs.getLong("content_id"),
                        rs.getString("content"),
                        rs.getString("userName"),
                        rs.getTimestamp("created_time").toLocalDateTime(),
                        rs.getTimestamp("updated_time").toLocalDateTime()
                ));

        return Optional.ofNullable(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule){
        String sql = "DELETE FROM schedule WHERE content_id = ?";
        jdbcTemplate.update(sql, schedule.getContentId());
    }
}
