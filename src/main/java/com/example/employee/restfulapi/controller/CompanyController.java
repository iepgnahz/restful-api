package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(value = "")
    public ResponseEntity getCompanies() throws Exception {
        List<Company> companyList = companyRepository.findAll();
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getCompanyById(@PathVariable Long id) throws Exception {
        Company company = companyRepository.findOne(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/employees")
    public ResponseEntity getEmployeeByCompanyId(@PathVariable Long id) throws Exception {
        Company company = companyRepository.findOne(id);
        Set<Employee> employees = company.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
    public ResponseEntity getCompanyByPage(@PathVariable int page, @PathVariable int pageSize) throws Exception {
        Pageable pageable = new PageRequest(page-1, pageSize);
        Page companies = companyRepository.findAll(pageable);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity addCompany(@RequestBody Company company) throws Exception {
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCompany(@PathVariable Long id, @RequestBody Company newCompany) throws Exception {
        Company company = companyRepository.findOne(id);
        company.setCompanyName(newCompany.getCompanyName());
        company.setEmployeesNumber(newCompany.getEmployeesNumber());
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCompany(@PathVariable long id) throws Exception {
        companyRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


