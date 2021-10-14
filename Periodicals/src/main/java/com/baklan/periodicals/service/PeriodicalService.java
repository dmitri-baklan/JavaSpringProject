package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.PeriodicalDTO;
import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.entity.periodicals.Subject;
import com.baklan.periodicals.entity.user.Role;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.repository.PeriodicalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PeriodicalService {

    @Autowired
    PeriodicalRepository periodicalRepository;

    @Transactional
    public void savePeriodical(PeriodicalDTO periodicalDTO){
        Periodical periodical = Periodical
                .builder()
                .name(periodicalDTO.getName())
                .subject(periodicalDTO.getSubject())
                .price(periodicalDTO.getPrice())
                .subscribers(periodicalDTO.getSubscribers())
                .build();

        periodicalRepository.save(periodical);
    }


    public Periodical getPeriodicalById(Long id){
        return periodicalRepository.findById(id).orElseThrow(null);
    }


    public Page<Periodical> getAllPeriodicals(String sortField, String subject,
                                              boolean asc, int page,
                                              int size, String searchQuery){

        Pageable pageable = buildPage(sortField, asc, page, size);
        if(subject.isBlank()){
            return searchQuery.isBlank() ? periodicalRepository.findAll(pageable)
                    : periodicalRepository.findByName(searchQuery, pageable);

        }
        return searchQuery.isBlank() ? periodicalRepository.findBySubject(Subject.valueOf(subject), pageable)
                : periodicalRepository.findByName(searchQuery, pageable);
    }

    public void updatePeriodical(PeriodicalDTO periodicalDTO, Long id){
        periodicalRepository.save(
                Periodical.builder()
                        .id(id)
                        .name(periodicalDTO.getName())
                        .subject(periodicalDTO.getSubject())
                        .price(periodicalDTO.getPrice())
                        .subscribers(periodicalDTO.getSubscribers())
                        .build()
        );
    }

    public void deletePeriodical(Long id){
        periodicalRepository.deleteById(id);
    }

    private Pageable buildPage(String sortField, boolean asc, int page, int size) {
        Optional<Sort> sort = asc ? Optional.of(Sort.by(sortField).ascending())
                : Optional.of(Sort.by(sortField).descending());

        return PageRequest.of(page - 1, size, sort.get());
    }

}
