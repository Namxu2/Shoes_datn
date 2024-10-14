package com.fpoly.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpoly.datn.model.dto.ChartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "chartSoleDTO",
                        classes = @ConstructorResult(
                                targetClass = ChartDTO.class,
                                columns = {
                                        @ColumnResult(name = "label",type = String.class),
                                        @ColumnResult(name = "value",type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getProductOrderSoles",
        resultSetMapping = "chartSoleDTO",
        query = "select s.name as label, count(o.quantity) as value  from sole s " +
                "inner join product p on p.sole_id = s.id " +
                "inner join orders o on p.id = o.product_id " +
                "where o.status = 3 " +
                "group by s.id"
)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sole")
public class Sole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status", columnDefinition = "BOOLEAN")
    private boolean status;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
    @OneToMany(mappedBy = "sole")
    @JsonIgnore
    private List<Product> products;
}
