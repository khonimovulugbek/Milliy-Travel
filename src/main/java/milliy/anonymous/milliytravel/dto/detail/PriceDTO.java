package milliy.anonymous.milliytravel.dto.detail;


import milliy.anonymous.milliytravel.dto.BaseDTO;
import milliy.anonymous.milliytravel.enums.Currency;
import milliy.anonymous.milliytravel.enums.TourType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PriceDTO extends BaseDTO {

    @Positive(message = "cost must be positive")
    private Long cost;

    @NotNull(message = "Currency required")
    private Currency currency;

    @NotNull(message = "Type required")
    private TourType type;

    public PriceDTO(String id, Long cost, Currency currency, TourType type) {
        super.id = id;
        this.cost = cost;
        this.currency = currency;
        this.type = type;
    }
}
