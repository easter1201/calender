#calender

	기능  	 - 		 메소드명	 - 				url             -	요청내용 - response - 상태코드
일정 등록	 - putSchedule		 - /api/schedules				- 요청 body  - 등록 정보 		-
일정 조회 	 - getSchedule 		 - /api/schedules/{scheduleId}  - 요청 param - 단건 응답 정보-
일정 전체조회 - getAllSchedule 	 - /api/schedules				- 요청 param - 다건 응답 정보-
일정 수정 	 - updateSchedule 	 - /api/schedules/{scheduleId}  - 요청 body  - 수정 정보 		-
일정 삭제 	 - deleteSchedule 	 - /api/schedules/{scheduleId}	- 요청 param - none			-
일정 전체삭제 - deleteAllSchedule - /api/schedules				- 요청 param - none			-