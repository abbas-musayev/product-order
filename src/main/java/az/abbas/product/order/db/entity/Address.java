package az.abbas.product.order.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Where(clause = "is_delete=false")
@SQLDelete(sql = "update address set is_delete=true where id_address=?")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idAddress;

    private String addressName;

    private boolean isDelete;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Customer customer;

    @PrePersist
    public void setIsDelete(){
        this.isDelete=false;
    }

}