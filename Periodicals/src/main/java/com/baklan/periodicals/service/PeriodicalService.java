package com.baklan.periodicals.service;

import com.baklan.periodicals.dto.PeriodicalDTO;
import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.entity.periodicals.Subject;
import com.baklan.periodicals.entity.user.Role;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.exception.NotEnoughBalanceException;
import com.baklan.periodicals.exception.PeriodicalNotFoundException;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.exception.UserNotSavedException;
import com.baklan.periodicals.repository.PeriodicalRepository;
import com.baklan.periodicals.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.swing.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PeriodicalService {

    @Autowired
    PeriodicalRepository periodicalRepository;

    @Autowired
    UserRepository userRepository;

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

    public Periodical getPeriodicalById(Long id)throws PeriodicalNotFoundException{
        return periodicalRepository.findById(id).orElseThrow(PeriodicalNotFoundException::new);
    }

    /**
     * Returns <em>page</em> which contain collection of periodicals.<br>
     * <code>Pageable</code> contain  {@link org.springframework.data.domain.Sort}
     * with parameters for sorting periodicals.<br>
     * If <code>searchQuery</code> is not blank, then this field is
     * applied as the only parameter to search periodical by <code>name</code>.<br>
     * If <code>subject</code> is not blank, then the collection
     * will be filtered by the <code>subject</code> field.<br>
     * Else collection would be sorted by <code>sortField</code>.
     * @param sortField the name of the field to sort
     * @param subject <em>value</em> of {@link com.baklan.periodicals.entity.periodicals.Subject}
     *                to filter periodical by <code>subject</code>
     * @param asc direction for sorting
     * @param page number of current page to displaying periodicals
     * @param size max number of periodicals which containing one page
     * @param searchQuery contain name of periodical for searching by <code>name</code>
     * @return {@code Page<Periodical>} which contain sorted and filtered collection of periodicals entity
     * @see Pageable
     * @see #buildPage(String, boolean, int, int) 
     */
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


    @Transactional
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

    /**
     * Change current subscription status of Periodical for current user.<br>
     * If current user already has this periodical in <code>user.periodicals</code> than <code>remove</code><br>
     * If current user is not subscribed yet in this periodical:
     * <ul>
     *     <li>If current <em>periodical price</em> bigger than <em>user balance</em> throw <code>NotEnoughBalanceException</code></li>
     *     <li>If current <em>user balance</em> bigger <em>periodical price</em> than <code>add</code> periodical to current user</li>
     * </ul>
     * @param id of Periodical which should change subscription
     * @param userDetails data for getting current <code>user</code> from DB
     * @throws PeriodicalNotFoundException
     * @throws UserNotFoundException
     * @throws NotEnoughBalanceException
     */
    @Transactional
    public void changeSubscription(Long id, UserDetails userDetails)throws RuntimeException{
        Periodical periodical = periodicalRepository.findById(id)
                .orElseThrow(PeriodicalNotFoundException::new);

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if(!user.getPeriodicals().remove(periodical)){
            if (user.getBalance() - periodical.getPrice() >= 0) {
                user.setBalance(user.getBalance() - periodical.getPrice());
                user.getPeriodicals().add(periodical);
                periodical.setSubscribers(periodical.getSubscribers()+1);
                userRepository.save(user);
                return;
            }
            throw new NotEnoughBalanceException();
        }
        periodical.setSubscribers((periodical.getSubscribers() == 0) ? 0 : (periodical.getSubscribers()-1));
        userRepository.save(user);
    }

}
