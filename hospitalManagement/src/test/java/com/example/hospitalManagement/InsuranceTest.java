package com.example.hospitalManagement;

import com.example.hospitalManagement.dto.AppointmentResponseDto;
import com.example.hospitalManagement.entity.Appointment;
import com.example.hospitalManagement.entity.Insurence;
import com.example.hospitalManagement.entity.Patient;
import com.example.hospitalManagement.service.AppointmentService;
import com.example.hospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public  void testInsurence(){
        Insurence insurence= Insurence.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validTill(LocalDate.of(2030,1,1))
                .build();

        Patient patient = insuranceService.assignInsuranceToPatient(insurence,1L);
        System.out.println(patient);

    }

    @Test
    public void  testCreateAppointment(){
        Appointment appointment =Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025, 11,1,12,00))
                .reason("general checkup")
                .build();
//        var newAppointment= appointmentService.createNewAppointment(appointment);
//        System.out.println(newAppointment);
    }
}
