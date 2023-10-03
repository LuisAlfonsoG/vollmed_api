package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consultations.ConsultationDataFields;
import med.voll.api.domain.consultations.ConsultationDataRegister;
import med.voll.api.domain.consultations.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/consultations")
@SecurityRequirement(name = "bearer-key")
public class ConsultationController {

    @Autowired
    private ConsultationService service;
    @PostMapping
    @Transactional
    public ResponseEntity addConsultation(@RequestBody @Valid ConsultationDataRegister data){
        var response = service.validateConsultationData(data);
        return ResponseEntity.ok(response);
    }

}
