package com.curie.curieapp.dto.response;

public record LoginResponse(String accessToken, Long expiresIn) {
}
