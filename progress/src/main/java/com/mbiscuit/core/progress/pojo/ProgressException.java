package com.mbiscuit.core.progress.pojo;

import com.mbiscuit.core.common.pojo.CoreException;

public class ProgressException extends CoreException {
    public ProgressException(String message) {
        super(message);
    }

    public ProgressException(String message, Throwable cause) {
        super(message, cause);
    }
}
