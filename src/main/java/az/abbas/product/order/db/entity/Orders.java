package az.abbas.product.order.db.entity;

import az.abbas.product.order.enums.EnumOrderStatus;
import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Where(clause = "is_delete=false")
@SQLDelete(sql = "update order_my set is_delete=true where id_order=?")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idOrder;

    @JsonView(ViewsOrder.simple.class)
    private String address;

    @JsonView(ViewsOrder.simple.class)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderCreatedDate;

    @JsonView(ViewsOrder.simple.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderFinisDate;

    @JsonView(ViewsOrder.simple.class)
    @Column(unique = true)
    private Integer order_unique;

    @JsonView(ViewsOrder.simple.class)
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Customer customer;

    @JsonView(ViewsOrder.simple.class)
    private boolean isDelete;

    @JsonView(ViewsOrder.simple.class)
    @JsonManagedReference
    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductAll> productAll;

    @JsonView(ViewsOrder.simple.class)
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus enumOrderStatus;

    @PrePersist
    public void setIsDelete(){
        this.isDelete=false;
    }

}
