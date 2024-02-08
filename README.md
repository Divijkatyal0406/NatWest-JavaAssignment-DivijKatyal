# NatWest-JavaAssignment-DivijKatyal

I utilized Spring Boot (Java framework) to build a service that assess scholarship eligibility faster and with less memory usage. The process includes efficient CSV handling through parallel processing and streaming, coupled with a speedy student eligibility check facilitated by database indexing.

Checkout Deployed Service at (Note: Loading may take 30-50 seconds): https://natwest-assignment-divijkatyal.onrender.com/swagger-ui/index.html

You can go through a concise demo video, to experience efficient CSV processing and seamless student status checks in action.- https://drive.google.com/file/d/1HjIhNJOwnArdPqQCGv0hjzpcAxc28XVS/view?usp=sharing

GitHub Repository Link: https://github.com/Divijkatyal0406/NatWest-JavaAssignment-DivijKatyal 
 
Endpoint1 - CSV processing according to Subject Cutoffs(Path: /upload, Method: POST, Parameters: Cutoff Marks for each subject, Request Body: Students CSV File, Response Body: Updated CSV File):
This endpoint does rapid processing for scholarship eligibility determination. I utilized parallel processing, to calculate available threads which concurrently handle multiple rows of student data. Through distributed row processing, the workload is efficiently divided among threads, optimizing resource utilization. Furthermore, I implemented streaming to ensure memory efficiency, enabling the handling of large CSV data in chunks without loading the entire dataset into memory.

Endpoint2 - Student's Eligibility Check by Roll Number(Path: /status, Method: GET, Parameters: RollNo, Response Body: Eligibility status ('YES', 'NO') or 'NA'): 
This endpoint lets users check a student's scholarship eligibility status by providing their Roll Number. I utilized indexing mechanism to swiftly search the database, returning the eligibility status or NA if no student is found.

Tech Stack:
Spring Boot for Microservice architecture.
Swagger for API documentation and endpoint testing.
H2 as In-memory DB for storing students data.
JUnit for writing and executing thorough unit tests.
