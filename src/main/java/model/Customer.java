package model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "customer",schema = "social")
public class Customer {
    @Setter(AccessLevel.NONE)
    @Id
    private Long id;
    @Column(name = "registred_address_id")
    private int registerAddressId;
    @Column(name = "actual_address_id")
    private int actualAddressId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    private String sex;

}
