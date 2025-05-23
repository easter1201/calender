package com.example.calender.repository;

import com.example.calender.entity.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.*;
import java.sql.*;
import java.time.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override //일정 저장
    public Schedule saveSchedule(Schedule schedule, String userName, String password, String email){
        userExist(schedule.getUserId(), userName, password, email);

        String sql = "INSERT INTO schedule (content, user_id, created_time, updated_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getContent(), schedule.getUserId(), Timestamp.valueOf(schedule.getGeneratedTime()), Timestamp.valueOf(schedule.getModifiedTime()));

        String getId = "SELECT LAST_INSERT_ID()";
        Long generatedId = jdbcTemplate.queryForObject(getId, Long.class);
        schedule.setContentId(generatedId);
        return schedule;
    }

    @Override //일정 전체 조회
    public List<Schedule> findAll(){
        String sql = "SELECT s.content_id, s.content, s.user_id, s.created_time, s.updated_time, u.user_name FROM schedule s JOIN user u ON s.user_id = u.user_id ORDER BY updated_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule(
                    rs.getLong("content_id"),
                    rs.getString("content"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("created_time").toLocalDateTime(),
                    rs.getTimestamp("updated_time").toLocalDateTime()
            );
            schedule.setUserName(rs.getString("user_name"));
            return schedule;
        });
    }

    @Override //특정 일정 조회
    public Optional<Schedule> findById(Long id){
        String sql = "SELECT * FROM schedule WHERE content_id = ?";
        Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id},
                (rs, rowNum) -> new Schedule(
                        rs.getLong("content_id"),
                        rs.getString("content"),
                        rs.getLong("user_id"),
                        rs.getTimestamp("created_time").toLocalDateTime(),
                        rs.getTimestamp("updated_time").toLocalDateTime()
                ));

        return Optional.ofNullable(schedule);
    }

    @Override //일정 삭제
    public void deleteSchedule(Schedule schedule){
        String sql = "DELETE FROM schedule WHERE content_id = ?";
        jdbcTemplate.update(sql, schedule.getContentId());
    }

    @Override //일정 생성 시 기존유저와 동일한 입력값인지 확인, 기존 유저가 없는 경우 생성, 아닌 경우 해당 유저 정보 활용
    public void userExist(Long userId, String userName, String password, String email){
        String check = "SELECT COUNT(*) FROM user WHERE user_id = ?";
        Integer cnt = jdbcTemplate.queryForObject(check, Integer.class, userId);

        if(cnt != null && cnt == 0){
            String insert = "INSERT INTO user (user_id, user_name, password, email) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insert, userId, password, email);
        }
    }

    @Override //특정 일정 조회 쿼리
    public List<Schedule> findByFilter(Long userId, LocalDate date){
        StringBuilder sql = new StringBuilder("SELECT s.content_id, s.content, s.user_id, s.created_time, s.updated_time, u.user_name FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE 1=1");

        List<Object> parameters = new ArrayList<>();

        if(userId != null){
            sql.append(" AND s.user_id = ?");
            parameters.add(userId);
        }

        if(date != null){
            sql.append(" AND DATE(s.updated_time) = ?");
            parameters.add(Date.valueOf(date));
        }
        sql.append(" ORDER BY s.updated_time DESC");

        return jdbcTemplate.query(sql.toString(), parameters.toArray(), (rs, rowNum) -> {
            Schedule schedule = new Schedule(
                    rs.getLong("content_id"),
                    rs.getString("content"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("created_time").toLocalDateTime(),
                    rs.getTimestamp("updated_time").toLocalDateTime()
            );
            schedule.setUserName(rs.getString("user_name"));
            return schedule;
        });
    }

    @Override //일정 수정
    public void updateSchedule(Schedule schedule){
        String sql = "UPDATE schedule SET content = ?, updated_time = ? WHERE content_id = ?";
        jdbcTemplate.update(sql, schedule.getContent(), Timestamp.valueOf(schedule.getModifiedTime()), schedule.getContentId());
    }

    @Override //비밀번호 확인
    public boolean checkPassword(Long userId, String password){
        String sql = "SELECT COUNT(*) FROM user WHERE user_id = ? AND password = ?";
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, userId, password);

        if(cnt != null && cnt > 0) return true;
        else return false;
    }

    @Override //페이지화된 특정 일정 조회
    public List<Schedule> findFilteredPage(Long userId, LocalDate date, int set, int size){
        StringBuilder sql = new StringBuilder("SELECT s.*, u.user_name FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(userId != null){
            sql.append(" AND s.user_id = ?");
            params.add(userId);
        }
        if(date != null){
            sql.append(" AND DATE(s.updated_time) = ?");
            params.add(Date.valueOf(date));
        }
        sql.append(" ORDER BY s.updated_time DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(set);
        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) ->{
            Schedule schedule = new Schedule(
                    rs.getLong("content_id"),
                    rs.getString("content"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("created_time").toLocalDateTime(),
                    rs.getTimestamp("updated_time").toLocalDateTime()
            );
            schedule.setUserName(rs.getString("user_name"));
            return schedule;
        });
    }

    @Override //페이지화된 전체 일정 조회
    public List<Schedule> findPaged(int set, int size){
        String sql = "SELECT s.*, u.user_name FROM schedule s JOIN user u ON s.user_id = u.user_id ORDER BY s.updated_time DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{size, set}, (rs, rowNum) -> {
            Schedule schedule = new Schedule(
                    rs.getLong("content_id"),
                    rs.getString("content"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("created_time").toLocalDateTime(),
                    rs.getTimestamp("updated_time").toLocalDateTime()
            );
            schedule.setUserName(rs.getString("user_name"));
            return schedule;
        });
    }

    @Override
    public long countAll(){ //일정의 전체 갯수
        String sql = "SELECT COUNT(*) FROM schedule";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override //특정 일정의 갯수
    public long countByFilter(Long userId, LocalDate date){
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(userId != null){
            sql.append(" AND user_id = ?");
            params.add(userId);
        }
        if(date != null){
            sql.append(" AND DATE(updated_time) = ?");
            params.add(Date. valueOf(date));
        }
        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Long.class);
    }
}
