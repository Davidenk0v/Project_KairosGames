package com.kairosgames.kairos_games.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceRequest {

    private String genero;
    private String franquicia;
    private String plataforma;
    private String motivacion;
    private String narrativa;
    private String experiencia;
    private String esperado;
    private String tipoMundo;
    private String juegoFav;
}
