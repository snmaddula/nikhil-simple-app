package com.nikhil.sample.app.db

import com.nikhil.sample.app.MockMvcSpec
import com.nikhil.sample.app.repo.EmployeeRepo
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class AddEmployeeEntitySpec extends MockMvcSpec {

    @Autowired
    EmployeeRepo employeeRepo

    def 'should add new employee'() {
        given:
        def employee = new EmployeeDto(firstname: 'FN', lastname: 'LN', designation: 'BA', active: true)
        when:
        def response = mockMvc.perform(post('/api/employees')
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then: 'should return created employee'
        response.status == 201
        content.firstname == employee.firstname
        content.lastname == employee.lastname
        content.designation == employee.designation
        content.active == employee.active
        and: 'should persist employee'
        def saved = employeeRepo.findById(content.id)
                .orElseGet { assert false }
        saved.id != null
        saved.firstname == employee.firstname
        saved.designation == employee.designation
        saved.active == employee.active
    }

    @Unroll
    def 'should validate invalid firstname and lastname'(String firstname, String lastname) {
        given:
        def employee = new EmployeeDto(firstname: firstname, lastname: lastname, designation: 'B', active: true)
        when:
        def response = mockMvc.perform(post('/api/employees')
                .content(toJson(employee))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response
        then:
        response.status == 400
        where:
        firstname | lastname
        null      | null
        ''        | ''
        'x' * 101 | 'y' * 101
    }

    @Unroll
    def 'should validate invalid designation'(String designation) {
        given:
        def employee = new EmployeeDto(firstname: 'A', lastname: 'B', designation: designation, active: true)
        when:
        def response = mockMvc.perform(post('/api/employees')
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
}
