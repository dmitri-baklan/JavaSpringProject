package com.baklan.periodicals.entity.replenishment;

import com.baklan.periodicals.dto.UserDTO;

import com.baklan.periodicals.entity.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "replenishments")
public class Replenishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime time;
}
