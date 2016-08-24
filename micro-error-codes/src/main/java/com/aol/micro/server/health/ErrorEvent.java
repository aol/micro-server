package com.aol.micro.server.health;

import com.aol.micro.server.errors.BaseException;
import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.errors.Severity;
import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error-event")
@XmlType(name = "")
public class ErrorEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final Date time = new Date();

    @XmlElement(name = "formatted-date")
    private final String formattedDate = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z").format(new Date());

    @XmlElement(name = "error-code")
    @Getter
    private final ErrorCode code;

    @XmlElement(name = "error-message")
    private final String message;

    @XmlTransient
    private final boolean fatal;

    public ErrorEvent() {
        code = null;
        message = null;
        fatal = false;
    }

    public ErrorEvent(final BaseException exception) {
        code = exception.getErrorCode();
        message = exception.getMessage();
        fatal = Severity.FATAL.equals(code.getSeverity());
    }

    public <R> R visit(Function<ErrorEvent, R> fatalError, Function<ErrorEvent, R> nonFatalError) {
        return fatal ? fatalError.apply(this) : nonFatalError.apply(this);

    }

}
