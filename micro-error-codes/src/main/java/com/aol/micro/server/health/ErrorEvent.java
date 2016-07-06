package com.aol.micro.server.health;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.aol.micro.server.errors.BaseException;
import com.aol.micro.server.errors.ErrorCode;
import com.google.common.annotations.VisibleForTesting;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error-event")
@XmlType(name = "")
public class ErrorEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    final Date time = new Date();

    @XmlElement(name = "formatted-date")
    final String formattedDate = new SimpleDateFormat(
                                                      "yyyy.MM.dd 'at' HH:mm:ss z").format(new Date());

    @XmlElement(name = "error-code")
    ErrorCode code;

    @XmlElement(name = "error-message")
    String message;

    public ErrorEvent() {
    }

    public ErrorEvent(final BaseException exception) {
        code = exception.getErrorCode();
        message = exception.getMessage();
    }

    public ErrorCode getCode() {
        return code;
    }

    @VisibleForTesting
    public void setCode(final ErrorCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    @VisibleForTesting
    public void setMessage(final String message) {
        this.message = message;
    }

}
