//package com.baklan.periodicals.entity.account;
//
//import com.baklan.periodicals.entity.replenishment.Replenishment;
//import com.baklan.periodicals.entity.user.User;
//import lombok.*;
//import org.apache.catalina.LifecycleState;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Set;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "accounts",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"user_id"})
//        }
//)
//public class Account {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    private Long balance;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false, unique = true)
//    private User user;
//
//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
//    private Set<Replenishment> replenishments;
//}
