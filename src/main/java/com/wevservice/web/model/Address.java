package com.wevservice.web.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    //uses generator from database
    @SequenceGenerator(name = "address_id_seq",sequenceName ="address_id_seq",initialValue = 10,allocationSize = 1)
    @GeneratedValue(generator ="address_id_seq",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
    //automatically insert creating time
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;
    //automatically update when entity changed
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modified;


}
