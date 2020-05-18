package it.polito.ai.boot1;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
public class TimestampFakeServiceImp implements TimestampService {
    @Override
    public String getTimestamp() {
        return "This is fake timestampService";
    }
}
