package com.wevservice.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wevservice.web.params.Sex;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "customer")
public class Customer {
    @Setter(AccessLevel.NONE)
    @Id
    @SequenceGenerator(name = "customer_id_seq",sequenceName ="customer_id_seq",initialValue = 10,allocationSize = 1)
    @GeneratedValue(generator ="customer_id_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    private String sex;

    @ManyToOne
    @JoinColumn(name = "actual_address_id",referencedColumnName = "id")
    private Address actualAddress;
    @ManyToOne
    @JoinColumn(name = "registred_address_id",referencedColumnName = "id")
    private Address registerAddress;



}
