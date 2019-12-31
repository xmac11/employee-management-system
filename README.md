# employee-management-system
Group project for the Mind the &lt;Code> program in Spring Boot

### Description
Backend for a CRUD application which exposes a REST API for managing the organizational structure of a company. <br />
The system was built with **Spring Boot** using the **Controller-Service-Repository** architecture. <br />
Role-based authorization was implemented for restricting certain requests to certain roles (admin, companyManager, businessUnitManager, departmentManager, unitManager, employee).

### Technologies
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JUnit](https://junit.org/junit4/)
- [mockito](https://site.mockito.org/)
- [MySQL](https://www.mysql.com/)

[Postman](https://www.getpostman.com/) was used to perform HTTP requests.

### E-R Diagram
<img src="https://www.dropbox.com/s/fmjldmnu6rx6arz/e-r.png?dl=0&raw=1" width="400">

### API spec
<details>
  <summary>Authentication:</summary>
  
  - POST:
    * **/auth:** username and password should be included in the Body. <br /> If authentication is successful, a JWT token is returned which should be included in all subsequent requests.
</details>

<details>
  <summary>Business Units:</summary>
  
  - GET: 
    * **/businessUnits:** Get all business units
    * **/businessUnits/{businessUnitId}:** Get business unit by Id
  - POST:
    * **/businessUnits:** Post a new business unit
  - PUT:
    * **/businessUnits/{businessUnitId}:** Update (override) an existing business unit
  - PATCH:
    * **/businessUnits/{businessUnitId}:** Update partially an existing business unit
</details>

<details>
  <summary>Departments:</summary>
  
  - GET: 
    * **/departments:** Get all departments
    * **/departments/{departmentId}:** Get deparment by Id
  - POST:
    * **/departments:** Post a new department
  - PUT:
    * **/departments/{departmentId}:** Update (override) an existing department
  - PATCH:
    * **/departments/{departmentId}:** Update partially an existing department
</details>

<details>
  <summary>Units:</summary>
  
  - GET: 
    * **/units:** Get all units
    * **/units/{unitId}:** Get unit by Id
  - POST:
    * **/units:** Post a new unit
  - PUT:
    * **/units/{unitId}:** Update (override) an existing unit
  - PATCH:
    * **/units/{unitId}:** Update partially an existing unit
</details>

<details>
  <summary>Employees:</summary>
  
  - GET: 
    * **/employees:** Get all employees
    * **/employees/{employeeId}:** Get employee by Id
    * **/employees/{searchCriteria}/{id}:** Get all employees which belong in searchCriteria ![in](https://latex.codecogs.com/png.latex?\in) {businessUnit, department, unit} with a given Id. <br/>
    For example, **/employees/department/2** returns all employees which belong to the Department with Id = 2.
  - POST:
    * **/employees:** Post a new employee
  - PUT:
    * **/employees/{employeeId}:** Update (override) an existing employee
  - PATCH:
    * **/employees/{employeeId}:** Update partially an existing employee
  - DELETE:
    * **/employees/{employeeId}:** Delete an employee by Id  
</details>

<details>
  <summary>Tasks:</summary>
  
  <br /> **Note:** Only employees from the same unit are allowed to work on a certain task. <br />
  
  - GET: 
    * **/tasks:** Get all tasks
    * **/tasks/{taskID}:** Get task by Id
  - POST:
    * **/tasks:** Post a new task
  - PUT:
    * **/tasks/{taskID}:** Update (override) an existing task
  - PATCH:
    * **/tasks/{taskID}:** Update partially an existing task
  - DELETE:
    * **/tasks:** Delete all tasks  
    * **/tasks/{taskId}:** Delete a task by Id
    * **/tasks/batch:** Delete all tasks with the user-provided IDs
</details>

### Team
- [Charalampos Makrylakis](https://github.com/xmac11)
- [Aristidis Kallergis](https://github.com/ArisKallergis)
- [Iosif Gemenitzoglou](https://github.com/gemeiosi)
- [Konstantinos Tsaknias](https://github.com/Qbique)
- [Dimitrios Pitsios](https://github.com/Jim-Pit)

Instructor: [Petros Efthymiou](https://github.com/p3tran)
