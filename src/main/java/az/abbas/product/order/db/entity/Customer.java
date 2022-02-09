package az.abbas.product.order.db.entity;

import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Where(clause = "is_delete=false")
@SQLDelete(sql = "update customer set is_delete=true where id_customer=?")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idCustomer;

    @JsonView(ViewsOrder.simple.class)
    private String name;
    @JsonView(ViewsOrder.simple.class)
    private String surname;
    private String username;
    private String password;
    private Float balance;

    private Boolean isDelete;

    @CreationTimestamp
    private LocalDateTime createdTimeCustomer;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Address> address;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Orders> orders;

    @PrePersist
    public void setIsDelete(){
        this.isDelete=false;
    }
}
