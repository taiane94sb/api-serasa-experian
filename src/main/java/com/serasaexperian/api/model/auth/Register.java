package com.serasaexperian.api.model.auth;

import com.serasaexperian.domain.model.UserRole;

public record Register(String login, String password, UserRole role) {
}
