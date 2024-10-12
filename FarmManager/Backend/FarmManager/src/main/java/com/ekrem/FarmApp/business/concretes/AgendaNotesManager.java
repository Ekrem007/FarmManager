package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.AgendaNotesService;
import com.ekrem.FarmApp.entities.concretes.AgendaNotes;
import com.ekrem.FarmApp.entities.dtos.AgendaNoteDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.AgendaNotesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaNotesManager implements AgendaNotesService {

    private final AgendaNotesRepository agendaNotesRepository;

    public AgendaNotesManager(AgendaNotesRepository agendaNotesRepository) {
        this.agendaNotesRepository = agendaNotesRepository;
    }

    @Override
    public AgendaNoteDto add(AgendaNoteDto agendaNoteDto) {
        AgendaNotes agendaNotes = mapToEntity(agendaNoteDto);
        agendaNotesRepository.save(agendaNotes);
        return mapToDto(agendaNotes);
    }

    @Override
    public AgendaNoteDto update(Long id,AgendaNoteDto agendaNoteDto) {
        AgendaNotes agendaNotes = agendaNotesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AgendaNotes", "id", id));
        agendaNotes.setContent(agendaNoteDto.getContent());
        agendaNotes.setUpdatedAt(new Date());
        agendaNotesRepository.save(agendaNotes);
        return mapToDto(agendaNotes);
    }

    @Override
    public void delete(Long id) {
        AgendaNotes agendaNotes = agendaNotesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AgendaNotes", "id", id));
        agendaNotesRepository.delete(agendaNotes);
    }

    @Override
    public AgendaNoteDto getAgendaNote(Long id) {
        AgendaNotes agendaNotes = agendaNotesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AgendaNotes", "id", id));
        return mapToDto(agendaNotes);
    }

    @Override
    public List<AgendaNoteDto> getAgendaNotes(LocalDateTime startDate, LocalDateTime endDate) {
        return agendaNotesRepository.getAllBetweenDates(startDate, endDate).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private AgendaNoteDto mapToDto(AgendaNotes agendaNotes) {
        return new AgendaNoteDto(agendaNotes.getId(), agendaNotes.getContent(), agendaNotes.getCreatedAt(), agendaNotes.getUpdatedAt());
    }

    private AgendaNotes mapToEntity(AgendaNoteDto agendaNoteDto) {
        return new AgendaNotes(agendaNoteDto.getId(), agendaNoteDto.getContent(), agendaNoteDto.getCreatedAt(), agendaNoteDto.getUpdatedAt());
    }
}
