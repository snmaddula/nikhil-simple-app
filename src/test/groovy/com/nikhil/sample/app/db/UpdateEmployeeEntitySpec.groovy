package com.nikhil.sample.app.db

import com.nikhil.sample.app.MockMvcSpec
import com.nikhil.sample.app.entity.EmployeeEntity
import com.nikhil.sample.app.repo.EmployeeRepo
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class UpdateEmployeeEntitySpec extends MockMvcSpec {

    @Autowired
    EmployeeRepo employeeRepo

    def setup() {
        EmployeeEntity ee = new EmployeeEntity(id: '7666bf2a-41af-4795-9984-24f380a9439d');
        ee.firstname = 'A'
        ee.lastname = 'B'
        ee.designation = 'C'
        ee.active = true
        employeeRepo.saveAndFlush(ee)
    }

    def 'should update employee'() {
        given:
        def id = '7666bf2a-41af-4795-9984-24f380a9439d'
        def employee = new EmployeeDto(id: id, firstname: 'A', lastname: 'B', designation: 'C', active: true)
        when:
        def response = mockMvc.perform(put("/api/employees/$id")
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then: 'should return updated employee'
        response.status == 200
        content.firstname == employee.firstname
        content.lastname == employee.lastname
        content.designation == employee.designation
        content.active == employee.active
        and: 'should persist employee'
        def saved = employeeRepo.findById(id)
                .orElseGet { assert false }
        saved.id == id
        saved.firstname == employee.firstname
        saved.lastname == employee.lastname
        saved.designation == employee.designation
        saved.active == employee.active
    }

    @Unroll
    def 'should validate invalid firstname'(String firstname) {
        given:
        def employee = new EmployeeDto(firstname: firstname, lastname: 'B', designation: 'C', active: true)
        when:
        def response = mockMvc.perform(put('/api/employees/7666bf2a-41af-4795-9984-24f380a9439d')
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        then:
        response.status == 400
        where:
        firstname | _
        null      | _
        ''        | _
        'x' * 501 | _
    }

    @Unroll
    def 'should validate invalid designation'(String designation) {
        given:
        def employee = new EmployeeDto(firstname: 'A', designation: designation, active: true)
        when:
        def response = mockMvc.perform(put('/api/employees/7666bf2a-41af-4795-9984-24f380a9439d')
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        then:
        response.status == 400
        where:
        designation | _
        null        | _
        ''          | _
        'x' * 1001  | _
    }

    def 'should return not found on invalid id'() {
        given:
        def employee = new EmployeeDto(firstname: 'A', lastname: 'B', designation: 'C', active: true)
        when:
        def response = mockMvc.perform(put('/api/employees/321')
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        then:
        response.status == 404
    }
}
