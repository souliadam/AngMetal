package com.esprit.gestionAuth.persistence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MontantPanier {
    private Double montantTotalHT = 0d;
    private Double Remise = 0d;
    private Double TVA = 0d;
    private Double montantTotalAPayer = 0d;
}
