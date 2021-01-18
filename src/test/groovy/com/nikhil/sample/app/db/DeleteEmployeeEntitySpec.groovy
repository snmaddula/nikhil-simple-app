package com.nikhil.sample.app.db

import com.nikhil.sample.app.MockMvcSpec
import com.nikhil.sample.app.entity.EmployeeEntity
import com.nikhil.sample.app.repo.EmployeeRepo
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

class DeleteEmployeeEntitySpec extends MockMvcSpec {

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

    def 'should delete employee'() {

        given:
            def id = '7666bf2a-41af-4795-9984-24f380a9439d'
        when:
            def response = mockMvc.perform(delete("/api/employees/$id"))
                    .andReturn().response
        then:
            response.status == 200
        and: 'should delete employee from db'
            !employeeRepo.findById(id).isPresent()
    }
}
