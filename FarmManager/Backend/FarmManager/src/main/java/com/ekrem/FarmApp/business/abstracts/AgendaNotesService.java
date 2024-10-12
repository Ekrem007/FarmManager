package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.dtos.AgendaNoteDto;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AgendaNotesService {
    AgendaNoteDto add(AgendaNoteDto agendaNoteDto);
    AgendaNoteDto update(Long id,AgendaNoteDto agendaNoteDto);
    void delete(Long id);
    AgendaNoteDto getAgendaNote(Long id);

    List<AgendaNoteDto> getAgendaNotes(LocalDateTime startDate, LocalDateTime endDate);

}
