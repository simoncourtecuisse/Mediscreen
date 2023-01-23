package com.Mediscreen.Report.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String invoker, Response response) {
        if (response.status() == 400) {
            return new ResourceNotFoundException("Report not found");
        }
        return defaultErrorDecoder.decode(invoker, response);
    }
}
