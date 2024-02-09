# NatWest-JavaAssignment-DivijKatyal

I utilized Spring Boot (Java framework) to build a service that assess scholarship eligibility faster and with less memory usage. The process includes efficient CSV handling through parallel processing and streaming, coupled with a speedy student eligibility check facilitated by database indexing.

Checkout Deployed Service at <b>(Note: Loading may take 30-50 seconds)</b>: https://natwest-assignment-divijkatyal.onrender.com/swagger-ui/index.html

You can go through a concise demo video, to experience efficient CSV processing and seamless student status checks in action.- https://drive.google.com/file/d/1HjIhNJOwnArdPqQCGv0hjzpcAxc28XVS/view?usp=sharing

# API Endpoints
## Endpoint1 - CSV processing according to Subject Cutoffs
This endpoint does rapid processing for scholarship eligibility determination. I utilized parallel processing, to calculate available threads which concurrently handle multiple rows of student data. Through distributed row processing, the workload is efficiently divided among threads, optimizing resource utilization. Furthermore, I implemented streaming to ensure memory efficiency, enabling the handling of large CSV data in chunks without loading the entire dataset into memory.

### Request

`POST /upload`

### Query Parameters
```
Parameter: Science Marks
value: INTEGER marks

Parameter: Maths Marks
value: INTEGER marks

Parameter: English Marks
value: INTEGER marks

Parameter: Computer  Marks
value: INTEGER marks
```

### Request Body
```
CSV File with Students Data
```

### Response Body
```
Updated CSV File with eligibility status as 'YES'/'NO'
```

## Endpoint2 - Student's Eligibility Check by Roll Number
This endpoint lets users check a student's scholarship eligibility status by providing their Roll Number. I utilized indexing mechanism to swiftly search the database, returning the eligibility status or NA if no student is found.

### Request

`GET /status`

### Query Parameters
```
Parameter: RollNo
value: INTEGER Rollno
```

### Response Body
```
Eligibility status ('YES', 'NO') or 'NA'
```

Tech Stack:
Spring Boot for Microservice architecture.
Swagger for API documentation and endpoint testing.
H2 as In-memory DB for storing students data.
JUnit for writing and executing thorough unit tests.
