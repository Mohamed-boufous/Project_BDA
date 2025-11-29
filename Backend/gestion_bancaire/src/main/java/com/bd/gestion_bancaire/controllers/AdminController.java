package com.bd.gestion_bancaire.controllers;

import com.bd.gestion_bancaire.dto.*;
import com.bd.gestion_bancaire.services.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    public AdminController(){
    }


}
