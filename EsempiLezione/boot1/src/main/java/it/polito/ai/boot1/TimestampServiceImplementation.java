package it.polito.ai.boot1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Primary
// Questa è l'implementazione concreta del servizio
@Service
public class TimestampServiceImplementation implements TimestampService {

    @Override
    public String getTimestamp() {
        return new Date().toString();
    }
}
