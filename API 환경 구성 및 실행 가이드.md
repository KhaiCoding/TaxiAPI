## API 환경구성

<ul>
  <li>Language : Java 1.8</li>
  <li>Framework : SpringBoot 1.5.15 </li>
  <li>Database : MySQL 8.0</li>
  <li>Dependencies(maven) 
    <ul>
    <li>spring-boot-starter</li>
    <li>spring-boot-starter-test</li>
    <li>spring-boot-starter-web</li>
    <li>spring-boot-starter-jdbc</li>
    <li>lombok</li>
    <li>spring-boot-starter-data-couchbase</li>
    <li>mybatis-connect-java</li>
    <li>h2</li>
    <li>maven-surefire-plugin</li>
    <li>junit</li>
    <li>spring-boot-starter-data-solr</li>
    </ul>
  </li>
</ul>

### AWS 배포 가이드

**1.** 인스턴스 생성

&nbsp;&nbsp;저는 Ubuntu로 진행했습니다.

&nbsp;&nbsp;&nbsp;ubuntu 는 sudo apt-get .... 을 활용해서 환경에 필요한 설치를 진행합니다.

&nbsp;&nbsp;&nbsp;AMI 는 sudo yum ... 으로 작성하면 환경에 필요한 것들을 설치 할 수 있습니다.

**2.** 인스턴스에 Apache2, 자바 설치

    sudo apt install apache2
    
    sudo apt-get update
    
    sudo apt-get install openjdk-8-jdk

**2.** 인스턴스에 build 한 jar 파일 업로드

    filezila 등의 ftp 프로그램을 사용하면 편리합니다.
    apache2를 설치하셨다면 /var/www/html 디렉토리 안에 build한 jar 파일을 놓습니다.

**3.** 업로드 한 jar 파일 실행

    /var/www/html 경로로 이동합니다.
    $java -jar taxi_api.jar 명령어를 사용해서 호출합니다.
    
    실행되면 ctrl+z 를 누릅니다.
    $bg
        background 프로세스 확인
    $disown
    $exit

이제 연결 세션을 종료해도 계속 jar파일이 실행됩니다.

### jenkins 등의 ci를 위한 배포 스크립트

    
#!/bin/bash

REPOSITORY=/home/ubuntu/app/git

cd $REPOSITORY/taxiapi-1/

echo "> Git Pull" 

git pull

echo "> 프로젝트 Build 시작"

./mvn package

echo "> Build 파일 복사"

cp ./build/libs/*.jar

$REPOSITORY/ echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f taxiapi-1)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then 

     echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."

else
     echo "> kill -2 $CURRENT_PID" 
     kill -2 $CURRENT_PID
     sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls $REPOSITORY/ |grep 'taxiapi-1' | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME &


### How to use

1. 배포한 ip주소로 접속하셔서 아래 예시처럼 요청을 보내시면 됩니다.

    ex) 13.209.195.209:9000/api/taxi/allList
    
    ex) http://13.209.195.209:9000/api/user/join
    
    ex) http://13.209.195.209:9000/api/user/request
    

<hr>

### Request url

**api/user/join/** method = POST



회원가입 - 정상처리

<img src="https://t1.daumcdn.net/cfile/tistory/994F543D5B8BAE7D0F">

회원가입 - 아이디가 이미 있는 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99CDF94B5B8BD53512">

회원가입 - 키, 값을 모두 적지 않은 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99C7604F5B8BD75325">

**api/user/login/** method = POST



로그인 - 정상 처리

<img src="https://t1.daumcdn.net/cfile/tistory/99D94F485B8BD8C637">

로그인 - 아이디 또는 비밀번호가 틀린 경우

<img src="https://t1.daumcdn.net/cfile/tistory/992C59505B8BD96C0F">

로그인- 키, 값을 모두 적지 않은 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99BA3E445B8BD94D27">

**api/user/idCheck/** method = POST

아이디 중복확인 - 중복된 아이디

<img src="https://t1.daumcdn.net/cfile/tistory/991A2F3B5B8BDB372D">

아이디 중복확인 - 아이디를 적지 않은 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99A438345B8BDB1731">

아이디 중복확인 - 사용가능 아이디

<img src="https://t1.daumcdn.net/cfile/tistory/992177355B8BDB291C">

**api/taxi/allList/** method = GET

목록조회

<img src="https://t1.daumcdn.net/cfile/tistory/995CE4355B8BDDFE28">

```{.json}
[
{"request_id":5,"address":"테헤란로 156","marked":true,"request_time":"2018-09-02 22:08:57","marked_time":"2018-09-02 22:26:28","user_id":"user1@naver.com"},
{"request_id":3,"address":"왕산로 150","marked":true,"request_time":"2018-09-02 16:00:21","marked_time":"2018-09-02 17:18:39","user_id":"aaa@123.com"},
{"request_id":2,"address":"왕산로 555","marked":true,"request_time":"2018-09-02 15:36:01","marked_time":"2018-09-02 17:13:56","user_id":"123@123.com"},
{"request_id":1,"address":"왕산로 222","marked":true,"request_time":"2018-09-02 15:12:12","marked_time":"2018-09-02 17:13:18","user_id":"aaa@123.com"}
]
```

**api/taxi/request/** method = POST

택시 배차 요청 - 주소가 100자 이상인 경우

<img src="https://t1.daumcdn.net/cfile/tistory/996FBF405B8BDE9927">

택시 배차 요청 - 키 , 값을 모두 입력하지 않은 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99B035375B8BE0A315">

택시 배차 요청 - 정상 처리

<img src="https://t1.daumcdn.net/cfile/tistory/9985AE355B8BE1061C">

**api/taxi/response/** method = GET   

기사 배차 - request_id를 작성하지 않은 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99F2334B5B8BE4101A">

기사 배차 - request_id에 해당하는 요청이 없는 경우

<img src="https://t1.daumcdn.net/cfile/tistory/99CC6D505B8BE48D03">

기사 배차 - 정상 처리

<img src="https://t1.daumcdn.net/cfile/tistory/9991A3455B8BE51C34">

