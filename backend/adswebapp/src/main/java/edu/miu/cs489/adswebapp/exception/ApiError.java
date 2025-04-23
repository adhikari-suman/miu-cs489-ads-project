package edu.miu.cs489.adswebapp.exception;

import java.util.Map;

public record ApiError(Integer code, String message, Map<String, String> errors) {

}
