# SpringBatch-study
Spring Batch에 대해서 공부하고 정리하는 레포

# 배치란?
 1. 큰 단위의 작업을 일괄 처리 하는 것
 2. 대부분 처리량이 많고 비 실시간성 처리에 사용
 3. 사용자와의 상호작용 보다는 스케줄러와 같은 시스템에 의해서 실행
 
 # Spring Batch의 구성요소
 ```
  Spring Batch의 기본 단위는 Job
  Job은 Step으로 이루어지고, Step은 Tasklet이나 Chunk로 이루어진다.
  
  Tasklet은 간단한 작업에 유리
  Chunk는 큰 작업을 n개로 나누어서 작업하는데에 유리
  
  Job은 N개의 Step을 가지며, Step을 실행하며, 작업의 흐름(Flow)를 관리 할 수 있다.
 ```
 ## application.yml
 ```yml
  spring:
  batch:
      # batch 실행 시에 지정하지않으면 기본적으로 모든 Job이 실행된다.
      # Application 실행시 실행 할 job을 Program Argument로 전달 할 수 있다. (없다면 아무것도 실행x)
    job:
      names: ${job.name:NONE}
      # initialize-schema의 옵션은 3가지
      # always: Batch가 DDL 실행하는것을 허용
      # embedded: 내장 DB사용시에 사용
      # never: Batch가 DDL실행하는 것을 막음 Prod에 적합
    initialize-schema: never
 ```
 
 
 # Spring Batch의 메타데이터를 저장하는 테이블
 ![MetaTable](https://github.com/ktj1997/SpringBatch-study/blob/master/src/main/resources/static/Batch%20Meta%20table.png)
 
 ## 1. BATCH_JOB_INSTANCE
 ```
 Job이 실행되며 생성되는 최상위 계층 테이블
 job_name과 job_key를 기준으로 하나의 Row생성
 job_key는 BATCH_JOB_EXECUTION_PARAMS의 Parameter를 나열해 암호화되어 저장된다.
 
 JobInstacne 클래스와 매핑
```

 ## 2. BATCH_JOB_EXECUTION
 ```
 Job이 실행 되는 동안 시작/종료시간 Job상태, 결과 저장
 
 JobExecution 클래스와 매핑
 ```
 
 ## 3. BATCH_JOB_EXECUTION_PARAMS
 ```
 Job을 실행하기 위한 Parameter의 정보 저장
 
 JobParameters 클래스와 매핑
 ```
 
 ## 4. BATCH_JOB_EXECUTION_CONTEXT
 ```
 Job이 실행되며 공유할 데이터를 직렬화 해서 저장
 
 ExecutionContext 클래스와 매핑
 ```
 
 ## 5. BATCH_STEP_EXECUTION
 ```
 Step이 실행되는 동안 필요한 데이터 또는 실행된 결과 저장 

 StepExecution 클래스와 매핑
```
 
 ## 6. BATCH_STEP_EXECUTION_CONTEXT
 ```
 Step이 실행되는 동안 공유해야 할 데이터를 직렬화 해 저장 (Step끼리 공유x, Step끼리 공유하려면, BATCH_JOB_EXECUTION_CONTEXT)
 
 ExecutionContext 테이블과 매핑
 ```

# Chunk와 Tasklet의 차이

## Chunk
```
  ItemReader, ItemProcessor, ItemWriter를 통해서 페이징 된 데이터를 처리한다.
  대량처리를 하는경우 Tasklet보다 쉽게 처리 가능
```
### ItemReader < I >
```
   데이터를 읽어오는 Stream의 역할
   다양한 구현체가 있다.(List,JDBC,JPA,KAFKA,HIBERNATE,FILE ...)
```

### ItemProcessor <I,O>
```
   데이터를 가공하는 역할. I --> O
   원본 데이터를 가공해서 Writer에 넘겨 줄수도있고, 넘겨주지 않을 수도 있다.
```

### ItemWriter < O >
```
   N(N<=chunkSize) 만큼의 크기로 나뉘어진 Processor를 거친 데이터들인 List<O>를 받는다.
```
