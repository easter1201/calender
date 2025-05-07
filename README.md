#calender


| 기능 | 메소드명        | URL            | 요청내용 | Response 예시                                                                                             | 상태코드      |
|----|-------------|----------------| ---- |---------------------------------------------------------------------------------------------------------|-----------|
| 일정 등록 | putSchedule | /api/schedules | Body:{"userId": 1,"content": "스터디 모임","createdAt": "2025-05-07","updatedAt": "2025-05-07"}| {"scheduleId": 101,"message": "일정 등록 완료"}                                                               | 200: 등록완료 |
| 일정 조회 | getSchedule | /api/schedules/{scheduleId} | Param: scheduleId = 101 | {"scheduleId": 101,"userId": 1,"content": "스터디 모임","createdAt": "2025-05-07","updatedAt": "2025-05-07"} | 200: 조회완료 |
| 일정 전체조회 | getAllSchedule | /api/schedules | 없음  | { "scheduleId": 101, "content": "스터디 모임" }, { "scheduleId": 102, "content": "운동" }                      | 200: 조회완료 |
| 일정 수정 | updateSchedule | /api/schedules/{scheduleId} | Body:{"content": "스터디 장소 변경","updatedAt": "2025-05-08"}{"scheduleId": 101,"message": "수정 완료"}| { "scheduleId": 101, "message": "수정 완료" }                                                                                                        | 200: 수정완료 |
| 일정 삭제 | deleteSchedule | /api/schedules/{scheduleId} | Param: scheduleId = 101 | {"message": "삭제 완료"}                                                                                    | 200: 삭제완료 |
| 일정 전체삭제 | deleteAllSchedule | /api/schedules | 없음  | {"message": "전체 일정 삭제 완료"}                                                                              | 200: 삭제완료 |

---


