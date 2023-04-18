package com.rest.api.controllers;

import com.rest.api.exceptions.UnsupportedMathOperationException;
import com.rest.api.math.SimpleMath;
import com.rest.api.util.NumberConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
    private   final AtomicLong counter = new AtomicLong();

    private final SimpleMath math = new SimpleMath();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public double sum(@PathVariable("numberOne") String a,
                      @PathVariable("numberTwo") String b) {
        if (!NumberConverter.isNumeric(a) || !NumberConverter.isNumeric(b))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.sum(NumberConverter.convertToDouble(a), NumberConverter.convertToDouble(b));
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public double subtraction(@PathVariable("numberOne") String a,
                              @PathVariable("numberTwo") String b) {
        if (!NumberConverter.isNumeric(a) || !NumberConverter.isNumeric(b))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.subtraction(NumberConverter.convertToDouble(a), NumberConverter.convertToDouble(b));
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public double multiplication(@PathVariable("numberOne") String a,
                                 @PathVariable("numberTwo") String b) {
        if (!NumberConverter.isNumeric(a) || !NumberConverter.isNumeric(b))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.multiplication(NumberConverter.convertToDouble(a), NumberConverter.convertToDouble(b));
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public double division(@PathVariable("numberOne") String a,
                           @PathVariable("numberTwo") String b) {
        if (!NumberConverter.isNumeric(a) || !NumberConverter.isNumeric(b))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        if (NumberConverter.convertToDouble(b) == 0D)
            throw new UnsupportedMathOperationException("Division by zero is not allowed!");

        return math.division(NumberConverter.convertToDouble(a), NumberConverter.convertToDouble(b));
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public double average(@PathVariable("numberOne") String a,
                          @PathVariable("numberTwo") String b) {
        if (!NumberConverter.isNumeric(a) || !NumberConverter.isNumeric(b))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.average(NumberConverter.convertToDouble(a), NumberConverter.convertToDouble(b));
    }

    @GetMapping("/squareRoot/{numberOne}")
    public double SquareRoot(@PathVariable("numberOne") String a) {
        if (!NumberConverter.isNumeric(a))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.squareRoot(NumberConverter.convertToDouble(a));
    }
}
