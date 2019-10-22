# 생태 정보 서비스

## 사전준비사항
  * [Docker 설치 사이트](https://docs.docker.com/v17.09/engine/installation/)에 접속하여 운영체제를 선택하여 Docker 설치 및 dockerd 실행
  * [Docker-compose 설치 사이트](https://docs.docker.com/compose/install/)에 접속하여 운영체제를 선택하여 Docker-compose 설치
  * `docker ps` 및 `docker-compose` 명령어를 통해서 설치 확인
  * API 테스트를 위해서 [Postman](https://www.getpostman.com/downloads/)을 설치

## 실행
### Docker 기반 실행
Docker 기반 실행은 소스코드를 빌드하여 docker 이미지를 생성하고, 해당 이미지를 기반으로 실행하며, 크게 두단계로 나누어진다.

  * `docker-compose`를 이용하여 도커를 생성 및 실행
  * `mysql procedure`를 생성
  
``` shell
./gradlew startDocker
./gradlew createProc
```

### 로컬 테스트 및 컴파일
로컬테스트를 수행하기 위해서는 `mysql:3306`이 실행중이어야 하며, `word_freq` MySQL Procedure가 정상적으로 설치되어있어야 한다.

  * [Docker기반 실행](#Docker-기반-실행) 을 실행을 완료하여야 한다.
  * 로컬 개발 및 빌드를 위해서 mysql 호스트 설정이 필요하다. 아래의 두가지 방법중에 하나를 수행할 수 있다.
    * [application.properties](src/main/resources/application.properties) 에서 아래와 같이 설정을 변경
    
  ``` yaml
  spring.datasource.url=jdbc:mysql://localhost:3306/ecoexp?useUnicode=yes&characterEncoding=UTF-8
  ```
    * `/etc/hosts` 파일에 호스트 설정 추가
    
  ``` apacheconf
  127.0.0.1 mysql
  ```
  * `./gradlew bootRun`을 수행하여 소스코드를 로컬에서 실행할 수 있다.
    * 로컬 실행은 기본적으로 `8080` 포트를 사용하여 접속 할 수 있다.

### 테스트코드 수행
  * `./gradlew test`를 통해서 테스트코드를 수행할 수 있다.

## API 명세 및 테스트
### API 테스트
API 테스트를 위해서 Postman을 활용한다.

  * Postman을 실행하여, (Collection)[etc/postman/ecoexp.postman_collection.json]과 (Environment)[etc/postman/eco-localhost.postman_environment.json)를 Import 한다.
  * Environment를 `eco-localhost` 로 선택한다.
    * 기본적으로 `{{eco-host}}` 변수는 docker테스트를 하기 위해 `8082` 포트로 설정되어 있음.
    * 로컬 빌드로 테스트를 하기 위해서는 `8080`으로 변경이 필요함.
  * 최초에 `/v1/auth/signup`을 실행하여 `jwt`를 발급
  * Environment에서 `jwt` 변수를 받급받은 것으로 변경한다.
  * `/v1/eco/**` API를 실행한다.
  
### API 명세

| Method | API                        | 설명                                                          |
|--------|:---------------------------|:--------------------------------------------------------------|
| POST   | /v1/auth/signup            | 회원가입                                                      |
| POST   | /v1/auth/signin            | 로그인                                                        |
| POST   | /v1/auth/refresh/token     | 토큰갱신                                                      |
| POST   | /v1/eco/batch              | CSV 파일 업로드 및 데이터 삽입                                |
| POST   | /v1/eco/create             | 프로그램 데이터 생성                                          |
| POST   | /v1/eco/update/{programId} | 프로그램 데이터 업데이트                                      |
| GET    | /v1/eco/list/regions       | 등록된 모든 지역과 지역코드 조회                              |
| GET    | /v1/eco/region/{regionId}  | 지역코드에 등록된 프로그램 조회                               |
| GET    | /v1/eco/list/region        | 지역명으로 등록된 프로그램 조회                               |
| GET    | /v1/eco/list/keyword/count | 해당 키워드(소개컬럼 기준)로 등록된 지역별 프로그램 갯수 조회 |
| GET    | /v1/eco/count/keyword      | 등록된 모든 프로그램의 상세컬럼에서 해당 키워드 사용빈도 조회 |
| GET    | /v1/eco/recommend          | 지역,키워드로 프로그램 추천                                   |
| GET    | /v1/eco/program/{prgCode}  | 프로그램 코드로 프로그램 정보 조회                            |

## Troubleshooting

### Postman 환경변수 import 및 사용방법
  * [Postman learning center](https://learning.getpostman.com/docs/postman/environments_and_globals/manage_environments/) 참고

### 로컬 실행시에 API 가 제대로 동작 하지 않을 경우
  * [API 테스트](#API-테스트)에서 설명된 것과 같이 Postman Environment 를 `8080`으로 수정했는지 확인
  
### `/v1/eco/count/keyword`가 동작하지 않을 경우
  * `gradle createProc`을 했는지 확인
    * `gradle createProc`이 제대로 동작하려면, MySQL 도커 컨테이너 이름이 `mysql`이어야 함.
	
### 별도의 MySQL 호스트로 테스트를 하고 싶을 경우
  * (application.properties)[src/main/resources/application.properties]에서 mysql 호스트 이름 수정
  * (프로시저 생성 스크립트)[src/main/resources/create-proc.sql]를 활용하여 해당 데이터베이스에 프로시저를 생성
