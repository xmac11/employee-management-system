package com.team.ghana;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.company.Company;
import com.team.ghana.company.CompanyRepository;
import com.team.ghana.department.Department;
import com.team.ghana.department.DepartmentRepository;
import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeRepository;
import com.team.ghana.unit.Unit;
import com.team.ghana.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

import static com.team.ghana.enums.ContractType.EXTERNAL;
import static com.team.ghana.enums.ContractType.UNISYSTEMS;
import static com.team.ghana.enums.Status.ACTIVE;
import static com.team.ghana.enums.Status.INACTIVE;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BusinessUnitRepository businessUnitRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Company company = new Company("UniSystems", "+30 211 999 7000", "19-23, Al.Pantou str.");

								/* Business Units */
		BusinessUnit horizontalBU = new BusinessUnit("Horizontal", 1, company);
		BusinessUnit verticalBU = new BusinessUnit("Vertical", 2, company);

								/* Departments */
			// horizontal
		Department itDepartment = new Department("IT & Managed Services", horizontalBU);
		Department solutionsDepartment = new Department("Solutions & Pre-Sales", horizontalBU);
		Department technicalDepartment = new Department("Technical", horizontalBU);
			// vertical
		Department bankingDepartment = new Department("Banking & Financial Sector", verticalBU);
		Department publicSectorDepartment = new Department("Public Sector", verticalBU);
		Department telecomDepartment = new Department("Telecom & Enterprises", verticalBU);

									/* Units */
			// ITbusinessUnitRepository
		Unit softwareDevelopment = new Unit("Software Development", itDepartment);
		Unit qualityAssurance = new Unit("Quality Assurance", itDepartment);
			// Solutions
		Unit researchAndDevelopment = new Unit("Research and Development", solutionsDepartment);
			// Technical
		Unit support = new Unit("IT Support", technicalDepartment);
			// Banking
		Unit auditing = new Unit("Auditing", bankingDepartment);
		Unit accounting = new Unit("Accounting", bankingDepartment);

		Employee harris = new Employee("Makrylakis", "Charalampos", "address1", "123456789", LocalDate.of(2019, 11, 12),
				null, ACTIVE, UNISYSTEMS, softwareDevelopment, "Junior Software Developer");
		Employee aris = new Employee("Aris", "Kallergis", "address1", "123456789", LocalDate.of(2019, 11, 12),
				null, ACTIVE, UNISYSTEMS, softwareDevelopment, "Junior Software Developer");
		Employee kostas = new Employee("Kostas", "Tsaknias", "address1", "123456789", LocalDate.of(2017, 2, 3),
				null, ACTIVE, EXTERNAL, qualityAssurance, "Software Tester");
		Employee iosif = new Employee("Iosif", "Gemenitzoglou", "address1", "123456789", LocalDate.of(2019, 7, 9),
				null, ACTIVE, EXTERNAL, researchAndDevelopment, "Researcher");
		Employee dimitris = new Employee("Dimitris", "Pitsios", "address1", "123456789", LocalDate.of(2019, 11, 12),
				null, ACTIVE, EXTERNAL, support, "IT support");
		Employee eleni = new Employee("Eleni", "Eleni", "address1", "123456789", LocalDate.of(2012, 11, 12),
				LocalDate.of(2019, 3, 2), INACTIVE, EXTERNAL, auditing, "Auditor");
		Employee maria = new Employee("Maria", "Maria", "address1", "123456789", LocalDate.of(2013, 4, 23),
				LocalDate.of(2018, 5, 2), INACTIVE, EXTERNAL, accounting, "Accountant");


		// save Company
		companyRepository.save(company);

		// save Business Units
		businessUnitRepository.save(horizontalBU);
		businessUnitRepository.save(verticalBU);

		// save Departments
		departmentRepository.save(itDepartment);
		departmentRepository.save(solutionsDepartment);
		departmentRepository.save(technicalDepartment);
		departmentRepository.save(bankingDepartment);
		departmentRepository.save(publicSectorDepartment);
		departmentRepository.save(telecomDepartment);

		// save Units
		unitRepository.save(softwareDevelopment);
		unitRepository.save(qualityAssurance);
		unitRepository.save(researchAndDevelopment);
		unitRepository.save(support);
		unitRepository.save(auditing);
		unitRepository.save(accounting);

		// save Employees
		employeeRepository.save(harris);
		employeeRepository.save(aris);
		employeeRepository.save(kostas);
		employeeRepository.save(dimitris);
		employeeRepository.save(eleni);
		employeeRepository.save(maria);
	}
}
