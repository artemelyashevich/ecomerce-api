package com.elyashevich.auth.api.dto;

import com.elyashevich.auth.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTokenResponse {

    private String email;

    private List<String> roles;
}
