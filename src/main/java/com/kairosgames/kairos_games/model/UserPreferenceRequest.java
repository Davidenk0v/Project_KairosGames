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

    private Long preferenceId;
    private String response;

    public Long getPreferenceId() {
        return preferenceId;
    }
    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }
    public String getResponse(){return response;}
    public void setResponse(String response){this.response = response;}

}
