# Foodlier Batch
- jenkins를 통해 일정 주기 마다 batch job을 실행시켜서 dummy data 제거 및 data 수정
## 주요 기능
- 각각의 job을 멀티 모듈로 구성하여 각각의 job의 주기마다 실행
  - ElasticSearch Job : 일정 주기마다 모든 회원을 조회하여 ES의 데이터를 업데이트해줌(ex: 작성자 닉네임 변경)

# 노션 링크
https://jumpy-salute-9af.notion.site/1-507f9a29e4ba44e5841e3f03bcba9479
