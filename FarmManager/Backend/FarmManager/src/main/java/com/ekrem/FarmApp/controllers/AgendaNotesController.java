package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.AgendaNotesManager;
import com.ekrem.FarmApp.entities.dtos.GetByDateRequest;
import com.ekrem.FarmApp.entities.dtos.AgendaNoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agenda-notes")
public class AgendaNotesController {

    private final AgendaNotesManager agendaNotesManager;

    public AgendaNotesController(AgendaNotesManager agendaNotesManager) {
        this.agendaNotesManager = agendaNotesManager;
    }

    @PostMapping
    public ResponseEntity<AgendaNoteDto> addAgendaNotes(@RequestBody AgendaNoteDto AgendaNoteDto) {
        return ResponseEntity.ok(agendaNotesManager.add(AgendaNoteDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaNoteDto> getAgendaNotes(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(agendaNotesManager.getAgendaNote(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgendaNotes(@PathVariable(name = "id") Long id) {
        agendaNotesManager.delete(id);
        return ResponseEntity.ok("AgendaNotes deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaNoteDto> updateAgendaNotes(@PathVariable(name = "id") Long id, @RequestBody AgendaNoteDto AgendaNoteDto) {
        return ResponseEntity.ok(agendaNotesManager.update(id, AgendaNoteDto));
    }

    @GetMapping
    public ResponseEntity<List<AgendaNoteDto>> getAgendaNotes(@RequestParam String startDate, @RequestParam String endDate ) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atStartOfDay();
        return ResponseEntity.ok( agendaNotesManager.getAgendaNotes(start, end));
    }
}
