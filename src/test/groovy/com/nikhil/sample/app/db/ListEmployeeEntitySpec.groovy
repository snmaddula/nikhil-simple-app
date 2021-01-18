package com.nikhil.sample.app.db

import com.nikhil.sample.app.entity.EmployeeEntity
import com.nikhil.sample.app.repo.EmployeeRepo
import groovy.json.JsonSlurper
import com.nikhil.sample.app.MockMvcSpec
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class ListEmployeeEntitySpec extends MockMvcSpec {

    @Autowired
    EmployeeRepo employeeRepo

    def setup() {
        EmployeeEntity ee = new EmployeeEntity(id: '7666bf2a-41af-4795-9984-24f380a9439d');
        ee.firstname = 'FN'
        ee.lastname = 'LN'
        ee.designation = 'ABC'
        ee.active = false
        employeeRepo.saveAndFlush(ee)
    }


    def 'should list all entries'() {
        when:
            def response = mockMvc.perform(get('/api/employees'))
                    .andReturn().response
            def content = new JsonSlurper().parseText(response.contentAsString)
        then:
            response.status == 200
            content.size == 1
            content.any {
                it.id != null &&
                it.firstname == 'FN' &&
                it.lastname == 'LN' &&
                it.designation == 'ABC' &&
                it.active == false
            }
    }
}
