package it.polito.ai.test;

import it.polito.ai.IntStreamReducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IntStreamReducerTest {

    private IntStreamReducer isr;

    @BeforeEach
    void setUp() {
        isr = new IntStreamReducer();
    }

    @AfterEach
    void tearDown() {
        isr = null;
    }

    @Test
    void testSimpleStream(){
        assertEquals(6, isr.reduce(IntStream.of(1, 2, 3)));
    }

    @Test
    void testEmptyStream(){
        assertThrows(RuntimeException.class, ()->isr.reduce(IntStream.empty()));
    }
}