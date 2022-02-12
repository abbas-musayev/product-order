package az.abbas.product.order.db.entity;

import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
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
@SQLDelete(sql = "update product_all set is_delete=true where id_product_all=?")
public class ProductAll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idProductALl;

    @JsonView(ViewsOrder.simple.class)
    private String productName;
    @JsonView(ViewsOrder.simple.class)
    private Float productPrice;
    @JsonView(ViewsOrder.simple.class)
    private Integer miqdar;
    @JsonView(ViewsOrder.simple.class)
    private Boolean isDelete;

    @ToString.Exclude
    @JsonBackReference()
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Orders orders;

    @PrePersist
    public void setIsDelete(){
        this.isDelete=false;
    }
}
