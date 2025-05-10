package com.hospital_vm.cl.hospital_vm.controller;

import com.hospital_vm.cl.hospital_vm.model.Paciente;
import com.hospital_vm.cl.hospital_vm.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes  = pacienteService.BuscarTodosLosPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
            //alternativa 2 -> return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
        //alternativa 2 -> return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity <Paciente> listarunosolo(@PathVariable Integer id)
    {
          Paciente oPaciente= pacienteService.BuscarPorId(id);
          return ResponseEntity.ok(oPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente)
    {
        Paciente pacienteNuevo = pacienteService.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteNuevo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> modifcar(@PathVariable Integer id, @RequestBody Paciente paciente)
{
        Paciente pacienteMod = pacienteService.BuscarPorId(id);
        pacienteMod.setId(id);
        pacienteMod.setRun(paciente.getRun());
        pacienteMod.setNombres(paciente.getNombres());
        pacienteMod.setApellidos(paciente.getApellidos());
        pacienteMod.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteMod.setCorreo(paciente.getCorreo());
        pacienteService.save(pacienteMod);

        return ResponseEntity.ok(pacienteMod);
}
}

