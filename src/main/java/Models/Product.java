package Models;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime dateRegistration;
    private Category category;
}