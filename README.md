# Toy-for-test
Toy project for testing new learning

## Travis CI

Continuous Integration (CI) : merge small code change frequently

Travis CI clone git repository to new virtual environment and conduct script

- [X] Travis : build branch에 PR(push)시 빌드

- [X] Travis : PR시 Docker Image build 후 Push

- [ ] Travis : AWS CodeDeploy로 배포

## Jenkins
- [X] Pipeline script from SCM : credential 설정 후 github 연동

- [X] Pipeline script : git clone - config file provider plugin 통해 properties 생성, spring project build

- [ ] Docker : docker 연동으로 jenkins 컨테이너 아닌 서버 컨테이너에서 jar run

- [X] Publish over SSH : EC2로 빌드 결과물 전달 

## Spring

- [X] AOP : Aspect 만들어보기

- [ ] AOP : Aspect 이용해서 로깅 구현하기

- [ ] Reactor : Mono(publisher) 익히기

## Discord API

- [X] Discord : Gateway 통해 command response

- [ ] Discord : Webhook 통해 command response
